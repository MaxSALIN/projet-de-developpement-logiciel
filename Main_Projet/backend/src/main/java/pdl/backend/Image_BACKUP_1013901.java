package pdl.backend;

public class Image {
  private String name;
<<<<<<< HEAD
  private final String type;
  private final String size;
  private final byte[] data;

  public Image(final String name, final byte[] data, final String type, final String size){
=======
  private String type;
  private String size;
  private byte[] data;

  public Image(final String name, final byte[] data, final String type, final String size) {
    id = count++;
>>>>>>> besoin_5
    this.name = name;
    this.data = data;
    this.type = type;
    this.size = size;
<<<<<<< HEAD
=======
  }

  public long getId() {
    return id;
>>>>>>> besoin_5
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
