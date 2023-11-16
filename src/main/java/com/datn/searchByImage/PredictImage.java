package com.datn.searchByImage;

import weka.classifiers.lazy.IBk;
import weka.core.Instances;

	public class PredictImage {
		public String predictImage(Instances predict, IBk knn) throws Exception {
	    	predict.setClassIndex(predict.numAttributes() - 1);
	    	
	    	double predict_label = knn.classifyInstance(predict.instance(0));
	    	predict.instance(0).setClassValue(predict_label);
	    	
	    	return predict.instance(0).stringValue(predict.classIndex());
	    	
	    	
	    }
}
