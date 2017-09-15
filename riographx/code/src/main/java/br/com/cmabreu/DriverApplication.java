package br.com.cmabreu;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkFiles;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
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
		
		// Adiciona o script sage.sh ao cluster. Já deverá existir no caminho HDFS abaixo.
		context.sc().addFile("hdfs://sparkmaster:9000/riographx/sage.sh");
		// ----------------------------------------------------------------------------------------------

		/**
		 * 			EXECUÇÃO DO WORKFLOW
		 **/
		
		
		/** 			Primeiro passo do workflow 												**/
		// Coleta os grafos do banco de dados usando o indice da tabela de parametros
		// ----------------------------------------------------------------------------------------------
		Step1 stp1 = new Step1();
		Dataset<Row> graphs = stp1.run( spark, indexParameter );
		// ----------------------------------------------------------------------------------------------


		/** 			Segundo passo do workflow 												**/
		// Cria um Pair RDD para possibilitar a paralelização dos grafos usando uma chave agrupadora
		// 		e também para criar objetos Java com os dados dos grafos. Evita a maipulação dos atributos 
		// 		individualmente. Cada objeto Graph é identificado unicamente pelo atributo "index_id".
		// ----------------------------------------------------------------------------------------------
		Step2 stp2 = new Step2();
		JavaPairRDD<String, Graph> graphsPairRDD = stp2.run( graphs );
		// ----------------------------------------------------------------------------------------------
		
		
		/** 			Terceiro passo do workflow 												**/
		// Particiona o RDD usando a chave como agrupador		
		// ----------------------------------------------------------------------------------------------
		JavaPairRDD<String, Graph> partitionedRdd = graphsPairRDD.partitionBy( 
			new HashPartitioner( 2 * numCores ) 
		);
		// ----------------------------------------------------------------------------------------------

		
		
		/** 			Quarto passo do workflow 												**/
		// Para cada elemento do RDD ( um grafo "Graph" ) chama o programa externo "sage.sh"
		//		que é encarregado de executar o GENI e/ou o EIGSOLVE dependendo dos parametros
		// 		passados pelo usuário.
		// O resultado é um conjunto de arquivos que serão usados pelo "evaluate". 
		// ----------------------------------------------------------------------------------------------
		//Step4 stp4 = new Step4();
		//JavaRDD<String> output = stp4.run( partitionedRdd );
		
		String external = "sh " + SparkFiles.get("sage.sh");
		JavaRDD<String> output = partitionedRdd.pipe( "sh /home/magno/riographx/sage.sh" );		
		// ----------------------------------------------------------------------------------------------
		
		
		/** 			Fim do workflow															**/
		List<String> fim = output.collect();
		System.out.println( fim.toString() );

		
		
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


