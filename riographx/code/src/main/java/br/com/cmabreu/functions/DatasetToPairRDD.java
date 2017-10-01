package br.com.cmabreu.functions;

import java.util.UUID;

import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Row;

public class DatasetToPairRDD implements Function<Row,String> {
	private static final long serialVersionUID = 1L;

	
	public String call(Row row) throws Exception {
		
		String serial = UUID.randomUUID().toString().replaceAll("-", "");

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
		
		Integer order_min = row.getAs("order_min"); 
		Integer order_max = row.getAs("order_max"); 
		
		int runEigsolve = 0;
		int runGeni = 0;
		if ( function.contains("lambda") || function.contains("mu") || function.contains("q_") ) {
			runEigsolve = 1;	
		}	
		
		if ( function.contains("omega") || function.contains("chi") || function.contains("SIZE") || function.contains("d_") ) {
			runGeni = 1;
		}					

		String key = String.valueOf( ordem );
		
		// 5,12,DC{,5,1,4,0,1,0,591,\lambda_2 + \chi + \overline{\lambda_2},max,1,0,0,0,0,10,1,0,0,1,0,0,0,0,0,1,1,1d02feb2575946bb9dc478b6f1b3adbc,4,7
		
		String result = key + "," + index_id + "," + function.replaceAll("\\\\", "\\\\\\\\") + "," + g6 + "," + ordem + "," + grauminimo + "," + 
				graumaximo+ "," +trianglefree+ "," +conexo+ "," +
				bipartite+ "," +parameter_id + "," +caixa1+ "," +adjacency+ "," +laplacian+ "," +slaplacian+ "," +allowdiscgraphs+ "," +
				biptonly+ "," +maxresults+ "," +adjacencyb+ "," +laplacianb+ "," +slaplacianb+ "," +chromatic+ "," +chromaticb+ "," +click+ "," +
				clickb+ "," +largestdegree+ "," +numedges+","+runGeni+","+runEigsolve+","+serial+","+order_min+","+order_max;
		
		return result;
		
		
	}

	
}
