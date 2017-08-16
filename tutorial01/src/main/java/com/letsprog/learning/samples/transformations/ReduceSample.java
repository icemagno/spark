package com.letsprog.learning.samples.transformations;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class ReduceSample {

	public static void main(String[] args) {
		 
		String master = "local[*]";
		
		SparkConf sparkConf = new SparkConf();
		 
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
 		JavaSparkContext context = new JavaSparkContext(sparkConf);
 
		JavaRDD<Integer> numbersRDD = context.parallelize(Arrays.asList(8,0,5,3,10,6));
		
		long total = numbersRDD.reduce((n1,n2)->n1+n2);
		System.out.println(total);
		
		context.close();
 
	}	
	
	
	
}
