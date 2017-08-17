package com.letsprog.learning.samples.transformations;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PostgreSQLConnect {

	public static void main(String[] args) {

		try {
			Class.forName("org.postgresql.Driver");
		} catch ( Exception e ) {
			System.out.println("Problemas ao carregar o driver PostgreSQL: " + e.getMessage() );
		}
		
		System.out.println("Driver carregado.");
		
		/*
		String master = "local[*]";
		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
		JavaSparkContext context = new JavaSparkContext(sparkConf);
		*/
		
		// -------------------------------------------------------------------------------
		// YOUR CODE HERE
		// -------------------------------------------------------------------------------

		
		SparkSession spark = SparkSession
				.builder()
				.appName("Java Spark SQL basic example")
				.config("driver", "org.postgresql.Driver")
				.getOrCreate(); 		

		System.out.println("Sessão Criada.");

		Dataset<Row> jdbcDF = spark.read()
				.format("jdbc")
				.option("url", "jdbc:postgresql://192.168.25.51:5432/graphx")
				.option("dbtable", "public.graphdatabase")
				.option("user", "postgres")
				.option("password", "admin")
				.option("driver", "org.postgresql.Driver")
				.load(); 		

		jdbcDF.show(10);
		jdbcDF.printSchema();


		// -------------------------------------------------------------------------------
		spark.stop();
		//context.close();

	}	



}
