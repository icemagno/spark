package br.com.cmabreu;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DriverApplication {

	public void run( String indexParameter ) {
		
		// Carrega o driver PostgreSQL
		try {
			Class.forName("org.postgresql.Driver");
		} catch ( Exception e ) {
			System.out.println("Problemas ao carregar o driver PostgreSQL: " + e.getMessage() );
			System.exit(0);
		}		
		

		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("Portal RioGraphX");
		sparkConf.set("driver", "org.postgresql.Driver");
		JavaSparkContext context = new JavaSparkContext(sparkConf);		
		

		SparkSession spark = new SparkSession( context.sc() );
		/*
		SparkSession spark = SparkSession
				.builder()
				.appName("Portal RioGraphX")
				.config("driver", "org.postgresql.Driver")
				.getOrCreate(); 		
		*/

		
		// Primeiro passo do workflow -------------------------------------------------------------------
		Step1 stp1 = new Step1();
		Dataset<Row> graphs = stp1.run( spark, indexParameter );

		if ( graphs.count() == 0 ) {
			System.out.println("Nenhum grafo encontrado para os parametros fornecidos, ou parametros nao encontrados com indice " + indexParameter + "." );
			System.exit(0);
		}		
		

		// Segundo passo do workflow --------------------------------------------------------------------
		Step2 stp2 = new Step2();
		JavaPairRDD<String, Graph> graphsPairRDD = stp2.run( graphs );
		
		JavaPairRDD<String, Graph> parallelizedRdd = context.parallelizePairs( graphsPairRDD.collect() );
		
		parallelizedRdd.collect();
		System.out.println( parallelizedRdd.partitions().size() );
		
		
		spark.stop();
		context.close();
	}
	
	
}
