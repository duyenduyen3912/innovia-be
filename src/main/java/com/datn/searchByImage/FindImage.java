package com.datn.searchByImage;

import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.LinearNNSearch;
import weka.core.neighboursearch.NearestNeighbourSearch;

	public class FindImage {
		public Instances findNeighborsInRadius(Instances queryData, double radius, Instances createInstances) throws Exception {
	        queryData.setClassIndex(queryData.numAttributes() - 1);

	        // Set up NearestNeighbourSearch
	        NearestNeighbourSearch search = new LinearNNSearch();
	        search.setDistanceFunction(new EuclideanDistance());
	        search.setInstances(createInstances);
  
	        Instances neighborsInstances = search.kNearestNeighbours(queryData.instance(0), 20);
	        Instances neighbors = new Instances(createInstances, 0);

	        for (int i = 0; i < neighborsInstances.size(); i++) {
	            Instance neighborInstance = neighborsInstances.get(i);
	            double distance = search.getDistances()[i];
	            if(distance == 0) {
	            	neighbors.add(neighborInstance);
	            	return neighbors;
	            } else if (distance <= radius) {
	                neighbors.add(neighborInstance);
	            }
	        }
	        
	   
	        return neighbors;
	    }
}
