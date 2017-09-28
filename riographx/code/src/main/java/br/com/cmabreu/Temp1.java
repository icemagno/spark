package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class Temp1 implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public JavaPairRDD<Integer, Graph> run( JavaRDD<String> functionResults ) {
		
		PairFunction<String, Integer, Graph> pairFunction = new PairFunction<String, Integer, Graph>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<Integer, Graph> call(String line) throws Exception {
				// 625,181,\lambda_2 + \chi + \overline{\lambda_2},ET~w,6,2,5,0,1,0,591,min,1,0,0,0,0,10,1,0,0,1,0,0,0,0,0,1,1,3ed7254eafbd4a2ba581b7b0ca952b1b,1.5451000000000001
				
				String[] parameters = line.split(",");

				//String rddKey = parameters[0];
				Integer index_id = Integer.valueOf( parameters[1] );
				String function = parameters[2];
				String g6 = parameters[3];
				Integer ordem = Integer.valueOf( parameters[4] );
				Integer grauminimo = Integer.valueOf( parameters[5] );
				Integer graumaximo = Integer.valueOf( parameters[6] );
				Integer trianglefree = Integer.valueOf( parameters[7]);
				Integer conexo = Integer.valueOf( parameters[8] );
				Integer bipartite = Integer.valueOf( parameters[9] );
				Integer parameter_id = Integer.valueOf(parameters[10]); 
				String caixa1 = parameters[11];
				String adjacency = parameters[12];
				String laplacian = parameters[13];
				String slaplacian = parameters[14];
				String allowdiscgraphs = parameters[15];
				String biptonly = parameters[16];
				String maxresults = parameters[17];
				String adjacencyb = parameters[18];
				String laplacianb = parameters[19];
				String slaplacianb = parameters[20];
				String chromatic = parameters[21];
				String chromaticb = parameters[22];
				String click = parameters[23];
				String clickb = parameters[24];
				String largestdegree = parameters[25];
				String numedges = parameters[26];
				//String runGeni = parameters[27];
				//String runEigsolve = parameters[28];
				String serial = parameters[29];				
				String functionResult = parameters[30];
				
				Graph graph = new Graph( index_id, function, g6, ordem, grauminimo,  graumaximo,
						trianglefree, conexo, bipartite, parameter_id, caixa1,
						adjacency, laplacian, slaplacian, allowdiscgraphs, biptonly,
						maxresults, adjacencyb, laplacianb, slaplacianb, chromatic,
						chromaticb, click, clickb, largestdegree, numedges );
				
				graph.setSerial( serial );
				graph.setFunctionResult(functionResult);
				
				return new Tuple2<Integer, Graph>( ordem, graph );
			}
			
		};
		
		
		JavaPairRDD<Integer, Graph> graphsPairRDD = functionResults.mapToPair( pairFunction );			
		return graphsPairRDD;
		
	}

}
