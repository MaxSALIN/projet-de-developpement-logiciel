package pdl.backend;

public class Image {
  private String name;
  private String type;
  private String size;

  public Image() {

  }

  public Image(final String name, final String type, final String size) {
    this.name = name;
    this.type = type;
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getSize() {
    return size;
  }

  public void setSize(final String size) {
    this.size = size;
  }

}
