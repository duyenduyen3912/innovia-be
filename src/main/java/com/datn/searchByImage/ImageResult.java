package com.datn.searchByImage;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageResult {

    public List<String> Result(Instances instancesB) {
    	
        // refer
        String fileAPath = "C:\\Users\\ngocl\\eclipse-workspace\\datn-be\\data\\extracted.arff";

        
        List<String> image_result = new ArrayList<>();
        ImageProcess imageProcess = new ImageProcess();

        try {
            Instances instancesA = readArffFile(fileAPath);

            for (int i = 0; i < instancesB.numInstances(); i++) {
                Instance instanceB = instancesB.instance(i);
                
                
                if (hasMatchingInstance(instanceB, instancesA) != null) {
                	Instance instanceA = hasMatchingInstance(instanceB, instancesA);
                   
                    String imageName = instanceA.stringValue(0);
                    String imageFinal = imageProcess.trimPath(imageName);
                    image_result.add(imageFinal);
                } else {
                    System.out.println("No matching instance found for instance " + (i + 1) + " in file B.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image_result;
    }

    private static Instances readArffFile(String filePath) throws Exception {
    	DataSource source = new DataSource(filePath);
        Instances data = source.getDataSet();
        return data;
    }

    private static Instance hasMatchingInstance(Instance queryInstance, Instances referenceInstances) {
        for (int i = 0; i < referenceInstances.numInstances(); i++) {
            Instance candidateInstance = referenceInstances.instance(i);

            // So sánh các giá trị đặc trưng giữa hai instance
            if (instancesMatch(queryInstance, candidateInstance)) {
                return candidateInstance;
            }
        }
        return null;
    }

    private static boolean instancesMatch(Instance instance1, Instance instance2) {
    	int j = 2;
        for (int i = 1; i < instance1.numAttributes(); i++) {
            if (instance1.value(i) != instance2.value(j)) {
                return false;
            }
            j++;
        }
        return true;
    }
}
