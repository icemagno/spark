package com.letsprog.learning.samples.transformations;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

public class PostgreSQLConnect {

	public static void main(String[] args) {

		if ( args.length == 0 ) {
			System.out.println("Falta parametros! Saindo...");
			System.exit(0);
		}
		
		String indexParameter = String.valueOf( args[0] );
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch ( Exception e ) {
			System.out.println("Problemas ao carregar o driver PostgreSQL: " + e.getMessage() );
		}
		System.out.println("Driver carregado.");
		
		/*
		String master = "local[*]";
		SparkConf sparkConf = new SparkConf();
		sparkConf.setAppName("Hello Spark");
		sparkConf.setMaster( master );
		JavaSparkContext context = new JavaSparkContext(sparkConf);
		*/
		
		// -------------------------------------------------------------------------------
		// YOUR CODE HERE
		// -------------------------------------------------------------------------------

		// https://github.com/high-performance-spark/high-performance-spark-examples/blob/master/src/main/java/com/highperformancespark/examples/dataframe/JavaHappyPandas.java
		
		SparkSession spark = SparkSession
				.builder()
				.appName("Portal RioGraphX")
				.config("driver", "org.postgresql.Driver")
				.getOrCreate(); 		

		Dataset<Row> parametersTable = spark.read()
				.format("jdbc")
				.option("url", "jdbc:postgresql://192.168.25.103:5432/graphx")
				.option("dbtable", "public.spectral_parameters")
				.option("user", "postgres")
				.option("password", "admin")
				.option("driver", "org.postgresql.Driver")
				.load(); 		

		Dataset<Row> graphDatabaseTable = spark.read()
				.format("jdbc")
				.option("url", "jdbc:postgresql://192.168.25.103:5432/graphx")
				.option("dbtable", "public.graphdatabase")
				.option("user", "postgres")
				.option("password", "admin")
				.option("driver", "org.postgresql.Driver")
				.load(); 		
		
		
		SQLContext graphDatabaseContext = graphDatabaseTable.sqlContext();
		graphDatabaseTable.createOrReplaceTempView("graphdatabase");

		SQLContext parametersContext = parametersTable.sqlContext();
		parametersTable.createOrReplaceTempView("parameters");
		
		Dataset<Row> parameters = parametersContext.sql("SELECT * FROM parameters WHERE index_id = " + indexParameter);
		if ( parameters.count() > 0 ) {
			
			Row param = parameters.first();
			String function = param.getAs("optifunc");
			String gorder = param.getAs("gorder"); 
			String mindegree = param.getAs("mindegree"); 
			String maxdegree = param.getAs("maxdegree"); 
			String trianglefree = param.getAs("trianglefree"); 
			String biptonly = param.getAs("biptonly"); 
			String allowdiscgraphs = param.getAs("allowdiscgraphs"); 
			
			if ( trianglefree.equals("on") ) trianglefree = "1"; else trianglefree = "0";
			if ( biptonly.equals("on") ) biptonly = "1"; else biptonly = "0";
			
			// Para LAPACK se: optifunc like '%lambda%' or sp.optifunc like '%mu%' or sp.optifunc like '%q!_%'
			/*
				sp.gorder::int = gd.ordem and gd.grauminimo >= sp.mindegree::int and gd.graumaximo <= sp.maxdegree::int and 
						( case when sp.trianglefree = 'on' then 1 else 0 end ) = gd.trianglefree and 
						( case	when sp.biptonly = 'on' then 1 else 0 end ) = gd.bipartite and 
						( (sp.allowdiscgraphs = 'off' and 1 = gd.conexo) or (sp.allowdiscgraphs = 'on'))			
			*/
			if ( function.contains("lambda") || function.contains("mu") || function.contains("q_") ) {
				System.out.println("Executar LAPACK");	
			}

			
			// Para GENI   se: optifunc like '%omega%' or sp.optifunc like '%chi%' or sp.optifunc like '%SIZE%' or sp.optifunc like '%d!_%'
			/*
			
 				sp.gorder::int = gd.ordem and gd.grauminimo >= sp.mindegree::int and gd.graumaximo <= sp.maxdegree::int and 
 						( case	when sp.trianglefree = 'on' then 1 else 0 end ) = gd.trianglefree and 
 						( case	when sp.biptonly = 'on' then 1 else 0 end ) = gd.bipartite and 
 						( (sp.allowdiscgraphs = 'off' and 1 = gd.conexo) or (sp.allowdiscgraphs = 'on'))			
			
			*/
			if ( function.contains("omega") || function.contains("chi") || function.contains("SIZE") || function.contains("d_") ) {
				System.out.println("EXECUTAR GENI");
			}

			String sql = "SELECT * FROM graphdatabase WHERE "+gorder+" = ordem and grauminimo >= "+mindegree+" and graumaximo <= "+
					maxdegree+" and "+trianglefree+" = trianglefree and	" + biptonly +" = bipartite and	" +
					"( ('"+allowdiscgraphs+"' = 'off' and 1 = conexo) or ('"+allowdiscgraphs+"' = 'on'))";
			

			System.out.println( sql );
			
			Dataset<Row> graphs = graphDatabaseContext.sql(sql);
			graphs.show( 200 );
			
			
		} else {
			System.out.println("Nenhum parametro encontrado com o indice " + indexParameter );
		}
		
		
		
		
		
		// Converte Dataset para Pair RDD ----------------------------------------------------------
		/*
		PairFunction<Row, Long, String> pairFunction = new PairFunction<Row, Long, String>() {
			private static final long serialVersionUID = 1L;

			public Tuple2<Long, String> call(Row row) throws Exception {
		        return new Tuple2<Long, String>((Long) row.get(2), (String) row.get(1));
		    }
			
		};
		JavaPairRDD<Long, String> jpRDD = jdbcDF.toJavaRDD().mapToPair( pairFunction );
		*/		
		// -----------------------------------------------------------------------------------------
		
		
		// Filtra
		/*
		Function< Tuple2<Long, String>, Boolean  > theFilter = new Function< Tuple2<Long, String>, Boolean  >() {
			public Boolean call( Tuple2<Long, String> keyValue ) {
				return keyValue._2().equals("");
			}
		};
		JavaPairRDD<Long, String> filteredRDD = jpRDD.filter( theFilter );
		*/
		
		
		// -------------------------------------------------------------------------------
		spark.stop();
		//context.close();

	}	

