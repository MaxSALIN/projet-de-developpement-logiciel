package imageprocessing;


import java.awt.image.BufferedImage;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import boofcv.struct.image.Planar;


public class SimilarImages {

    ////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////						HOG Setup					  //////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
    
    /*public static int distance(Point2D_I32 A, Point2D_I32 B){
        return (int) Math.sqrt((B.x-A.x)*(B.x-A.x)+(B.y-A.y)*(B.y-A.y));

    }

    public static boolean minDistance(DescribeImageDense<Planar<GrayU8>, TupleDesc_F64> d1, DescribeImageDense<Planar<GrayU8>, TupleDesc_F64> d2, int threshold, List<Pair<Integer, Integer>> compare){
        boolean has_ended=false;
        
        int min=Integer.MAX_VALUE;
        
        int i_min=Integer.MAX_VALUE;
        int j_min=Integer.MAX_VALUE;
        for (int i = 0; i < d1.getLocations().size(); i++) {
            for (int j = 0; j < d2.getLocations().size(); j++) {
                Pair<Integer, Integer> couple= Pair.with(i,j);
                if(j>=i){
                    if(distance(d1.getLocations().get(i), d2.getLocations().get(j))<min && !compare.contains(couple) ){
                        min=distance(d1.getLocations().get(i), d2.getLocations().get(j));                 
                        i_min=i;
                        j_min=j;
                        
                    }
                }
                
                
            }
            
        }
        if(min>threshold){
            has_ended= true;
        }
        else{
            Pair<Integer, Integer> couple_to_remove= Pair.with(i_min,j_min);
            compare.add(couple_to_remove);
            has_ended= false;
        }

        return has_ended;
        
    }
	public static int compareImage(Planar<GrayU8> baseImage, Planar<GrayU8> imageUsed){
        int threshold = 100;
        
        List<Pair<Integer, Integer>> tupleList = new ArrayList<>();

        ConfigDenseHoG hog_config_baseImage = new ConfigDenseHoG(); // no params = default configuration
        DescribeImageDense<Planar<GrayU8>, TupleDesc_F64> describer_baseImage = FactoryDescribeImageDense.hog(hog_config_baseImage, ImageType.pl(3,GrayU8.class));
        describer_baseImage.process(baseImage); // Compute all block representations
        
        ConfigDenseHoG hog_config_imageUsed = new ConfigDenseHoG(); // no params = default configuration
        DescribeImageDense<Planar<GrayU8>, TupleDesc_F64> describer_imageUsed = FactoryDescribeImageDense.hog(hog_config_imageUsed, ImageType.pl(3,GrayU8.class));
        describer_imageUsed.process(imageUsed); // Compute all block representations

       
        int size1=describer_baseImage.getDescriptions().size();
        int size2=describer_imageUsed.getDescriptions().size();
        int result=0;

        while(true){
            boolean over_threshold=minDistance(describer_baseImage, describer_imageUsed, threshold, tupleList);
            if(over_threshold==true){
                break;
            }
            else{
                result++;
            }
            System.out.println(result);
        }
        
        int result_percent = result*Math.min(size1, size2)/100;
        return result_percent;
        /*
        for (int i = 0; i < describer_baseImage.getLocations().size(); i++) {

            // pixel location with p.x and p.y
            Point2D_I32 p1 = describer_baseImage.getLocations().get(i);
            Point2D_I32 p2 = describer_imageUsed.getLocations().get(i);
            // d.data is a double[] of size 36  
            TupleDesc_F64 d1 = describer_baseImage.getDescriptions().get(i);
            TupleDesc_F64 d2 = describer_imageUsed.getDescriptions().get(i);
            // You would process the feature descriptor here
        }
        

    }*/
	
    ////////////////////////////////////////////////////////////////////////////////////////////////



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

        BufferedImage input2 = UtilImageIO.loadImage("colorimages/hamac-640x426.jpg");
        Planar<GrayU8> image = ConvertBufferedImage.convertFromPlanar(input, null, true, GrayU8.class);
        Planar<GrayU8> image2 = ConvertBufferedImage.convertFromPlanar(input2, null, true, GrayU8.class);
        //int test =compareImage(image, image2);
        //System.out.println(test);
        
        
        final String outputPath = args[1];
        UtilImageIO.saveImage(image, outputPath);
        System.out.println("Image saved in: " + outputPath);
    }

}