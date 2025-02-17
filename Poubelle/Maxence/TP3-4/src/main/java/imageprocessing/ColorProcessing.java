package imageprocessing;


import java.awt.image.BufferedImage;

import boofcv.alg.color.ColorHsv;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;


public class ColorProcessing {


	////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////						Question 1					  //////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void changeLuminosity(Planar<GrayU8> input, int delta){
		for(int i=0; i<input.getNumBands(); i++){
			for (int y = 0; y < input.getHeight(); ++y) {
				for (int x = 0; x < input.getWidth(); ++x) {
					int gl = input.getBand(i).get(x, y);
					gl = gl+delta;
					if(gl>255){
						gl=255;
					}
					else if(gl<0){
						gl=0;
					}
					input.getBand(i).set(x, y, gl);
				}
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////						Question 2					  //////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	public static void meanFilter(Planar<GrayU8> input, Planar<GrayU8> output, int size) {
		for(int b=0; b<input.getNumBands(); b++){
			int calcsize=size/2;
			for (int y = 0; y < input.height; ++y) {
				for (int x = 0; x < input.width; ++x) {
					output.getBand(b).set(x, y, input.getBand(b).get(x, y));
				}
			}
		for (int y = calcsize; y < input.height-calcsize; ++y) {
			for (int x = calcsize; x < input.width-calcsize; ++x) {
					int r=0;
					for(int j=y-calcsize; j<y+calcsize; j++){
						for(int i=x-calcsize; i<x+calcsize;i++){
							r+=(input.getBand(b).get(i, j));
						}
					}
					int gl=(r/(size*size));
					output.getBand(b).set(x, y, gl);
				}
			}
		}	
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////						Question 3					  //////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void grayFilter(Planar<GrayU8> input, Planar<GrayU8> output) {
		for(int y = 0; y < input.getHeight(); y++) {
			for(int x = 0; x < input.getWidth(); x++) {
				int gl1 = (int) (input.getBand(0).get(x, y)*0.3);
				int gl2 = (int) (input.getBand(1).get(x, y)*0.59);
				int gl3 = (int) (input.getBand(2).get(x, y)*0.11);
				int grayValue = (gl1+gl2+gl3);
				output.getBand(0).set(x, y, grayValue);
				output.getBand(1).set(x, y, grayValue);
				output.getBand(2).set(x, y, grayValue);
			}
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////						Question 4					  //////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////



	//////////////////////						Partie 1					  //////////////////////
	
	public static void rgbToHsv(Planar<GrayF32> input) {
		ColorHsv.rgbToHsv(input, input);
	}

	public static void hsvToRgb(Planar<GrayF32> input) {
		ColorHsv.hsvToRgb(input, input);
	}

	//////////////////////						Partie 2					  //////////////////////
	
	public static void colorImage(Planar<GrayF32> input, double teinte) {
		double[] hsv = new double[3];
		double[] rgb = new double[3];
		for(int y = 0; y < input.getHeight(); ++y) {
			for(int x = 0; x < input.getWidth(); ++x) {
				ColorHsv.rgbToHsv(input.getBand(0).get(x, y), input.getBand(1).get(x, y), input.getBand(2).get(x, y), hsv);
				hsv[0]=teinte*(Math.PI/180);
				ColorHsv.hsvToRgb(hsv[0], hsv[1], hsv[2], rgb);
				for(int b =0; b<input.getNumBands(); b++){
					input.getBand(b).set(x, y, (float) rgb[b]);	
				}
			}
		}	
	}

	//////////////////////						Partie 3					  //////////////////////
	
	public static void changeSaturation(Planar<GrayF32> input, double saturation) {
		double[] hsv = new double[3];
		double[] rgb = new double[3];
		for(int y = 0; y < input.getHeight(); ++y) {
			for(int x = 0; x < input.getWidth(); ++x) {
				ColorHsv.rgbToHsv(input.getBand(0).get(x, y), input.getBand(1).get(x, y), input.getBand(2).get(x, y), hsv);
				hsv[1]=saturation;
				ColorHsv.hsvToRgb(hsv[0], hsv[1], hsv[2], rgb);
				for(int b =0; b<input.getNumBands(); b++){
					input.getBand(b).set(x, y, (float) rgb[b]);
				}
			}
		}	
	}

	//////////////////////						Partie 4					  //////////////////////
	
	public static void histoTeinte(Planar<GrayF32> input, GrayU8 output) {
		double[] hsv = new double[3];
		int maxHeight=0;
		int[] histTeinte= new int[360];
		for(int y = 0; y < input.getHeight(); ++y) {
			for(int x = 0; x < input.getWidth(); ++x) {
				ColorHsv.rgbToHsv(input.getBand(0).get(x, y), input.getBand(1).get(x, y), input.getBand(2).get(x, y), hsv);
				int newTeinte=(int) (hsv[0]*(180/Math.PI));
				histTeinte[newTeinte]+=1;
			}

		}
		for(int i=0; i<360; i++){
			if(histTeinte[i]>maxHeight){
				maxHeight=histTeinte[i];
			}
		}
		for(int x = 0; x < output.getWidth(); ++x) {
			int nb_to_draw = histTeinte[x]*199/maxHeight;
			for(int y =0; y<nb_to_draw; y++){
				output.set(x, output.getHeight() -1 - y, 255);
			}
		}
	}
			
	
	//////////////////////						Partie 5					  //////////////////////
	
	public static void histoTeinteSaturation(Planar<GrayF32> input, GrayU8 output) {
		double[] hsv = new double[3];
		int[][] histTeinteSaturation= new int[360][101];
		for(int i =0; i<360; i++){
			for(int j=0; j<101; j++){
				histTeinteSaturation[i][j]=0;
			}
		}
		for(int y = 0; y < input.getHeight(); ++y) {
			for(int x = 0; x < input.getWidth(); ++x) {
				ColorHsv.rgbToHsv(input.getBand(0).get(x, y), input.getBand(1).get(x, y), input.getBand(2).get(x, y), hsv);
				int newTeinte = (int) (hsv[0]*(180/Math.PI));
				int newSaturation = (int) (hsv[1]*100);
				histTeinteSaturation[newTeinte][newSaturation]+=1;	
			}
		}			
		for(int y= 0; y < output.getHeight();y++){
			for(int x=0;x<output.getWidth(); x++){
				int gl = histTeinteSaturation[x][y];
				if(gl>255){
					gl=255;
				}
				output.set(x, y, gl);
			}
		}
	}

	public static void main( String[] args ) {

		// load image
		if (args.length < 2) {
			System.out.println("missing input or output image filename");
			System.exit(-1);
		}
		final String inputPath = args[0];
		BufferedImage input = UtilImageIO.loadImage(inputPath);
		
		if(input == null) {
			System.err.println("Cannot read input file '" + inputPath);
			System.exit(-1);
		}

		////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////					   Introduction	     			  //////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		/////////																			   /////////
		/////////		  Pour pouvoir exécuter les fonctions pour chaque question, 		   /////////
		/////////		     décommentez le code (/* */) puis recommentez le.				   /////////
		/////////																			   /////////
		/////////																			   /////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		Planar<GrayU8> image = ConvertBufferedImage.convertFromPlanar(input, null, true, GrayU8.class);
		Planar<GrayU8> output = image.createSameShape();

		////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////						Question 1					  //////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////

		
		/*changeLuminosity(image, -100);
		final String outputPath = args[1];
		UtilImageIO.saveImage(image, outputPath);
		System.out.println("Image saved in: " + outputPath);*/


		////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////						Question 2					  //////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////


		/*meanFilter(image, image, 11);
		final String outputPath = args[1];
		UtilImageIO.saveImage(image, outputPath);
		System.out.println("Image saved in: " + outputPath);*/

		////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////						Question 3					  //////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////


		/*grayFilter(image, output);
		final String outputPath = args[1];
		UtilImageIO.saveImage(output, outputPath);
		System.out.println("Image saved in: " + outputPath);*/


		////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////						Question 4					  //////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////
		
		Planar<GrayF32> image_hsv = ConvertBufferedImage.convertFromPlanar(input, null, true, GrayF32.class);
		BufferedImage input_image_hsvToRgb = UtilImageIO.loadImage("images_result_TP4/Q4/mountains_rgbToHsv.jpg");
		Planar<GrayF32> image_hsvToRgb = ConvertBufferedImage.convertFromPlanar(input_image_hsvToRgb, null, true, GrayF32.class);

		//////////////////////						Partie 1					  //////////////////////
		

		//rgbToHsv

		/*rgbToHsv(image_hsv);
		final String outputPath = args[1];
		UtilImageIO.saveImage(image_hsv, outputPath);
		System.out.println("Image saved in: " + outputPath);*/
		

		//////////////////////						Partie 2					  //////////////////////
		

		/*colorImage(image_hsv, 270);
		final String outputPath = args[1];
		UtilImageIO.saveImage(image_hsv, outputPath);
		System.out.println("Image saved in: " + outputPath);*/


		//////////////////////						Partie 3					  //////////////////////
		

		/*changeSaturation(image_hsv, 0);
		final String outputPath = args[1];
		UtilImageIO.saveImage(image_hsv, outputPath);
		System.out.println("Image saved in: " + outputPath);*/
		

		//////////////////////						Partie 4					  //////////////////////
		

		GrayU8 imageGrey = new GrayU8(360, 200);

		/*histoTeinte(image_hsv, imageGrey);
		final String outputPath = args[1];
		UtilImageIO.saveImage(imageGrey, outputPath);
		System.out.println("Image saved in: " + outputPath);*/
		
		
		//////////////////////						Partie 5					  //////////////////////
		

		GrayU8 imageGrey2 = new GrayU8(360, 101);

		/*histoTeinteSaturation(image_hsv, imageGrey2);
		final String outputPath = args[1];
		UtilImageIO.saveImage(imageGrey2, outputPath);
		System.out.println("Image saved in: " + outputPath);*/
		
	}

}