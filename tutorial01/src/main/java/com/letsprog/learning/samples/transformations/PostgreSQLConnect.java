package com.letsprog.learning.samples.transformations;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PostgreSQLConnect {

	public static void main(String[] args) {

		String master = "local[*]";
		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
		JavaSparkContext context = new JavaSparkContext(sparkConf);

		// -------------------------------------------------------------------------------
		// YOUR CODE HERE
		// -------------------------------------------------------------------------------

		SparkSession.builder().master("local[*]").appName("Spark2JdbcDs").getOrCreate();
		
		SparkSession spark = SparkSession
				.builder()
				.appName("Java Spark SQL basic example")
				.config("spark.some.config.option", "some-value")
				.getOrCreate(); 		


		Dataset<Row> jdbcDF = spark.read()
				.format("jdbc")
				.option("url", "jdbc:postgresql://192.168.25.51:5432/graphx")
				.option("dbtable", "public.graphdatabase")
				.option("user", "postgres")
				.option("password", "admin")
				.load(); 		

		jdbcDF.show(10);
		jdbcDF.printSchema();


		// -------------------------------------------------------------------------------
		spark.stop();
		context.close();

	}	



}
