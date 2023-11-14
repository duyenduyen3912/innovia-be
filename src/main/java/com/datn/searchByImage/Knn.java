package com.datn.searchByImage;

import weka.classifiers.lazy.IBk;
import weka.classifiers.Evaluation;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.neighboursearch.LinearNNSearch;
import weka.core.neighboursearch.NearestNeighbourSearch;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.ConverterUtils.DataSink;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Knn extends KnowledgeModel {
    private IBk knn;
    public Knn(Instances trainingData, String m_opts, String d_opts) throws Exception {
        super(trainingData, m_opts, d_opts);
    }

    public void buildKNN(Instances trainingData) throws Exception {
    	
        this.trainSet = trainingData;
        this.trainSet.setClassIndex(this.trainSet.numAttributes() - 1);
        this.knn = new IBk();
        knn.setOptions(model_options);
        knn.buildClassifier(this.trainSet);
    }

    
    public Instances findNeighborsInRadius(Instances queryData, double radius, Instances createInstances) throws Exception {
        queryData.setClassIndex(queryData.numAttributes() - 1);

        // Set up NearestNeighbourSearch
        NearestNeighbourSearch search = new LinearNNSearch();
        search.setDistanceFunction(new EuclideanDistance());
        search.setInstances(createInstances);

        int k = createInstances.size();  
        Instances neighborsInstances = search.kNearestNeighbours(queryData.instance(0), 20);
        Instances neighbors = new Instances(createInstances, 0);

        for (int i = 0; i < neighborsInstances.size(); i++) {
            Instance neighborInstance = neighborsInstances.get(i);
            double distance = search.getDistances()[i];
      
            if (distance <= radius) {
           
                neighbors.add(neighborInstance);
            }
        }
   
        return neighbors;
    }
    

    
}
