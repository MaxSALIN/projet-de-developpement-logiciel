package imageprocessing;

import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayS16;
import boofcv.struct.image.GrayU8;


public class Convolution {

  public static void meanFilter(GrayU8 input, GrayU8 output, int size) {
   
        int calcsize=size/2;
        for (int y = 0; y < input.height; ++y) {
            for (int x = 0; x < input.width; ++x) {
                output.set(x, y, input.get(x, y));
            }
        }
    for (int y = calcsize; y < input.height-calcsize; ++y) {
        for (int x = calcsize; x < input.width-calcsize; ++x) {
                int r=0;
                for(int j=y-calcsize; j<y+calcsize; j++){
                    for(int i=x-calcsize; i<x+calcsize;i++){
                        r+=(input.get(i, j));
                    }
                }
                int gl=(r/(size*size));
                output.set(x, y, gl);
            }
        }
    	
  }

  public static void convolution(GrayU8 input, GrayS16 output, int[][] kernel) {
      int n = (kernel.length-1)/2;
      for(int y=0; y<input.getHeight(); y++){
        for(int x=0; x<input.getWidth(); x++){
            if(y<n || y>n){
                output.set(x, y, 0);
            }
            if(x<n || x>n){
                output.set(x, y, 0);
            }
        }
      }
      for(int y=n; y<input.getHeight() -n ; y++){
        for(int x=n; x<input.getWidth()-n; x++){
            int r=0;
            for(int u=-n; u<=n; u++){
                for(int v=-n; v<=n; v++){
                    r+=input.get(x+u, y+v) * (kernel[u+n][v+n]);
                }
                
            }
            output.set(x, y, r);
        }
      }
  }

  public static void gradientImage(GrayU8 input, GrayU8 output, int[][] kernelX, int[][] kernelY){
      GrayS16 outputX=new GrayS16(output.getWidth(), output.getHeight());
      convolution(input, outputX, kernelX);
      GrayS16 outputY=new GrayS16(output.getWidth(), output.getHeight());
      convolution(input, outputY, kernelY);

      for(int y=0; y< input.getHeight(); y++){
        for(int x=0; x<input.getWidth(); x++){
            double gxderive = Math.pow(outputX.get(x, y), 2.0);
            double gyderive = Math.pow(outputY.get(x, y), 2.0);
            int gl = (int) Math.sqrt(gxderive+gyderive);
            output.set(x, y, gl);
        }
      }
  }

  public static void gradientImageSobel(GrayU8 input, GrayU8 output){
    int[][] kernelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
    int[][] kernelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    gradientImage(input, output, kernelX, kernelY);
  }

  public static void gradientImagePrewitt(GrayU8 input, GrayU8 output){
    int[][] kernelX = {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
    int[][] kernelY = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
    gradientImage(input, output, kernelX, kernelY);
  }

  
  public static void main(final String[] args) {
    // load image
    if (args.length < 2) {
      System.out.println("missing input or output image filename");
      System.exit(-1);
    }
    final String inputPath = args[0];
    GrayU8 input = UtilImageIO.loadImage(inputPath, GrayU8.class);
    GrayU8 output = input.createSameShape();

    
    //meanFilter(input, output, 11);
    //meanFilter(input, output, 111);

    //gradientImageSobel(input, output);
    
    //gradientImagePrewitt(input, output);
    
    // save output image
    final String outputPath = args[1];
    UtilImageIO.saveImage(output, outputPath);
    System.out.println("Image saved in: " + outputPath);
  }

}
