package pdl.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao implements Dao<Image> {

  private final Map<Long, Image> images = new HashMap<>();

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void afterPropertiesSet() throws Exception {
    jdbcTemplate.execute("DROP TABLE IF EXISTS gallery");
    this.jdbcTemplate
        .execute("CREATE TABLE IF NO EXISTS orientations (id bigserial PIRMARY KEY, name character varying(50))");
    jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('portrait')");
    jdbcTemplate.update("INSERT INTO orientations (name) VALUES ('paysage')");
    jdbcTemplate.execute("SELECT * FROM orientations");
    this.jdbcTemplate.execute(
        "CREATE TABLE IF NO EXISTS gallery (id bigserial PRIMARY KEY, fichier character varying(255), size character varying(255), orientation INTEGER NOT NULL REFERENCES orientations(id))");

  }

  public ImageDao() {
    // placez une image test.jpg dans le dossier "src/main/resources" du projet
    final ClassPathResource imgFile = new ClassPathResource("test.jpeg");
    byte[] fileContent;
    try {
      fileContent = Files.readAllBytes(imgFile.getFile().toPath());
      Image img = new Image("test.jpg", fileContent);
      images.put(img.getId(), img);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Optional<Image> retrieve(final long id) throws Exception {
    return Optional.ofNullable(images.get(id));
    jdbcTemplate.queryForObject("SELECT fichier FROM gallery WHERE id = ?", String.class, id);

  }

  @Override
  public List<Image> retrieveAll() {
    ArrayList<Image> listAll = new ArrayList<Image>();
    for (long i = 0; i < images.size(); i++) {
      Image image = images.get(i);
      listAll.add(image);
    }
    return listAll;
  }

  @Override
  public void create(final Image img) {
    images.put(img.getId(), img);
    jdbcTemplate.update("INSERT INTO gallery (name, fichier) VALUES (?, ?)", img.getName(), img.getName());
  }

  @Override
  public void update(final Image img, final String[] params) {
    // Not used
  }

  @Override
  public void delete(final Image img) {
    images.remove(img.getId(), img);
    jdbcTemplate.update("DELETE FROM gallery WHERE id=?", img.getId());
  }
}
