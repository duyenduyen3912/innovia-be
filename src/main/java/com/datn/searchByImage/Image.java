/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datn.searchByImage;

import java.io.File;

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
    public void SearchImage (Instances predict)  {
    	try {
    	KnowledgeModel km = new KnowledgeModel();
    
    	SimpleColorHistogramFilter filter = new SimpleColorHistogramFilter();
        DataSource source = new DataSource("C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\data\\image.arff");
        Instances data = source.getDataSet();
        filter.setInputFormat(data);
        Instances filteredData = Filter.useFilter(data, filter);
        
        saveArff(filteredData, "C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\data\\extracted.arff");
        int attributeIndexToRemove = filteredData.attribute("image").index() + 1; 

        // Create the Remove filter
        Remove removeFilter = new Remove();
        removeFilter.setAttributeIndices("" + attributeIndexToRemove); // Specify the index of the attribute to be removed

        // Set the input format for the filter
        removeFilter.setInputFormat(filteredData);

        // Apply the filter to remove the specified attribute
        Instances newData = Filter.useFilter(filteredData, removeFilter);
        
        
        Instances train = km.divideTrainTest(newData, 20, false);
        Instances test = km.divideTrainTest(newData, 20, true);
        
        Knn model = new Knn(train, "-K 3 -W 0 -A \"weka.core.neighboursearch.LinearNNSearch -A \\\"weka.core.EuclideanDistance -R first-last\\\"\"",null);
        model.buildKNN(train);
        model.evaluateKNN(test);
        Instances res = model.predictClassLabel(predict);
        saveArff(res, "C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\data\\result.arff");
    	} catch (Exception e) {
    		   
	        e.printStackTrace();
	        
	    }
    }
    
    public static void saveArff(Instances instances, String filePath) {
        try {
            
            ArffSaver arffSaver = new ArffSaver();
            arffSaver.setInstances(instances);
            arffSaver.setFile(new java.io.File(filePath));
            arffSaver.writeBatch();

           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
