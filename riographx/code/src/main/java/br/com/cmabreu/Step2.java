package br.com.cmabreu;

import java.io.Serializable;
import java.util.UUID;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/*
 * 	Segundo passo do workflow
 * 
 * 		Recebe um Dataset ( Dataset<Row> ).
 * 		Cria um PairRDD tendo como chave uma string com os valores ordem, grauminimo e graumaximo
 * 		concatenados.
 * 
 * 		Cria uma instância de Graph() para cada grafo encontrado
 *  	
 *	 	Retorna um JavaPairRDD<String, Graph>
 * 
 * 	// https://stackoverflow.com/questions/31424396/how-does-hashpartitioner-work
 */

public class Step2 implements Serializable {
	private static final long serialVersionUID = 5L;
	
	
	public JavaRDD<String> run( Dataset<Row> graphs ) {
		
		Function<Row,String> f = new Function<Row,String>() {
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
				
				int runEigsolve = 0;
				int runGeni = 0;
				if ( function.contains("lambda") || function.contains("mu") || function.contains("q_") ) {
					runEigsolve = 1;	
				}	
				
				if ( function.contains("omega") || function.contains("chi") || function.contains("SIZE") || function.contains("d_") ) {
					runGeni = 1;
				}					

				String result = index_id + "," + g6 + "," + ordem + "," + grauminimo + "," + graumaximo+ "," +trianglefree+ "," +conexo+ "," +
						bipartite+ "," +parameter_id + "," + function.replaceAll("\\\\", "\\\\\\\\") + "," +caixa1+ "," +adjacency+ "," +laplacian+ "," +slaplacian+ "," +allowdiscgraphs+ "," +
						biptonly+ "," +maxresults+ "," +adjacencyb+ "," +laplacianb+ "," +slaplacianb+ "," +chromatic+ "," +chromaticb+ "," +click+ "," +
						clickb+ "," +largestdegree+ "," +numedges+","+runGeni+","+runEigsolve+","+serial;
				
				
				return result;
			}
			
			
		};
		return graphs.toJavaRDD().map( f );
		
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
