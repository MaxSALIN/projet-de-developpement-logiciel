package pdl.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

@Repository
public class Database implements Dao<Image> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void afterPropertiesSet() throws Exception {
        jdbcTemplate.execute("DROP TABLE IF EXISTS gallery");
        this.jdbcTemplate
                .execute(
                        "CREATE TABLE IF NO EXISTS orientations (id bigserial PIRMARY KEY, name character varying(50))");
        jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('portrait')");
        jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('paysage')");
        jdbcTemplate.execute("SELECT * FROM orientations");
        this.jdbcTemplate.execute(
                "CREATE TABLE IF NO EXISTS gallery (id bigserial PRIMARY KEY, fichier character varying(255), size character varying(255), orientation INTEGER NOT NULL REFERENCES orientations(id), photo oid)");

    }

    public void create(final Image img) throws Exception {
        jdbcTemplate.update("INSERT INTO gallery (name, fichier) VALUES (?, ?)", img.getName(), img.getName());
    }

    public void delete(final Image img) throws Exception {
        jdbcTemplate.update("DELETE FROM gallery WHERE id=?", img.getId());
    }

    public void update(final Image img, final String[] params) throws Exception {
        // Not used
    }

    public Optional<Image> retrieve(final long id) throws Exception {
        return Optional.ofNullable(images.get(id));
        jdbcTemplate.queryForObject("SELECT fichier FROM gallery WHERE id = ?", String.class, id);
    }

    public List<Image> retrieveAll() throws Exception {
        ArrayList<Image> listAll = new ArrayList<Image>();
        for (long i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            listAll.add(image);
        }
        return listAll;
    }
}
