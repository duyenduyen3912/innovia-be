package com.datn.product;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.attribute.StringToNominal;
import weka.filters.unsupervised.instance.imagefilter.SimpleColorHistogramFilter;

import org.apache.commons.codec.binary.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageTest {

//    public static void main(String[] args) {
//        // Replace the following string with the actual Base64-encoded image string from your request
//        String imageSearch = "your_base64_encoded_image_string_here";
//
//        // Load an image from memory
//        try {
//            BufferedImage image = loadImageFromMemory(imageSearch);
//
//            // Extract color histogram features
//            double[] features = extractColorHistogram(image);
//
//            // Create an empty dataset
//            Instances dataset = createDataset();
//
//            // Create an instance
//            Instance instance = createInstance(dataset, features);
//
//            // Add the instance to the dataset
//            dataset.add(instance);
//
//            // Print the dataset
//            System.out.println(dataset);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public  BufferedImage loadImageFromMemory(String imageSearch) throws IOException {
		 String filePath = "C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\image\\decode_image.jpg";
		 
 	        try {
 	        	byte[] imageBytes = Base64.decodeBase64(imageSearch);
 	            File file = new File(filePath);
 	            try (FileOutputStream fos = new FileOutputStream(file)) {
 	                fos.write(imageBytes);
 	            }
 
 	          
 	        } catch (IOException e) {
 	    
 	        }
    	try {

        byte[] imageBytes = Base64.decodeBase64(imageSearch);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bis);
	    } catch (IOException e) {
	        throw new IOException("Error decoding Base64 to image", e);
	    }

    }

    public static double[] extractColorHistogram(BufferedImage image) {
        try {
            // Create SimpleColorHistogram filter
            SimpleColorHistogramFilter colorHistogram = new SimpleColorHistogramFilter();
            colorHistogram.setOptions(weka.core.Utils.splitOptions("-B 256"));

            // Convert BufferedImage to Weka Instance
            Instances dataset = createDataset();
            Instance instance = new DenseInstance(dataset.numAttributes());

            // Apply the SimpleColorHistogram filter
            colorHistogram.setInputFormat(dataset);
            instance.setDataset(dataset);
            colorHistogram.input(instance);

            // Convert the histogram to a 1D array
            colorHistogram.batchFinished();
            instance = colorHistogram.output();

            double[] features = new double[instance.numAttributes() - 1];
            for (int i = 0; i < features.length; i++) {
                features[i] = instance.value(i);
            }

            return features;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Instances createDataset() {
        // Declare the attribute (feature)
        ArrayList<Attribute> attributes = new ArrayList<>();

        // Replace 256 with the appropriate number of bins based on your requirements
        for (int i = 0; i < 256; i++) {
            attributes.add(new Attribute("bin_" + i));
        }

        // Declare the class attribute
        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("Sofa");
        classValues.add("Ban");
        classValues.add("Ghe");
        classValues.add("BanGhe");
        classValues.add("Giuong");
        classValues.add("Ke");
        classValues.add("Cay");
        Attribute classAttribute = new Attribute("class", classValues);

        // Add attributes to the dataset
        attributes.add(classAttribute);

        // Create an empty dataset with the specified attributes
        Instances dataset = new Instances("ImageDataset", attributes, 0);

        // Set the class index
        dataset.setClassIndex(attributes.size() - 1);

        return dataset;
    }

    public Instance createInstance(Instances dataset, double[] features) {
        // Create an empty instance
        Instance instance = new DenseInstance(dataset.numAttributes());

        // Set the feature values
        for (int i = 0; i < features.length; i++) {
            instance.setValue(i, features[i]);
        }

        // Set the class value (label)
        instance.setValue(dataset.classAttribute(), "class1"); // Replace with actual class label

        return instance;
    }
}
