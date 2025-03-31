package pdl.backend;

public class Image {

  /////////////////////////////////// Initialisation
  /////////////////////////////////// /////////////////////////////////////////////
  private static Long count = Long.valueOf(0);

  private String directory;
  private String name;
  private String type;
  private String size;
  private double[] descr_RGB;
  private double[] descr_HS;

  public Image() {

  }

  public Image(final String directory, final String name, final String type, final String size,
      final double[] descr_RGB,
      final double[] descr_HS) {
    this.directory = directory;
    this.name = name;
    this.type = type;
    this.size = size;
    this.descr_RGB = descr_RGB;
    this.descr_HS = descr_HS;

  }

  /////////////////////////////////// Getters
  /////////////////////////////////// /////////////////////////////////////////////

  public String getDirectory() {
    return directory;
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

  public double[] getDescr_RGB() {
    return descr_RGB;
  }

  public double[] getDescr_HS() {
    return descr_HS;
  }

  /////////////////////////////////// Setters
  /////////////////////////////////// /////////////////////////////////////////////

  public void setDirectory(final String directory) {
    this.directory = directory;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public void setSize(final String size) {
    this.size = size;
  }

  public void setDescr_RGB(final double[] descr_RGB) {
    this.descr_RGB = descr_RGB;
  }

  public void setDescr_HS(final double[] descr_HS) {
    this.descr_HS = descr_HS;
  }

}
