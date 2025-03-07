package pdl.backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ImageController {

  @Autowired
  private ObjectMapper mapper;

  private final Database database;

  @Autowired
  public ImageController(Database database) {
    this.database = database;
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable("id") long id) throws IOException {
    byte[] fileContent = new byte[] {};
    try {
      String img = "images/" + database.retrieve(id);
      if (!img.equals("")) {
        final ClassPathResource imgFile = new ClassPathResource(img);
        try {
          fileContent = Files.readAllBytes(imgFile.getFile().toPath());
        } catch (final IOException e) {
          System.err.println(e);
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
      }
      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
    } catch (Exception e) {
      System.err.println(e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
    try {
      File imgFile = new File(
          "/autofs/unitytravail/travail/clemsimon/L3/s6/projetWeb/l3c/Poubelle/Clement/backend/src/main/resources/images/"
              + database.retrieve(id));
      imgFile.delete();
      database.delete(id);
    } catch (Exception e) {
      System.err.println(e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok().body(null);
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) throws IOException {
      if (!(file
        .getContentType().equals(MediaType.IMAGE_JPEG_VALUE))
        && !(file.getContentType().equals(MediaType.IMAGE_PNG_VALUE))) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);

      } else {
        byte[] filecontent = file.getBytes();
        Long size = file.getSize();
        Path newFilePath = Paths.get("resources/images");
        File file2 = new File(
            "/autofs/unitytravail/travail/clemsimon/L3/s6/projetWeb/l3c/l3c/Main_projet/backend/src/main/resources/images/"
                + file.getOriginalFilename());
        file.transferTo(file2);
        Image img = new Image(file.getOriginalFilename(), filecontent, file.getContentType(), size.toString());
        try {
          database.create(img);
        } catch (Exception e) {
          System.err.println(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
      }
  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() throws IOException {
    ArrayNode nodes = mapper.createArrayNode();
    try {
      Collection<Image> listAll = database.retrieveAll();
      for (Image img : listAll) {
        ObjectNode node = mapper.createObjectNode();
        node.put("id", database.getId(img.getName()));
        node.put("name", img.getName());
        node.put("type", img.getType());
        node.put("size", img.getSize());
        nodes.add(node);
      }
    } catch (Exception e) {
      System.err.println(e);
    }

    return nodes;
  }

}
