package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

/*
 * Primeiro passo do workflow
 * 
 * 		Conecta com o banco de dados. 
 * 	
 * 		TODO: Parametrizar os dados de conexão ao banco.
 * 
 * 		Recebe o indice dos parametros na tabela de parametros e 
 * 		seleciona todos os grafos que cumprem os requisitos
 * 
 *  	REQUER VIEW "select_graphs" no banco de dados.
 *  
 *  	Retorna um Dataset ( Dataset<Row> ) contendo os grafos encontrados.
 *  
 * 		https://github.com/high-performance-spark/high-performance-spark-examples/blob/master/src/main/java/com/highperformancespark/examples/dataframe/JavaHappyPandas.java 
 */

/*
	drop view if exists select_graphs;
	create or replace view select_graphs as (
			select 
					gd.*,sp.index_id as parameter_id, sp.optifunc, sp.caixa1, sp.order_max, sp.order_min, 
					sp.adjacency, sp.laplacian, sp.slaplacian,sp.allowdiscgraphs,
					sp.biptonly,sp.maxresults,sp.adjacencyb,sp.laplacianb,sp.slaplacianb,
					sp.chromatic,sp.chromaticb,sp.click,sp.clickb,sp.largestdegree,sp.numedges
			from 
					graphdatabase gd, spectral_parameters sp 
			where  
					gd.grauminimo >= cast(sp.mindegree as integer) and 
					gd.graumaximo <= cast(sp.maxdegree as integer) and  
					cast(sp.trianglefree as integer) = gd.trianglefree and cast(sp.biptonly as integer) = gd.bipartite and 
					( (sp.allowdiscgraphs = '0' and gd.conexo = 1 ) or (sp.allowdiscgraphs = '1')) and
					( ( gd.ordem >= sp.order_min ) and ( gd.ordem <= sp.order_max ) )
	);
	
	-- select * from select_graphs where parameter_id = 591

*/

public class Step1 implements Serializable {
	private static final long serialVersionUID = 1L;

	public Dataset<Row> run( SparkSession spark, String indexParameter ) {
		
		// Abre conexao com a view select_graphs
		Dataset<Row> graphDatabaseTable = spark.read()
				.format("jdbc")
				.option("url", "jdbc:postgresql://192.168.25.103:5432/graphx")
				.option("dbtable", "public.select_graphs")
				.option("user", "postgres")
				.option("password", "admin")
				.option("driver", "org.postgresql.Driver")
				.load();
		
		SQLContext graphDatabaseContext = graphDatabaseTable.sqlContext();
		graphDatabaseTable.createOrReplaceTempView("select_graphs");

		
		// Seleciona os grafos de acordo com os parametros do usuario
		String sql = "select * from select_graphs where parameter_id = " + indexParameter; 			
		Dataset<Row> graphs = graphDatabaseContext.sql(sql);

		return graphs;
	}

}

/*
Tabela de parametros
+--------+---------+---------+----------+--------------------+------+---------+---------+---------+---------+------------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
|index_id|adjacency|laplacian|slaplacian|            optifunc|caixa1|order_min|order_max|mindegree|maxdegree|trianglefree|allowdiscgraphs|biptonly|maxresults|adjacencyb|laplacianb|slaplacianb|chromatic|chromaticb|click|clickb|largestdegree|numedges|
+--------+---------+---------+----------+--------------------+------+---------+---------+---------+---------+------------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
|     591|       on|      off|       off|\lambda_2 + \chi ...|   min|        6|        8|        0|        5|         off|            off|     off|        10|        on|       off|        off|       on|       off|  off|   off|          off|     off|
+--------+---------+---------+----------+--------------------+------+---------+---------+---------+---------+------------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+	 

Retorno da função "select_graphs"
+--------+-----+-----+----------+----------+------------+------+---------+------------+--------------------+------+---------+---------+----------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
|index_id|grafo|ordem|grauminimo|graumaximo|trianglefree|conexo|bipartite|parameter_id|            optifunc|caixa1|adjacency|laplacian|slaplacian|allowdiscgraphs|biptonly|maxresults|adjacencyb|laplacianb|slaplacianb|chromatic|chromaticb|click|clickb|largestdegree|numedges|
+--------+-----+-----+----------+----------+------------+------+---------+------------+--------------------+------+---------+---------+----------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
|      50| E?bw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      60| E?qw|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      61| E?rw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      66| E?zW|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      67| E?zw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      69| E?~w|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      77| ECRo|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      79| ECRw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      85| ECqg|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      86| ECro|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      87| ECrg|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      89| ECrw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      94| ECZO|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      95| ECZG|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      97| ECYW|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      98| ECZo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|      99| ECZg|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     100| ECZW|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     101| ECZw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     103| ECfo|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     105| ECfw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     107| ECzo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     108| ECzg|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     109| ECzW|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     110| ECxw|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     111| ECzw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     112| ECvo|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     114| ECuw|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     115| ECvw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     116| EC~o|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     117| EC~w|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     120| EEro|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     122| EErw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     125| EEio|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     126| EEho|    6|         2|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     127| EEiW|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     128| EEhW|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     129| EEjo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     130| EEjW|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     131| EEhw|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     132| EEjw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     134| EEzO|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     135| EEzo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     136| EEzg|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     137| EEzw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     138| EEvo|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     140| EEuw|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     141| EEvw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     142| EEno|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     143| EElw|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     144| EEnw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     145| EE~w|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     147| EFzo|    6|         3|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     148| EFzW|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     149| EFzw|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     150| EF~w|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     152| EQj_|    6|         1|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     153| EQjO|    6|         2|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     155| EQjo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     156| EQjg|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     157| EQjw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     158| EQzO|    6|         2|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     159| EQzo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     160| EQzg|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     161| EQzW|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     162| EQyw|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     163| EQzw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     164| EQ~o|    6|         3|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     165| EQ~w|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     166| EUZ_|    6|         2|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     167| EUZO|    6|         2|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     168| EUZo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     169| EUZw|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     170| EUxo|    6|         3|         3|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     171| EUzo|    6|         3|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     172| EUzW|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     173| EUzw|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     174| EU~w|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     175| ETzo|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     176| ETzg|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     177| ETzw|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     178| ETno|    6|         1|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     180| ETnw|    6|         1|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     181| ET~w|    6|         2|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     182| EV~w|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     183| E]zo|    6|         3|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     184| E]zg|    6|         2|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     185| E]yw|    6|         3|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     186| E]zw|    6|         3|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     187| E]~o|    6|         4|         4|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     188| E]~w|    6|         4|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     189| E^~w|    6|         4|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
|     190| E~~w|    6|         5|         5|           0|     1|        0|         591|\lambda_2 + \chi ...|   min|        1|        0|         0|              0|       0|        10|         1|         0|          0|        1|         0|    0|     0|            0|       0|
+--------+-----+-----+----------+----------+------------+------+---------+------------+--------------------+------+---------+---------+----------+---------------+--------+----------+----------+----------+-----------+---------+----------+-----+------+-------------+--------+
 */

