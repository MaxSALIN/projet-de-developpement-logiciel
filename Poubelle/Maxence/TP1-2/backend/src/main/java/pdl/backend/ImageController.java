package pdl.backend;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
    try {
      var imgFile = imageDao.retrieve(id).get();
      byte[] bytes = imgFile.getData();
      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    } catch (Exception e) {
      System.out.println("Error : " + e + ". Image not found");
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
    imageDao.delete(imageDao.retrieve(id).get());
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(null);
  }

  @RequestMapping(value = "/images", method = RequestMethod.POST)
  public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file) throws IOException {
      if (!(file
        .getContentType().equals(MediaType.IMAGE_JPEG_VALUE))
        && !(file.getContentType().equals(MediaType.IMAGE_PNG_VALUE))) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);

      } else {
        byte[] filecontent = file.getBytes();
        File file2 = new File(
        "/home/clement/travail/L3/s6/l3c/l3c/Poubelle/Maxence/TP1-2/backend/src/main/resources/images"
        + file.getOriginalFilename());
        file.transferTo(file2);
        Image img = new Image(file.getOriginalFilename(), filecontent);
        try {
          imageDao.create(img);
        } catch (Exception e) {
          System.err.println(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
      }
  }

  @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getImageList() {
    ArrayNode nodes = mapper.createArrayNode();
    List<Image> l = imageDao.retrieveAll();
    for (int i = 0; i < l.size(); i++) {
      ObjectNode node = mapper.createObjectNode();
      Image Im = l.get(i);
      node.put("name", Im.getName());
      node.put("id", Im.getId());
      nodes.add(node);
    }
    return nodes;
  }

}
