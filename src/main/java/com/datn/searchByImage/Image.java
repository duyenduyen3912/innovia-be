/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datn.searchByImage;

import java.io.File;
import java.util.List;

import weka.core.Instances;
import weka.filters.Filter;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.imagefilter.SimpleColorHistogramFilter;

/**
 *
 * @author ngocl
 */


public class Image {

    /**
     * @param args the command line arguments
     */
    public Instances SearchImage (Instances predict)  {
    	ImageProcess iProcess = new ImageProcess();
    	Instances res = null;
    	try {
    	KnowledgeModel km = new KnowledgeModel();
    
    	SimpleColorHistogramFilter filter = new SimpleColorHistogramFilter();
        DataSource source = new DataSource("C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\data\\image.arff");
        Instances data = source.getDataSet();
        filter.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, filter);
        Instances processData = iProcess.removeAttribute(filteredData, "image");
        Instances newData = iProcess.removeAttribute(processData, "class" );
       
        Instances train = km.divideTrainTest(newData, 20, false);
        Instances test = km.divideTrainTest(newData, 20, true);
        
        Knn model = new Knn(train, "-K 20 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\"", null);
//        model.buildKNN(train);
//        model.evaluateKNN(test);
//        Instances res = model.predictClassLabel(predict);
        res = model.findNeighborsInRadius(predict, 0.5, newData);
      
      
    	} catch (Exception e) {
	        e.printStackTrace();
	    }
    	return res;
    	
    }
    
   
    
    
}
