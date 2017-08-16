package com.letsprog.learning.samples.transformations;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class CollectSample {

	public static void main(String[] args) {
		 
		String master = "local[*]";
		
		SparkConf sparkConf = new SparkConf();
		 
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
 		JavaSparkContext context = new JavaSparkContext(sparkConf);
 
		JavaRDD<Integer> numbersRDD = context.parallelize(Arrays.asList(0,5,3));
		
		List<Integer> numbersList = numbersRDD.collect();
		System.out.println(numbersList.toString());
		
		context.close();
 
	}	
	
	
	
}
