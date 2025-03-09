package pdl.backend;

public class Image {
  private String name;
  private String type;
  private String size;
  private double[] descr_RGB;
  private double[] descr_HS;
  public Image() {

  }

  public Image(final String name, final String type, final String size, final double[] descr_RGB, final double[] descr_HS) {
    this.name = name;
    this.type = type;
    this.size = size;
    this.descr_RGB = descr_RGB;
    this.descr_HS = descr_HS;
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

  public void setDescr_RGB(final double[] descr_RGB) {
    this.descr_RGB = descr_RGB;
  }

  public double[] getDescr_RGB() {
    return descr_RGB;
  }

  public void setDescr_HS(final double[] descr_HS) {
    this.descr_HS = descr_HS;
  }

  public double[] getDescr_HS() {
    return descr_HS;
  }


}
