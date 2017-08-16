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
				.option("url", "jdbc:postgresql://10.5.115.122:5432/mclm")
				.option("dbtable", "public.node_data")
				.option("user", "postgres")
				.option("password", "admin")
				.load(); 		

		//jdbcDF.show(10);
		//jdbcDF.printSchema();
		// jdbcDF.sqlContext().sql
		
		jdbcDF.select("layername", "layeralias").write().format("json").save("view_estados_brasil.json");


		/*
	    Dataset<Row> peopleDF =
	    	      spark.read().format("json").load("examples/src/main/resources/people.json");		
	    peopleDF.write().bucketBy(42, "layername").sortBy("id_node_data").saveAsTable("people_bucketed");
		*/

		// -------------------------------------------------------------------------------
		spark.stop();
		context.close();

	}	



}
