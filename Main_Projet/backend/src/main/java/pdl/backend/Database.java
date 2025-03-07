package pdl.backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

        this.jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS orientations (id bigserial PRIMARY KEY, name character varying(50))");
        this.jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('portrait')");
        this.jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('paysage')");
        this.jdbcTemplate.execute("SELECT * FROM orientations");
        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS gallery (id bigserial PRIMARY KEY, fichier character varying(255) NOT NULL, data character varying(255))");
        try {
            ClassPathResource directoryResource = new ClassPathResource(
                    "../../../images", Database.class);
            File folder = directoryResource.getFile();
            Path folderPath = folder.toPath();
            try (Stream<Path> paths = Files.walk(folderPath, 1)) {
                paths.filter(Files::isRegularFile).forEach((Path path) -> {
                    byte[] fileBytes;
                    try {
                        fileBytes = Files.readAllBytes(path);
                        String name = path.toFile().getName();
                        Long size = path.toFile().length();
                        if (path.getFileName().toString().endsWith(".jpeg")) {
                            try {
                                Image img = new Image(name, fileBytes, MediaType.IMAGE_JPEG_VALUE, size.toString());
                                create(img);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                        if (path.getFileName().toString().endsWith(".png")) {
                            try {
                                Image img = new Image(name, fileBytes, MediaType.IMAGE_PNG_VALUE, size.toString());
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
        return jdbcTemplate.update("INSERT INTO gallery (fichier) VALUES (?)", img.getName());
    }

    public int delete(final Long id) throws Exception {
        return jdbcTemplate.update("DELETE FROM gallery WHERE id=?", id + 1);
    }

    public void update(final Image img, final String[] params) throws Exception {
        // Not used
    }

    public String retrieve(final long id) throws Exception {
        String finalString;
            finalString = jdbcTemplate.queryForObject("SELECT fichier FROM gallery WHERE id = ?", String.class,
                    (id + 1));
        return finalString;
    }

    public String getId(final String name){
        return jdbcTemplate.queryForObject("SELECT id FROM gallery WHERE fichier=?", String.class, name);
    }

    public Collection<Image> retrieveAll() throws Exception {
        return jdbcTemplate.query("SELECT id, data FROM gallery", new BeanPropertyRowMapper<>(Image.class));
    }
}
