package com.datn.searchByImage;

import weka.classifiers.lazy.IBk;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import java.util.Random;



public class Knn extends KnowledgeModel {
    private IBk knn;
    Evaluation eval;
    public Knn(Instances trainingData, String m_opts, String d_opts) throws Exception {
        super(trainingData, m_opts, d_opts);
    }

    public void buildKNN(Instances trainingData) throws Exception {
        this.trainSet = trainingData;
        this.trainSet.setClassIndex(this.trainSet.numAttributes() - 1);
        this.knn = new IBk();
        knn.setOptions(model_options);
        knn.buildClassifier(this.trainSet);
        SerializationHelper.write("data\\knn_model.model", knn);
    }
    
    public void evaluateKNN(Instances test) throws Exception {
        this.testSet = test;
        this.testSet.setClassIndex(this.testSet.numAttributes()-1);
        Random rd = new Random(1);
        int folds = 10;
        eval = new Evaluation(this.trainSet);
        eval.crossValidateModel(knn, this.testSet, folds, rd);
        System.out.println(eval.toSummaryString("\n -----Kết quả đánh giá mô hình nhận diện loại hình ảnh-----\n", false));
    }

    
    public String predictImage(Instances predict  ) throws Exception {
    	
    	predict.setClassIndex(predict.numAttributes() - 1);
        // predict
        double predict_label = knn.classifyInstance(predict.instance(0));
        predict.instance(0).setClassValue(predict_label);
    	
    	
    	return predict.instance(0).stringValue(predict.classIndex());
    }
    

    
}
