package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.api.java.JavaRDD;

/** 			Terceiro passo do workflow 												**/
// Para cada elemento do RDD ( um grafo "Graph" ) chama o programa externo "sage.sh"
//		que é encarregado de executar o GENI e/ou o EIGSOLVE dependendo dos parametros
// 		passados pelo usuário.
// O resultado é um conjunto de arquivos que serão usados pelo "evaluate". 
// ----------------------------------------------------------------------------------------------


public class Step3 implements Serializable {
	private static final long serialVersionUID = 1L;

	public JavaRDD<String> run( JavaRDD<String> graphs, String workDir, String sageScript ) {
		
		String external = workDir + "/" + sageScript;
		
		JavaRDD<String> output = graphs.pipe( external );	
		
		return output;
	}
	
}
