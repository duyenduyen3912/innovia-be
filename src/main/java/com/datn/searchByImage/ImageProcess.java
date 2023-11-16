package com.datn.searchByImage;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.imagefilter.SimpleColorHistogramFilter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class ImageProcess {
    private Instances data;

    public ImageProcess() {
        initializeAttributes();
    }

    private void initializeAttributes() {
        Attribute image = new Attribute("image", (ArrayList<String>) null);

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(image);

        this.data = new Instances("SearchImage", attributes, 1);
    }

    public Instances createInstance(String imagePath) {
        DenseInstance instance = new DenseInstance(2);
        
        instance.setValue(this.data.attribute("image"), imagePath);

        this.data.add(instance);
        return this.data;
    }
    
    public Instances createInstanceLabel(String imagePath) {
    	 Attribute image = new Attribute("image", (ArrayList<String>) null);
	     List<String> classValues = new ArrayList<>();
	     classValues.add("Sofa");
	     classValues.add("Ban");
	     classValues.add("Ghe");
	     classValues.add("BanGhe");
	     classValues.add("Giuong");
	     classValues.add("Ke");
	     classValues.add("Cay");
	     Attribute classDT = new Attribute("class", classValues);

       ArrayList<Attribute> attributes = new ArrayList<>();
       attributes.add(image);
       attributes.add(classDT);

       Instances InstancesLabel = new Instances("Predict_Label", attributes, 1);
       InstancesLabel.setClass(classDT);
       DenseInstance instance = new DenseInstance(2);
       
       instance.setValue(image, imagePath);
       instance.setMissing(classDT); 
       InstancesLabel.add(instance);

       return InstancesLabel;
    }
    
    public String loadImageFromMemory(String imageSearch) throws IOException {
		 
   	try {
   		
       byte[] imageBytes = Base64.decodeBase64(imageSearch);
       ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);

       BufferedImage image = ImageIO.read(bis);
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS");
       String timestamp = LocalDateTime.now().format(formatter);

       
       String fileName = "image_" + timestamp + ".png";
       
       String directoryPath = "C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\image"; 
       Path directory = Paths.get(directoryPath);
       
       File outputfile = new File(directory.toFile(),fileName);
       String imagePath = trimPath(outputfile.getPath());

       ImageIO.write(image, "png", outputfile);
       String image_predict = "image\\" + imagePath;
       return image_predict;
	    } catch (IOException e) {
	        throw new IOException("Error decoding Base64 to image", e);
	    }

   }
    
    public String trimPath(String path) {
        int lastIndexOfBackslash = path.lastIndexOf("\\", path.length() - 2);
        if (lastIndexOfBackslash != -1) {
            return path.substring(lastIndexOfBackslash + 1);
        }
        return path;
    }
    
    public Instances extractColorHistogram (Instances dataset)  {
    
    	 try {
	        SimpleColorHistogramFilter filter = new SimpleColorHistogramFilter();
	        filter.setInputFormat(dataset);
	        Instances filteredData = Filter.useFilter(data, filter);
	      
	        Instances newData = removeAttribute(filteredData, "image");
	        return newData;
	    } catch (Exception e) {
	   
	        e.printStackTrace();
	        return null; 
	    }
    }
    public void deleteImageFile(String imagePath) {
        File fileToDelete = new File(imagePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }
	   
    public Instances removeAttribute(Instances data, String attributeName) throws Exception {
        int attributeIndexToRemove = data.attribute(attributeName).index() + 1;

        Remove removeFilter = new Remove();
        removeFilter.setAttributeIndices("" + attributeIndexToRemove);

        removeFilter.setInputFormat(data);

        Instances newData = Remove.useFilter(data, removeFilter);

        return newData;
    }

}
