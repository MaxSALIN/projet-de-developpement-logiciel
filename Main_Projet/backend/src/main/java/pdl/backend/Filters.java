package pdl.backend;

import java.awt.image.BufferedImage;

import boofcv.alg.color.ColorHsv;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.alg.feature.color.GHistogramFeatureOps;
import boofcv.alg.feature.color.Histogram_F64;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.Planar;
import boofcv.alg.filter.blur.BlurImageOps;

public class Filters {
    public static void blackAndWhite(String input) {
		//create the input and output images
        BufferedImage bufferedInput = UtilImageIO.loadImage(input);
        Planar<GrayF32> imageSelected = ConvertBufferedImage.convertFromPlanar(bufferedInput, null, true, GrayF32.class);
        Planar<GrayF32> imageOutput = imageSelected.createSameShape();

		//compute gray values
        for(int y = 0; y < imageSelected.getHeight(); y++) {
			for(int x = 0; x < imageSelected.getWidth(); x++) {
				int gl1 = (int) (imageSelected.getBand(0).get(x, y)*0.3);
				int gl2 = (int) (imageSelected.getBand(1).get(x, y)*0.59);
				int gl3 = (int) (imageSelected.getBand(2).get(x, y)*0.11);
				int grayValue = (gl1+gl2+gl3);
				imageOutput.getBand(0).set(x, y, grayValue);
				imageOutput.getBand(1).set(x, y, grayValue);
				imageOutput.getBand(2).set(x, y, grayValue);
			}
		}

		//saving the output image
        final String outputPath = "Black_And_White_"+input;
		UtilImageIO.saveImage(imageOutput, outputPath);
		System.out.println("Image saved in: " + outputPath);
    }

    public static void gaussianBlur(String input) {
		//create the input and output images
        BufferedImage bufferedInput = UtilImageIO.loadImage(input);
        Planar<GrayF32> imageSelected = ConvertBufferedImage.convertFromPlanar(bufferedInput, null, true, GrayF32.class);
        Planar<GrayF32> imageOutput = imageSelected.createSameShape();
        
		//compute the gaussian blur
        BlurImageOps.gaussian(imageSelected, imageOutput, -1, 8, null);

		//saving the output image
        final String outputPath =  "Gaussian_Blur_"+input;
		UtilImageIO.saveImage(imageOutput, outputPath);
		System.out.println("Image saved in: " + outputPath);
    }
    
    public static void changeColor(String input, double hue){
		//create the input and output images
        BufferedImage bufferedInput = UtilImageIO.loadImage(input);
        Planar<GrayF32> imageSelected = ConvertBufferedImage.convertFromPlanar(bufferedInput, null, true, GrayF32.class);
        Planar<GrayF32> imageOutput = imageSelected.createSameShape();
        
		//compute the hue change
        double[] hsv = new double[3];
		double[] rgb = new double[3];
		for(int y = 0; y < imageSelected.getHeight(); ++y) {
			for(int x = 0; x < imageSelected.getWidth(); ++x) {
				ColorHsv.rgbToHsv(imageSelected.getBand(0).get(x, y), imageSelected.getBand(1).get(x, y), imageSelected.getBand(2).get(x, y), hsv);
				hsv[0]=hue*(Math.PI/180);
				ColorHsv.hsvToRgb(hsv[0], hsv[1], hsv[2], rgb);
				for(int b =0; b<imageOutput.getNumBands(); b++){
					imageOutput.getBand(b).set(x, y, (float) rgb[b]);	
				}
			}
		}

		//saving the output image	
        final String outputPath =  "Change_Color_"+hue+"_"+input;
		UtilImageIO.saveImage(imageOutput, outputPath);
		System.out.println("Image saved in: " + outputPath);
    }

    public static void pixelFilter(String input, int pixel_size) {
		//create the input and output images
		BufferedImage bufferedInput = UtilImageIO.loadImage(input);
        Planar<GrayF32> imageSelected = ConvertBufferedImage.convertFromPlanar(bufferedInput, null, true, GrayF32.class);
        Planar<GrayF32> imageOutput = imageSelected.createSameShape();

		//compute the pixelated effet
		for(int y = 0; y < imageSelected.getHeight(); y+=pixel_size) {
			for(int x = 0; x < imageSelected.getWidth(); x+=pixel_size) {
				for(int b=0; b<imageSelected.getNumBands(); b++){
					int pixel=0;
					for(int i=0; i<pixel_size && y + i < imageSelected.getHeight(); i++){
						for(int j=0; j<pixel_size && x + j < imageSelected.getWidth(); j++){
							pixel+=imageSelected.getBand(b).get(x+j,y+i);;
						}
					}
					pixel/=(pixel_size*pixel_size);
					for(int i=0; i<pixel_size && y + i < imageOutput.getHeight(); i++){
						for(int j=0; j<pixel_size && x + j < imageOutput.getWidth(); j++){
							imageOutput.getBand(b).set(x+j, y+i, pixel);
						}
					}
				}
			}
		}

		//saving the output image
		final String outputPath =  "Pixel_"+input;
		UtilImageIO.saveImage(imageOutput, outputPath);
		System.out.println("Image saved in: " + outputPath);
	}
}