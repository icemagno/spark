package com.letsprog.learning.samples.transformations;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class ReduceAndGroupByKeySample {

	public static void main(String[] args) {
		 
		String master = "local[*]";
		
		SparkConf sparkConf = new SparkConf();
		 
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
 		JavaSparkContext context = new JavaSparkContext(sparkConf);
		 
		JavaPairRDD<String,Integer> petsRDD = JavaPairRDD.fromJavaRDD(context.parallelize(
										Arrays.asList(
											new Tuple2<String,Integer>("cat", 1),
											new Tuple2<String,Integer>("dog", 5),
											new Tuple2<String,Integer>("cat", 3)
											)
										)
									);
		
		System.out.println(petsRDD.collect().toString());
		
		JavaPairRDD<String,Integer> agedPetsRDD = petsRDD.reduceByKey((v1,v2)->Math.max(v1, v2));
		System.out.println(agedPetsRDD.collect().toString());
		
		JavaPairRDD<String,Iterable<Integer>> groupedPetsRDD = petsRDD.groupByKey();
		System.out.println(groupedPetsRDD.collect().toString());
		
		context.close();
 
	}	
	
	
	
}
