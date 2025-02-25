package pdl.backend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(fileContent);
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
      e.printStackTrace();
    }
    return ResponseEntity.ok().body(null);
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) throws IOException {
        byte[] filecontent = file.getBytes();
        File file2 = new File(
            "/autofs/unitytravail/travail/clemsimon/L3/s6/projetWeb/l3c/Poubelle/Clement/backend/src/main/resources/images/"
                + file.getOriginalFilename());
        file.transferTo(file2);
        Image img = new Image(file.getOriginalFilename(), filecontent);
        try {
          database.create(img);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return ResponseEntity.ok().body(null);
  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() throws IOException {
    ArrayNode nodes = mapper.createArrayNode();
    try {
      Collection<Image> listAll = database.retrieveAll();
      for (Image img : listAll) {
        ObjectNode node = mapper.createObjectNode();
        node.put("name", img.getName());
        node.put("id", img.getId());
        nodes.add(node);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return nodes;
  }

}
