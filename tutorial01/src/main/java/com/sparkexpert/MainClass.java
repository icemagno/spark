package com.sparkexpert;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.JdbcRDD;

public class MainClass {

	public static void main(String[] args) {
		String master = "local[*]"; // .setMaster("spark://sparkmaster:7077") 
		SparkConf sparkConf = new SparkConf();
		 
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
 		JavaSparkContext context = new JavaSparkContext(sparkConf);
 		// -------------------------------------------------------------------------------
 		// YOUR CODE HERE
 		// -------------------------------------------------------------------------------
 		
 		//DbConnection dbConnection = new DbConnection(MYSQL_DRIVER, "jdbc:postgresql://10.5.115.122:5432/mclm", "postgres", "admin");
 		
 		
 		//JdbcRDD<Object[]> jdbcRDD = new JdbcRDD<>(sc, dbConnection, "select * from employees where emp_no >= ? and emp_no <= ?",
        //        10001, 499999, 10, new MapResult(), ClassManifestFactory$.MODULE$.fromClass(Object[].class)); 		

 		
 		
 		
 		// -------------------------------------------------------------------------------
		context.close();
	}	
	
	
	
}
