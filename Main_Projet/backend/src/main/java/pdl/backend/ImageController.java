package pdl.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import org.javatuples.Pair;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ImageController {

  ///////////////////////////////////         Initialisation        /////////////////////////////////////////////
  @Autowired
  private ObjectMapper mapper;

  private final Database database;

  @Autowired
  public ImageController(Database database) {
    this.database = database;
  }

  ///////////////////////////////////         GET Requests         /////////////////////////////////////////////

  //GET /images
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

  //GET /images/{id}
  @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<?> getImage(@PathVariable("id") long id) throws IOException {
    byte[] fileContent = new byte[] {};
    try {
      if (!database.retrieve(id).equals("")) {
        Path pathFile = Paths.get("src/main/resources/images/" + database.retrieve(id));
        try {
          fileContent = Files.readAllBytes(pathFile);
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

  //GET /images/{id}/similar
  @RequestMapping(value = "/images/{id}/similar", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
  @ResponseBody
  public ArrayNode getSimilarImages(@PathVariable("id") long id, @RequestParam("number") long N,
      @RequestParam("descriptor") String DESCR) throws IOException {
    ArrayNode nodes = mapper.createArrayNode();
    List<Pair<Image, Double>> scores = new ArrayList<>();
    try {
      Collection<Image> listAll = database.retrieveAll();
      String imgToUse = database.retrieve(id);

      if (imgToUse == null) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found with that ID");
      }
      for (Image img : listAll) {
        if ("RGB".equals(DESCR)) {
          Pair<Image, Double> current = Pair.with(img, database.distanceRGB(imgToUse, img.getName()));
          scores.add(current);
        } else if ("HS".equals(DESCR)) {
          Pair<Image, Double> current = Pair.with(img, database.distanceHS(imgToUse, img.getName()));
          scores.add(current);
        } else {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect descriptor");
        }

      }
    } catch (Exception e) {
      System.err.println(e);
    }

    scores.sort(Comparator.comparing(Pair::getValue1));

    for (int i = 1; i <= Math.min(N, scores.size()); i++) {
      ObjectNode node = mapper.createObjectNode();
      Image img = scores.get(i).getValue0();
      node.put("id", database.getId(img.getName()));
      node.put("name", img.getName());
      node.put("type", img.getType());
      node.put("size", img.getSize());
      node.put("similarité", scores.get(i).getValue1());
      nodes.add(node);
    }
    return nodes;
  }

  ///////////////////////////////////         DELETE Request         /////////////////////////////////////////////

  @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
    try {
      Path pathFile = Paths.get("src/main/resources/images/" + database.retrieve(id));
      Files.delete(pathFile);
      database.delete(id);
    } catch (Exception e) {
      System.err.println(e);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok().body(null);
  }

  ///////////////////////////////////         POST Request         /////////////////////////////////////////////

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
      System.out.println(file.getName()+"\n\n\n\n\n\n\n");
      InputStream imageInputStream = file.getInputStream();
      String dimensions = "";
      try {
        BufferedImage image = ImageIO.read(imageInputStream);
        int w = image.getWidth();
        int h = image.getHeight();
        dimensions = String.valueOf(w).concat("*").concat(String.valueOf(h));
      } catch (IOException ioe) {
        System.err.println(ioe);
      }

      Path pathFile2 = Paths.get(
          "src/main/resources/images/"
              + file.getOriginalFilename());
      Files.createFile(pathFile2);
      file.transferTo(pathFile2);
      Image img = new Image(file.getOriginalFilename(), file.getContentType(), dimensions,
          SimilarImages.histogramRGB(pathFile2.toString()),
          SimilarImages.histogramHS(pathFile2.toString()));
      try {
        database.create(img);
      } catch (Exception e) {
        System.err.println(e);
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
  }

  

}
