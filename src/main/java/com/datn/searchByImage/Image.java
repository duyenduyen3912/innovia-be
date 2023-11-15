/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datn.searchByImage;

import weka.core.Instances;
import weka.filters.Filter;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.instance.imagefilter.SimpleColorHistogramFilter;

/**
 *
 * @author ngocl
 */


public class Image {

    /**
     * @param args the command line arguments
     */
	ImageProcess iProcess = new ImageProcess();
    public Instances SearchImage (Instances predict)  {
    	Instances res = null;
    	try {
    	
    	Instances filteredData = extractColorHistogram("C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\data\\image.arff");
        Instances processData = iProcess.removeAttribute(filteredData, "image");
        Instances newData = iProcess.removeAttribute(processData, "class" );
        FindImage find = new FindImage();
        res = find.findNeighborsInRadius(predict, 0.5, newData);
      
      
    	} catch (Exception e) {
	        e.printStackTrace();
	    }
    	return res;
    	
    }
    
    public String predictLabel (Instances predict_label)  {
		 KnowledgeModel km = new KnowledgeModel();
		 String label = null;
		 try {
		 Instances filteredData = extractColorHistogram("C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\data\\image.arff");
		 Instances processData = iProcess.removeAttribute(filteredData, "image");
		 Instances train = km.divideTrainTest(processData, 20, false);
	     Instances test = km.divideTrainTest(processData, 20, true);
	        
		 Knn model = new Knn(train, "-K 20 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\"", null);
	     model.buildKNN(train);
	     model.evaluateKNN(test);
	     label = model.predictImage(predict_label);
		 } catch (Exception e) {
		        e.printStackTrace();
		    }
	    	return label;
    }
    
    private Instances extractColorHistogram (String path) throws Exception {
    	SimpleColorHistogramFilter filter = new SimpleColorHistogramFilter();
        DataSource source = new DataSource(path);
        Instances data = source.getDataSet();
        filter.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, filter);
        return filteredData;
    }
   
    
    
}
