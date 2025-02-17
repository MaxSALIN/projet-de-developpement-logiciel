package pdl.backend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ImageController {

  @Autowired
  private ObjectMapper mapper;

  private final ImageDao imageDao;

  @Autowired
  public ImageController(ImageDao imageDao) {
    this.imageDao = imageDao;
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable("id") long id) throws IOException {
    var imgFile = imageDao.retrieve(id).get();
    byte[] bytes = imgFile.getData();
    // retourne l'image en bytes depuis le dossier d'images ( ici imageDao )
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
        .body(bytes);
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
    var imgFile = imageDao.retrieve(id).get();
    imageDao.delete(imgFile);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(null);
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes) throws IOException {
    byte[] filecontent = file.getBytes();
    Image img = new Image(file.getName(), filecontent); // curl -X POST -F
                                                        // "file=@/autofs/unitytravail/travail/clemsimon/L3/s6/projet/backend/src/main/resources/arbre.jpeg"
                                                        // "http://localhost:8181/images"
    imageDao.create(img);
    return ResponseEntity.ok().body(null);
  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() throws IOException {
    ArrayNode nodes = mapper.createArrayNode();
    List<Image> listAll = imageDao.retrieveAll();
    for (Image img : listAll) {
      ObjectNode node = mapper.createObjectNode();
      node.put("name", img.getName());
      node.put("id", img.getId());
      nodes.add(node);

    }
    return nodes;
  }

}
