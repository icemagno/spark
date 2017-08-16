package com.letsprog.learning.samples.transformations;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkSample {

	public static void main(String[] args) {
		String master = "local[*]"; // .setMaster("spark://sparkmaster:7077") 
		SparkConf sparkConf = new SparkConf();
		 
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
 		JavaSparkContext context = new JavaSparkContext(sparkConf);
 		// -------------------------------------------------------------------------------
 		// YOUR CODE HERE
 		// -------------------------------------------------------------------------------
 		

 		
 		
 		

 		
 		
 		
 		// -------------------------------------------------------------------------------
		context.close();
	}	
	
	
	
}
