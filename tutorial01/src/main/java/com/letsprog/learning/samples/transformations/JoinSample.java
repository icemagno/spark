package com.letsprog.learning.samples.transformations;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class JoinSample {

	public static void main(String[] args) {

		String master = "local[*]";

		SparkConf sparkConf = new SparkConf();

		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
		JavaSparkContext context = new JavaSparkContext(sparkConf);

		JavaPairRDD<String,String> visitsRDD = JavaPairRDD.fromJavaRDD(context.parallelize(
				Arrays.asList(
						new Tuple2<String,String>("index.html", "1.2.3.4"),
						new Tuple2<String,String>("about.html", "3.4.5.6"),
						new Tuple2<String,String>("index.html", "1.3.3.1")
						)
				)
				);

		System.out.println(visitsRDD.collect().toString());

		JavaPairRDD<String,String> pageNamesRDD = JavaPairRDD.fromJavaRDD(context.parallelize(
				Arrays.asList(
						new Tuple2<String,String>("index.html", "Home"),
						new Tuple2<String,String>("about.html", "About")
						)
				)
				);

		System.out.println(pageNamesRDD.collect().toString());

		JavaPairRDD<String,Tuple2<String,String>> joinRDD = visitsRDD.join(pageNamesRDD);
		System.out.println(joinRDD.collect().toString());

		context.close();

	}	



}
