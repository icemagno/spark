package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class Step4 implements Serializable {
	private static final long serialVersionUID = 5L;
	
	
	public JavaPairRDD<Integer, String> run( JavaRDD<String> functionResults ) {
		
		PairFunction<String, Integer, String> pairFunction = new PairFunction<String, Integer, String>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Integer, String> call(String grafo) throws Exception {
				String[] parameters = grafo.split(",");
				Integer ordem = Integer.valueOf( parameters[4] );
				
				System.out.println("Transformando em par o grafo : " + grafo );
				// 5,16,\lambda_2 + \chi + \overline{\lambda_2},DEk,5,1,3,0,1,0,591,max,1,0,0,0,0,10,1,0,0,1,0,0,0,0,0,1,1,b7c6cc1731bf4607acdeb4713bc09c77,4,7,4.236
				
				return new Tuple2<Integer, String>( ordem, grafo );
			}

		};
		
		return functionResults.mapToPair( pairFunction );
	}
	
	/*
	public JavaPairRDD<String, Graph> run( Dataset<Row> graphs ) {
		
		PairFunction<Row, String, Graph> pairFunction = new PairFunction<Row, String, Graph>() {
			private static final long serialVersionUID = 1L;

			public Tuple2<String, Graph> call(Row row) throws Exception {

				String function = row.getAs("optifunc");
				String g6 = row.getAs("grafo");
				String biptonly = row.getAs("biptonly"); 
				String allowdiscgraphs = row.getAs("allowdiscgraphs"); 		
				String caixa1 = row.getAs("caixa1"); 		
				String adjacency = row.getAs("adjacency"); 		
				String laplacian = row.getAs("laplacian"); 		
				String slaplacian = row.getAs("slaplacian"); 		
				String maxresults = row.getAs("maxresults"); 		
				String adjacencyb = row.getAs("adjacencyb"); 		
				String laplacianb = row.getAs("laplacianb"); 		
				String slaplacianb = row.getAs("slaplacianb"); 		
				String chromatic = row.getAs("chromatic"); 		
				String chromaticb = row.getAs("chromaticb"); 		
				String click = row.getAs("click"); 		
				String clickb = row.getAs("clickb"); 		
				String largestdegree = row.getAs("largestdegree"); 		
				String numedges = row.getAs("numedges"); 		
				Integer index_id = row.getAs("index_id"); 
				Integer ordem = row.getAs("ordem"); 
				Integer grauminimo = row.getAs("grauminimo"); 
				Integer graumaximo = row.getAs("graumaximo"); 
				Integer trianglefree = row.getAs("trianglefree"); 
				Integer conexo = row.getAs("conexo"); 
				Integer bipartite = row.getAs("bipartite"); 
				Integer parameter_id = row.getAs("parameter_id"); 

				Graph graph = new Graph( index_id, function, g6, ordem, grauminimo,  graumaximo,
						trianglefree, conexo, bipartite, parameter_id, caixa1,
						adjacency, laplacian, slaplacian, allowdiscgraphs, biptonly,
						maxresults, adjacencyb, laplacianb, slaplacianb, chromatic,
						chromaticb, click, clickb, largestdegree, numedges );
				
				String key = String.valueOf( ordem ) + String.valueOf( grauminimo ) + String.valueOf( graumaximo );
		        return new Tuple2<String, Graph>( key, graph );
		    }
			
		};
		
		JavaPairRDD<String, Graph> graphsPairRDD = graphs.toJavaRDD().mapToPair( pairFunction );			
		return graphsPairRDD;
		
	}
	*/
	
}
