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

import java.util.Random;

public class Knn extends KnowledgeModel {
    private IBk knn;
    private Evaluation eval;

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

    public void evaluateKNN(Instances predictdata) throws Exception {
        this.testSet = predictdata;
        this.testSet.setClassIndex(this.testSet.numAttributes() - 1);

        Random rd = new Random(1);
        int folds = 10;
        eval = new Evaluation(this.trainSet);
        eval.crossValidateModel(knn, this.testSet, folds, rd);
//        System.out.println(eval.toSummaryString("\n -----Ket qua tim kiem bang hinh anh-----\n", false));
    }

    public Instances predictClassLabel(Instances data) throws Exception {
        Instances unLabel = data;
        unLabel.setClassIndex(unLabel.numAttributes() - 1);

        // Predict class
//        double predict = knn.classifyInstance(unLabel.instance(0));
//        unLabel.instance(0).setClassValue(predict);

        // Find neighbors
        NearestNeighbourSearch search = new LinearNNSearch();
        search.setDistanceFunction(new EuclideanDistance());
        search.setInstances(this.trainSet);

        Instances neighbors = search.kNearestNeighbours(unLabel.instance(0), 5);

        // Print or use neighbors as needed
//        System.out.println(unLabel.instance(0).stringValue(unLabel.classIndex()));
  

        return neighbors;
    }
}
