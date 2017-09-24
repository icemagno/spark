package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DriverApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	public void run( String indexParameter, String workDir, String sageScript ) {
		
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

		//int numCores = context.sc().defaultParallelism();
		//int numWorkers = context.sc().executorMemory();
		

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
		JavaRDD<String> preparedGraphs = new Step2().run(graphs);		
		// ----------------------------------------------------------------------------------------------
		
		
		/** 			Terceiro passo do workflow 												**/
		// Para cada grafo chama o programa externo passado no terceiro parâmetro (sage.sh).
		//		que é encarregado de executar o GENI e/ou o EIGSOLVE dependendo dos parametros
		// 		passados pelo usuário.
		// ----------------------------------------------------------------------------------------------
		Step3 stp4 = new Step3();
		JavaRDD<String> functionResults = stp4.run( preparedGraphs, workDir, sageScript );
		
		
			functionResults.foreach( new VoidFunction<String>(){
				private static final long serialVersionUID = 1L;
				@Override
				public void call(String t) throws Exception {
					System.out.println( "Resultado functionResults: " + t );
				}
			});
		
		// ----------------------------------------------------------------------------------------------

		
		
		/** 			Quarto passo do workflow 												**/

		
		
		
		/** 			Quinto passo do workflow 												**/
		// Insere o resultado da função de avaliação no objeto do grafo  
		//Step5 stp5 = new Step5();
		//JavaPairRDD<Integer, Graph> results = stp5.run(functionResults);
		// ----------------------------------------------------------------------------------------------

		
		
		
		/** 			Sexto passo do workflow 												**/
		// Agrupa o RDD usando a ordem do grafo (chave do RDD) como indice		
		// ----------------------------------------------------------------------------------------------
		//JavaPairRDD<Integer, Iterable<Graph> > agrupadoPorOrdemRdd = results.groupByKey( 
		//	new HashPartitioner( numWorkers -1 ) 
		//);
		// ----------------------------------------------------------------------------------------------		
		
		

		
		//Step6 stp6 = new Step6();
		//JavaPairRDD<Integer, List<Graph> > temp = stp6.run( agrupadoPorOrdemRdd );
		
		
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
		
		//printEvaluatedRDD( temp );
		
		
		/*		
				
		insert into select_out ( id_instance, id_experiment, id_activity, optifunc, evaluatedvalue, maxresults, caixa1, gorder, function, paramid, grafo ) 
			select  optifunc, evaluatedvalue, maxresults, caixa1, gorder, function, paramid, grafo from
			( WITH l AS 
				( SELECT CASE WHEN caixa1 = 'min' THEN maxresults ELSE 0 END AS lim_a, CASE WHEN caixa1 = 'max' THEN maxresults ELSE 0 END AS lim_d FROM 
					evaluate_out where id_experiment = %ID_EXP% LIMIT 1 )
				( select * from ( select eo.*, sp.id_instance, row_number() over ( partition by eo.gorder order by CAST( eo.evaluatedvalue as float) asc) 
					as rownum from evaluate_out eo join spectral_parameters sp on sp.id_instance = eo.paramid and sp.gorder::text = eo.gorder::text where 
					eo.id_experiment = %ID_EXP% ) tmp where rownum <= ( SELECT lim_a FROM l ) ) union all ( select * from ( select eo.*, sp.id_instance, 
					row_number() over (partition by eo.gorder order by CAST(eo.evaluatedvalue as float) desc) as rownum from evaluate_out eo join 
					spectral_parameters sp on sp.id_instance = eo.paramid and sp.gorder::text = eo.gorder::text where eo.id_experiment = %ID_EXP% ) 
					tmp where rownum <= (SELECT lim_d FROM l) ) ) as t1 where id_experiment = %ID_EXP%  
				
		*/		

		
		
		
		
		/** 			Fim do workflow															**/
		//List< Tuple2<Integer, Graph> > fim = results.collect();
		//for( Tuple2<Integer, Graph> ss : fim ) {
		//	System.out.println( ( (Graph) ss._2 ).toString() );
		//}
		
		
		
		spark.stop();
		context.close();
	}


	/*
	private void printEvaluatedRDD( JavaPairRDD<Integer, List<Graph> > theRdd ) {
		
		VoidFunction <Iterator< Tuple2<Integer, List<Graph>> > > f = new VoidFunction <Iterator< Tuple2<Integer, List<Graph>> > >() {
			private static final long serialVersionUID = 1L;

			@Override
			public void call( Iterator< Tuple2<Integer, List<Graph>> > arg0 ) throws Exception {
				System.out.println("------------   NEW PARTITION --------------------------");
				
				while( arg0.hasNext() ) {
					Tuple2<Integer, List<Graph> > tuple = arg0.next();
					for( Graph grafo : tuple._2 ) {
		                System.out.println( "Ordem: " + tuple._1 + " Valor: " + grafo.getFunctionResult() );
					}
	            }
				
			}
			
		};
		theRdd.foreachPartition(f);
		
	}
	*/
	
	
	/*
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


