package br.com.cmabreu;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import br.com.cmabreu.functions.DatasetToPairRDD;
import br.com.cmabreu.functions.KBestGraphs;
import br.com.cmabreu.functions.LineToGraphObject;
import br.com.cmabreu.functions.ToOrderPairRDD;
import scala.Tuple2;

public class DriverApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	public void run( String indexParameter, String workDir, String sageScript, String showgScript ) {
		
		// Carrega o driver PostgreSQL
		try {
			Class.forName("org.postgresql.Driver");
		} catch ( Exception e ) {
			System.out.println("Problemas ao carregar o driver PostgreSQL: " + e.getMessage() );
			System.exit(0);
		}		
		

		// Inicialização...
		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("Portal RioGraphX v1.0");
		//sparkConf.setMaster("local[*]");
		sparkConf.set("driver", "org.postgresql.Driver");
		JavaSparkContext context = new JavaSparkContext(sparkConf);		
		SparkSession spark = new SparkSession( context.sc() );

		int numCores = context.sc().defaultParallelism();
		int numWorkers = context.sc().executorMemory();
		

		/**
		 * 			EXECUÇÃO DO WORKFLOW
		 **/
		
		
		/** 			Primeiro passo do workflow 												**/
		// Coleta os grafos do banco de dados usando o indice da tabela de parametros
		// ----------------------------------------------------------------------------------------------
		Dataset<Row> graphs = new Step1().run( spark, indexParameter );
		// ----------------------------------------------------------------------------------------------


		
		
		
		/** 			Segundo passo do workflow 												**/
		// Acrescenta um numero de série e informações de execução do SAGE/EIGEN na linha de parametros.
		// ----------------------------------------------------------------------------------------------
		JavaRDD<String> preparedGraphs = graphs.toJavaRDD().map( new DatasetToPairRDD() );
		// ----------------------------------------------------------------------------------------------
		
		
		//preparedGraphs.repartition( numWorkers );
		
		
		/** 			Terceiro passo do workflow 												**/
		// Para cada grafo chama o programa externo passado no terceiro parâmetro (sage.sh).
		//		que é encarregado de executar o GENI e/ou o EIGSOLVE dependendo dos parametros
		// 		passados pelo usuário.
		// ----------------------------------------------------------------------------------------------
		String external = workDir + "/" + sageScript;
		JavaRDD<String> functionResults = preparedGraphs.pipe( external );
		// ----------------------------------------------------------------------------------------------

		
		
		
		
		/** 			Quarto passo do workflow 												**/
		// Cria um PairRDD com os grafos usando o numero de ordem como chave.
		JavaPairRDD<Integer, String> rddMapeado = functionResults.mapToPair( new ToOrderPairRDD() );
		
		
		
		

		
		
		/** 			Quinto passo do workflow 												**/
		// Agrupa o RDD usando a ordem do grafo (chave do RDD) como indice		
		// ----------------------------------------------------------------------------------------------
		JavaPairRDD<Integer, Iterable<String> > agrupadoPorOrdemRdd = rddMapeado.groupByKey( 
			new HashPartitioner( numCores ) 
		);
		// ----------------------------------------------------------------------------------------------		


		
		
		
		
	
		/** 			Sexto passo do workflow 												**/
		// Seleciona os K-Melhores grafos por grupo
		JavaPairRDD<Integer, List<String> > kBestGraphs = agrupadoPorOrdemRdd.mapToPair( new KBestGraphs() );
		

		
		
		
		
		
		
		/** 			Sétimo passo do workflow 												**/
		// Converte a String contendo os parâmetros do grafo em uma lista
		// de objetos tipo Graph
		//JavaPairRDD<Integer, Graph> graphsPairRDD = functionResults.mapToPair( new LineToGraphObject() );
		
		
		
		
		
		/*
		VoidFunction<String> ff = new VoidFunction<String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call(String t) throws Exception {
				System.out.println( t );
			}
		};
		showgResult.foreach( ff );
		*/
		
		// PASSO 7: SHOWG
		// pipe("/usr/lib/riographx/nauty24r2/showg -A -q");
		// Input (stdin):
		//  	E?bw ( o grafo )
		// Output (stdout):
		/*
				6
				0 0 0 0 1 1
				0 0 0 0 0 1
				0 0 0 0 0 1
				0 0 0 0 0 1
				1 0 0 0 0 1
				1 1 1 1 1 0
		 */
		

		// PASSO 8: TXT2DOT
		// Usar o wrapper TXT2DOT antigo em Java.
		
		
		// PASSO 9: GRAPHVIZ
		// "dot -Tgif -O " + dotInput
		

		
		
		
		/** 			Fim do workflow															**/
		//List< Tuple2<Integer, Graph> > fim = results.collect();
		//for( Tuple2<Integer, Graph> ss : fim ) {
		//	System.out.println( ( (Graph) ss._2 ).toString() );
		//}
		
		
		
		spark.stop();
		context.close();
	}


	
	private void printEvaluatedRDD( JavaPairRDD<Integer, List<String> > theRdd ) {
		
		VoidFunction <Iterator< Tuple2<Integer, List<String>> > > f = new VoidFunction <Iterator< Tuple2<Integer, List<String>> > >() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call( Iterator< Tuple2<Integer, List<String>> > arg0 ) throws Exception {
				System.out.println("------------   NEW PARTITION --------------------------");
				
				while( arg0.hasNext() ) {
					Tuple2<Integer, List<String> > tuple = arg0.next();
					for( String grafo : tuple._2 ) {
		                System.out.println( "Ordem: " + tuple._1 + " Valor: " + grafo );
					}
	            }
				
			}
			
		};
		theRdd.foreachPartition(f);
		
	}
	
	
	
	/*
	private void printPairRddPartitions( JavaPairRDD<Integer, String> theRdd ) {
		
		VoidFunction <Iterator< Tuple2<Integer, String> > > f = new VoidFunction <Iterator< Tuple2<Integer, String> > >() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call( Iterator< Tuple2<Integer, String> > arg0 ) throws Exception {
				System.out.println("--------------------------------------");
				
				while( arg0.hasNext() ) {
					Tuple2<Integer, String> tuple = arg0.next();
	                System.out.println( tuple._1 + "  " + tuple._2 );
	            }
				
			}
			
		};
		theRdd.foreachPartition(f);
		
	}
	*/
	
	
	
	
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
	*/
	
	
	
}


