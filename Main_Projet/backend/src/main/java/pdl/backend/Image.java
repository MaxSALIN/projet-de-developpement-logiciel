package pdl.backend;

public class Image {
  private String name;
  private final String type;
  private final String size;
  private final byte[] data;

  public Image(final String name, final byte[] data, final String type, final String size){
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

  public String getType(){
    return type;
  }

  public String getSize(){
    return size;
  }

  public byte[] getData() {
    return data;
  }
}
