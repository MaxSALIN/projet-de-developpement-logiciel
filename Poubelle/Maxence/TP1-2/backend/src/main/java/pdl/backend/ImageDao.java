package pdl.backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDao implements Dao<Image> {

  private final Map<Long, Image> images = new HashMap<>();

  public ImageDao() {
            try {
            ClassPathResource directoryResource = new ClassPathResource(
                    "images/");
            File folder = directoryResource.getFile();
            Path folderPath = folder.toPath();
            try (Stream<Path> paths = Files.walk(folderPath, 1)) {
                paths.filter(Files::isRegularFile).forEach((Path path) -> {
                    byte[] fileBytes;
                    try {
                        fileBytes = Files.readAllBytes(path);
                        String name = path.toFile().getName();
                        if (path.getFileName().toString().endsWith(".jpeg")) {
                            try {
                                Image img = new Image(name, fileBytes);
                                create(img);
                            } catch (Exception e) {
                                System.err.println(e);
                            }
                        }
                        if (path.getFileName().toString().endsWith(".png")) {
                            try {
                                Image img = new Image(name, fileBytes);
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

  @Override
  public Optional<Image> retrieve(final long id) {
    if (images.containsKey(id)) {
      return Optional.of(images.get(id));
    }
    return Optional.empty();
  }

  @Override
  public List<Image> retrieveAll() {
    List<Image> to_retrieve = new ArrayList<>();
    for (Map.Entry i : images.entrySet()) {
      to_retrieve.add(images.get(i.getKey()));
    }
    return to_retrieve;
  }

  @Override
  public void create(final Image img) {
    images.put(img.getId(), img);
  }

  @Override
  public void update(final Image img, final String[] params) {
    // Not used
  }

  @Override
  public void delete(final Image img) {
    if (images.containsKey(img.getId())) {
      images.remove(img.getId());
    }
  }
}
