package pdl.backend;

public class Image {
  private String name;
  private String type;
  private String size;
  private byte[] data;

  public Image(){

  }

  public Image(final String name, final byte[] data, final String type, final String size) {
    this.name = name;
    this.data = data;
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


  public byte[] getData() {
    return data;
  }
  public void setData(final byte[] data) {
    this.data = data;
  }

}
