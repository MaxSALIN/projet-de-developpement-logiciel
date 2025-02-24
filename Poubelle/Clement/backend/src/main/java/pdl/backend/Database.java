package pdl.backend;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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

        this.jdbcTemplate
                .execute(
                        "CREATE TABLE IF NOT EXISTS orientations (id bigserial PRIMARY KEY, name character varying(50))");
        this.jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('portrait')");
        this.jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('paysage')");
        this.jdbcTemplate.execute("SELECT * FROM orientations");
        this.jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS gallery (id bigserial PRIMARY KEY, fichier character varying(255) NOT NULL, orientation INTEGER NOT NULL REFERENCES orientations(id))");
        try {
            ClassPathResource directoryResource = new ClassPathResource(
                    "images/");
            File folder = directoryResource.getFile();
            Path folderPath = folder.toPath();
            try (Stream<Path> paths = Files.walk(folderPath, 1)) {
                paths.filter(Files::isRegularFile).forEach(path -> {
                    if (path.getFileName().toString().endsWith(".jpeg")) {
                        this.jdbcTemplate.update("INSERT INTO gallery (fichier, orientation) VALUES (?, ?)",
                                path.getFileName().toString(), 1);
                    }
                    if (path.getFileName().toString().endsWith(".png")) {
                        this.jdbcTemplate.update("INSERT INTO gallery (fichier, orientation) VALUES (?, ?)",
                                path.getFileName().toString(), 1);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int create(final Image img) throws Exception {
        return jdbcTemplate.update("INSERT INTO gallery (fichier, orientation) VALUES (?, ?)", img.getName(), 1);
    }

    public int delete(final Long id) throws Exception {
        return jdbcTemplate.update("DELETE FROM gallery WHERE id=?", id + 1);
    }

    public void update(final Image img, final String[] params) throws Exception {
        // Not used
    }

    public String retrieve(final long id) throws Exception {
        String finalString;
        if (id > jdbcTemplate.queryForObject("SELECT MAX(id) FROM gallery", Long.class)) {
            finalString = "";
        } else {
            finalString = jdbcTemplate.queryForObject("SELECT fichier FROM gallery WHERE id = ?", String.class,
                    (id + 1));

        }
        return finalString;
    }

    public Collection<Image> retrieveAll() throws Exception {
        return jdbcTemplate.query("SELECT fichier, id FROM gallery", new BeanPropertyRowMapper<>(Image.class));
    }
}
