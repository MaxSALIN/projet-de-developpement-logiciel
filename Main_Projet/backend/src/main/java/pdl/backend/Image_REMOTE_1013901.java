package pdl.backend;

public class Image {
  private static Long count = Long.valueOf(0);
  private Long id;
  private String name;
  private String type;
  private String size;
  private byte[] data;

  public Image(final String name, final byte[] data, final String type, final String size) {
    id = count++;
    this.name = name;
    this.data = data;
    this.type = type;
    this.size = size;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getSize() {
    return size;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public byte[] getData() {
    return data;
  }
}
