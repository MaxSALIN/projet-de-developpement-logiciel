package pdl.backend;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Database implements InitializingBean {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        jdbcTemplate.execute("DROP TABLE IF EXISTS gallery");
        jdbcTemplate.execute("DROP TABLE IF EXISTS orientations");

        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS orientations (id bigserial PRIMARY KEY, name character varying(50))");
        this.jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('portrait')");
        this.jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('paysage')");
        this.jdbcTemplate.execute("SELECT * FROM orientations");
        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS gallery (id bigserial PRIMARY KEY, name character varying(255) NOT NULL, type character varying(255), size character varying(255) NOT NULL, similarity character varying(255), descr_RGB vector(1000) NOT NULL, descr_HS vector(400) NOT NULL)");
        try {
            /*
             * ClassPathResource directoryResource = new ClassPathResource(
             * "images/");
             * File folder = directoryResource.getFile();
             * Path folderPath = folder.toPath();
             */
            Path folderPath = Paths.get("src/main/resources/images");
            try (Stream<Path> paths = Files.walk(folderPath, 1)) {
                paths.filter(Files::isRegularFile).forEach((Path path) -> {
                    byte[] fileBytes;
                    try {
                        fileBytes = Files.readAllBytes(path);
                        String name = path.toFile().getName();
                        InputStream pathInputStream = new FileInputStream(path.toFile());
                        String dimensions = "";
                        try {
                            BufferedImage image = ImageIO.read(pathInputStream);
                            int w = image.getWidth();
                            int h = image.getHeight();
                            dimensions = String.valueOf(w).concat("*").concat(String.valueOf(h));
                        } catch (IOException ioe) {
                            System.err.println(ioe);
                        }
                        if (path.getFileName().toString().endsWith(".jpeg")
                                || path.getFileName().toString().endsWith("jpg")) {
                            try {
                                Image img = new Image(name, MediaType.IMAGE_JPEG_VALUE, dimensions,
                                        SimilarImages.histogramRGB(path.toFile().toString()),
                                        SimilarImages.histogramHS(path.toFile().toString()));
                                create(img);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                        if (path.getFileName().toString().endsWith(".png")) {
                            try {
                                Image img = new Image(name, MediaType.IMAGE_PNG_VALUE, dimensions,
                                        SimilarImages.histogramRGB(path.toFile().toString()),
                                        SimilarImages.histogramHS(path.toFile().toString()));
                                create(img);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                });
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public int create(final Image img) throws Exception {
        return jdbcTemplate.update(
                "INSERT INTO gallery (name, type, size, descr_RGB, descr_HS) VALUES ( ?, ?,?, ? ,?)",
                img.getName(),
                img.getType(), img.getSize(), img.getDescr_RGB(), img.getDescr_HS());
    }

    public int delete(final Long id) throws Exception {
        return jdbcTemplate.update("DELETE FROM gallery WHERE id=?", id);
    }

    public void update(final Image img, final String[] params) throws Exception {
        // Not used
    }

    public String retrieve(final long id) throws Exception {
        String finalString;
        finalString = jdbcTemplate.queryForObject("SELECT name FROM gallery WHERE id = ?", String.class,
                (id));
        return finalString;
    }

    public String getId(final String name) {
        return jdbcTemplate.queryForObject("SELECT id FROM gallery WHERE name=?", String.class, name);
    }

    public Collection<Image> retrieveAll() throws Exception {
        return jdbcTemplate.query("SELECT name, type, size FROM gallery",
                new BeanPropertyRowMapper<>(Image.class));
    }

    public double distanceRGB(final String name1, final String name2) throws Exception {
        String finalString = """
                    SELECT (g1.descr_RGB <-> g2.descr_RGB)
                    FROM gallery g1, gallery g2
                    WHERE g1.name = ? AND g2.name = ?
                """;

        Double distance = jdbcTemplate.queryForObject(finalString, Double.class, name1, name2);

        jdbcTemplate.update("UPDATE gallery SET similarity = ? WHERE name = ?", distance, name1);

        return distance;
    }

    public double distanceHS(final String name1, final String name2) throws Exception {
        String finalString = """
                    SELECT (g1.descr_HS <-> g2.descr_HS)
                    FROM gallery g1, gallery g2
                    WHERE g1.name = ? AND g2.name = ?
                """;

        Double distance = jdbcTemplate.queryForObject(finalString, Double.class, name1, name2);

        jdbcTemplate.update("UPDATE gallery SET similarity = ? WHERE name = ?", distance, name1);

        return distance;
    }
}
