package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkFiles;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

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
		

		// Inicialização...
		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("Portal RioGraphX");
		//sparkConf.setMaster("local[*]");
		sparkConf.set("driver", "org.postgresql.Driver");
		JavaSparkContext context = new JavaSparkContext(sparkConf);		
		SparkSession spark = new SparkSession( context.sc() );

		int numCores = context.sc().defaultParallelism();
		//int numExecs = context.sc().getExecutorMemoryStatus().size();
		//System.out.println( "Cores: " + numCores + "   Executors: " + numExecs );
		
		//context.sc().addFile("hdfs://sparkmaster:9000/riographx/teste.jar");
		context.sc().addFile("hdfs://sparkmaster:9000/riographx/teste.sh");
		// ----------------------------------------------------------------------------------------------
		
		
		// Primeiro passo do workflow -------------------------------------------------------------------
		// Coleta os grafos do banco de dados usando o indice da tabela de parametros
		Step1 stp1 = new Step1();
		Dataset<Row> graphs = stp1.run( spark, indexParameter );

		if ( graphs.count() == 0 ) {
			System.out.println("Nenhum grafo encontrado para os parametros fornecidos, ou parametros nao encontrados com indice " + indexParameter + "." );
			System.exit(0);
		}		
		// ----------------------------------------------------------------------------------------------
		

		// Segundo passo do workflow --------------------------------------------------------------------
		// Cria um Pair RDD para possibilitar a paralelização dos grafos usando uma chave agrupadora
		Step2 stp2 = new Step2();
		JavaPairRDD<String, Graph> graphsPairRDD = stp2.run( graphs );
		// ----------------------------------------------------------------------------------------------
		
		
		
		// Terceiro passo do workflow
		// Particiona o RDD usando a chave como agrupador		
		JavaPairRDD<String, Graph> partitionedRdd = graphsPairRDD.partitionBy( new HashPartitioner( 2 * numCores ) );
		// ----------------------------------------------------------------------------------------------

		
		
		// Quarto Passo do workflow
		// Para cada elemento do RDD ...
		
		//String external = "java -jar " + SparkFiles.get("teste.jar");
		String external = "sh " + SparkFiles.get("teste.sh");
		
		System.out.println( external );
		
		//JavaRDD<String> output = partitionedRdd.pipe("java -jar /usr/lib/riographx/teste.jar");
		JavaRDD<String> output = partitionedRdd.pipe( external );
		VoidFunction<String> f = new VoidFunction<String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(String arg0) throws Exception {
				System.out.println("Output RDD: " + arg0 );
			}
		};
		output.foreach(f);
		
		
		
		// TESTES ---------------------------------------------------------------------------------------
		// Dataset<Row> repartRdd = graphs.repartition( numCores );
		// printDatasetPartitions( repartRdd );
		// JavaPairRDD<String, Graph> parallelizedRdd = context.parallelizePairs( graphsPairRDD.collect() );
		// printPairRddPartitions( partitionedRdd );

		
		// ----------------------------------------------------------------------------------------------
		spark.stop();
		context.close();
	}

	/*
	private void printDatasetPartitions( Dataset<Row> repartRdd ){
		
		ForeachPartitionFunction<Row> f = new ForeachPartitionFunction<Row>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call( Iterator<Row> arg0) throws Exception {
				System.out.println("--------------------------------------");
				
				while( arg0.hasNext() ) {
					Row row = arg0.next();
	                System.out.println( row.getAs("index_id") + "  " + row.getAs("grafo") );
	            }				
			}
		};
		
		repartRdd.foreachPartition( f );
	}

	
	private void foreachGraph( JavaPairRDD<String, Graph> theRdd ) {
		VoidFunction <Tuple2<String, Graph>> f = new VoidFunction < Tuple2<String, Graph> >() {

			@Override
			public void call(Tuple2<String, Graph> tuple) throws Exception {
				Graph graph = tuple._2;
				
			}
			
		};
		
		
	}
	
	private void printPairRddPartitions( JavaPairRDD<String, Graph> theRdd ) {
		
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
	*/
	
}


