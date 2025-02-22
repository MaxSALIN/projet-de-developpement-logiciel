package pdl.backend;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
        this.jdbcTemplate
                .execute(
                        "CREATE TABLE IF NO EXISTS orientations (id bigserial PIRMARY KEY, name character varying(50))");
        jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('portrait')");
        jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('paysage')");
        jdbcTemplate.execute("SELECT * FROM orientations");
        this.jdbcTemplate.execute(
                "CREATE TABLE IF NO EXISTS gallery (id bigserial PRIMARY KEY, fichier character varying(255) NOT NULL, size character varying(255) NOT NULL, orientation INTEGER NOT NULL REFERENCES orientations(id), photo oid)");
        ClassPathResource directoryResource = new ClassPathResource("resources/images/");
        File folder = directoryResource.getFile();
        Path folderPath = folder.toPath();
        try (Stream<Path> paths = Files.walk(folderPath, 0)) {
            paths.filter(Files::isRegularFile)
            .forEach(path -> {
                if(path.endsWith(".jpg") || path.endsWith(".png")){
                    this.jdbcTemplate.update("INSERT INTO gallery (fichier) VALUES (?)", path.getFileName());
                }}
            );
        }
    }

    public int create(final Image img) throws Exception {
        return jdbcTemplate.update("INSERT INTO gallery (fichier) VALUES (?)", img.getName());
    }

    public int delete(final Long id) throws Exception {
        return jdbcTemplate.update("DELETE FROM gallery WHERE id=?", id);
    }

    public void update(final Image img, final String[] params) throws Exception {
        // Not used
    }

    public String retrieve(final long id) throws Exception {
        return jdbcTemplate.queryForObject("SELECT fichier FROM gallery WHERE id = ?", String.class, id);
    }

    public Collection<Image> retrieveAll() throws Exception {
        return jdbcTemplate.query("SELECT fichier, id FROM gallery", new BeanPropertyRowMapper<>(Image.class));
    }
}
