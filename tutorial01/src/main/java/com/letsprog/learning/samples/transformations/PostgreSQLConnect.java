package com.letsprog.learning.samples.transformations;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
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
				.appName("Teste de Conexao com PostgreSQL")
				.config("driver", "org.postgresql.Driver")
				.getOrCreate(); 		

		System.out.println("Sessão Criada.");

		Dataset<Row> jdbcDF = spark.read()
				.format("jdbc")
				.option("url", "jdbc:postgresql://192.168.25.103:5432/graphx")
				.option("dbtable", "public.graphdatabase")
				.option("user", "postgres")
				.option("password", "admin")
				.option("driver", "org.postgresql.Driver")
				.load(); 		

		
		/*
		Function<String,Boolean> function = new Function<String,Boolean>() {
			public Boolean call( String x ) {
				return x.contains("");
			}
		};
		*/
		
		// https://github.com/high-performance-spark/high-performance-spark-examples/blob/master/src/main/java/com/highperformancespark/examples/dataframe/JavaHappyPandas.java
		
		SQLContext sqlContext = jdbcDF.sqlContext();
		jdbcDF.createOrReplaceTempView("graphdatabase");
		
		Dataset<Row> miniPandas = sqlContext.sql("SELECT * FROM graphdatabase WHERE grauminimo = 2 and graumaximo = 3 and ordem = 6");
		
		
		//jdbcDF.javaRDD().filter( function );
		//jdbcDF.printSchema();
		//jdbcDF.write().json("/graphx/teste");
		
		miniPandas.show( 200 );

		// -------------------------------------------------------------------------------
		spark.stop();
		//context.close();

	}	

	/*

		+--------+-----+-----+----------+----------+------------+------+---------+
		|index_id|grafo|ordem|grauminimo|graumaximo|trianglefree|conexo|bipartite|
		+--------+-----+-----+----------+----------+------------+------+---------+
		|       1|  D??|    5|         0|         0|           1|     0|        1|
		|       2|  D?_|    5|         0|         1|           1|     0|        1|
		|       3|  D?o|    5|         0|         2|           1|     0|        1|
		|       4|  D?w|    5|         0|         3|           1|     0|        1|
		|       5|  D?{|    5|         1|         4|           1|     1|        1|
		|       6|  DCO|    5|         0|         1|           1|     0|        1|
		|       7|  DCo|    5|         0|         2|           1|     0|        1|
		|       8|  DCW|    5|         1|         2|           1|     0|        1|
		|       9|  DCc|    5|         0|         2|           0|     0|        0|
		|      10|  DCw|    5|         1|         3|           1|     1|        1|
		+--------+-----+-----+----------+----------+------------+------+---------+

	 */

}
