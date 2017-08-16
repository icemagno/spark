package com.letsprog.learning.samples.transformations;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class UnionSample {

	public static void main(String[] args) {
		 
		String master = "local[*]";
		
		SparkConf sparkConf = new SparkConf();
		 
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
 		JavaSparkContext context = new JavaSparkContext(sparkConf);
 
		JavaRDD<Integer> numbers1RDD = context.parallelize(Arrays.asList(0,5,3));
		JavaRDD<Integer> numbers2RDD = context.parallelize(Arrays.asList(8,9,3));
		
		JavaRDD<Integer> unionRDD = numbers1RDD.union(numbers2RDD);
		System.out.println(unionRDD.collect().toString());
		
		JavaRDD<Integer> distinctRDD = unionRDD.distinct();
		System.out.println(distinctRDD.collect().toString());
		
		context.close();
 
	}	
	
	
	
}