	/*
	  
	  	+--------+---------+---------+----------+--------------------+------+------+---------+---------+------------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
		|index_id|adjacency|laplacian|slaplacian|optifunc            |caixa1|gorder|mindegree|maxdegree|trianglefree|allowdiscgraphs|biptonly|maxresults|adjacencyb|laplacianb|slaplacianb|chromatic|chromaticb|click|clickb|largestdegree|numedges|
		+--------+---------+---------+----------+--------------------+------+------+---------+---------+------------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
		|     596|      off|       on|       off|\mu_2 + \overline...|   max|     5|        1|        7|         off|            off|     off|        10|       off|        on|        off|      off|       off|  off|   off|          off|     off|
		+--------+---------+---------+----------+--------------------+------+------+---------+---------+------------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
	 

		+--------+-----+-----+----------+----------+------------+------+---------+
		|index_id|grafo|ordem|grauminimo|graumaximo|trianglefree|conexo|bipartite|
		+--------+-----+-----+----------+----------+------------+------+---------+
		|       1|  D??|    5|         0|         0|           1|     0|        1|
		|       2|  D?_|    5|         0|         1|           1|     0|        1|
		|       3|  D?o|    5|         0|         2|           1|     0|        1|
		|       4|  D?w|    5|         0|         3|           1|     0|        1|
		|       5|  D?{|    5|         1|         4|           1|     1|        1|
		|       6|  DCO|    5|         0|         1|           1|     0|        1|
		|       7|  DCo|    5|         0|         2|           1|     0|        1|
		|       8|  DCW|    5|         1|         2|           1|     0|        1|
		|       9|  DCc|    5|         0|         2|           0|     0|        0|
		|      10|  DCw|    5|         1|         3|           1|     1|        1|
		+--------+-----+-----+----------+----------+------------+------+---------+

	 */

}
