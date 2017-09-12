package br.com.cmabreu;

import java.io.Serializable;
import java.util.Iterator;

import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

public class DriverApplication implements Serializable {
	private static final long serialVersionUID = 1L;

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
		//sparkConf.setMaster("local[*]");
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
		// Coleta os grafos do banco de dados usando o indice da tabela de parametros
		Step1 stp1 = new Step1();
		Dataset<Row> graphs = stp1.run( spark, indexParameter );

		if ( graphs.count() == 0 ) {
			System.out.println("Nenhum grafo encontrado para os parametros fornecidos, ou parametros nao encontrados com indice " + indexParameter + "." );
			System.exit(0);
		}		
		

		// Segundo passo do workflow --------------------------------------------------------------------
		// Cria um Pair RDD para possibilitar a paralelização dos grafos usando uma chave agrupadora
		Step2 stp2 = new Step2();
		JavaPairRDD<String, Graph> graphsPairRDD = stp2.run( graphs );
		
		
		// Terceiro passo do workflow
		// Particiona o RDD usando a chave como agrupador		
		int numCores = context.sc().defaultParallelism();
		int numExecs = context.sc().getExecutorMemoryStatus().size();
		
		System.out.println( "Cores: " + numCores + "   Executors: " + numExecs );
		
		JavaPairRDD<String, Graph> partitionedRdd = graphsPairRDD.partitionBy( new HashPartitioner( numExecs ) );
		
		

		// JavaPairRDD<String, Graph> parallelizedRdd = context.parallelizePairs( graphsPairRDD.collect() );
		
		
		
		printPartitions( partitionedRdd );

		
		spark.stop();
		context.close();
	}

	
	
	private void printPartitions( JavaPairRDD<String, Graph> theRdd ) {
		
		VoidFunction <Iterator< Tuple2<String, Graph> > > f = new VoidFunction <Iterator< Tuple2<String, Graph> > >() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call( Iterator< Tuple2<String, Graph> > arg0 ) throws Exception {
				System.out.println("--------------------------------------");
				
				while( arg0.hasNext() ) {
					Tuple2<String, Graph> tuple = arg0.next();
	                System.out.println( tuple._1 + "  " + tuple._2.getG6() );
	            }
				
			}
			
		};
		theRdd.foreachPartition(f);
		
	}
}


