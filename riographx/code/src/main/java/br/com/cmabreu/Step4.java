package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;

/** 			Quarto passo do workflow 												**/
// Para cada elemento do RDD ( um grafo "Graph" ) chama o programa externo "sage.sh"
//		que é encarregado de executar o GENI e/ou o EIGSOLVE dependendo dos parametros
// 		passados pelo usuário.
// O resultado é um conjunto de arquivos que serão usados pelo "evaluate". 
// ----------------------------------------------------------------------------------------------


public class Step4 implements Serializable {
	private static final long serialVersionUID = 1L;

	public JavaRDD<String> run( JavaPairRDD<String, Graph> partitionedRdd, String workDir, String sageScript ) {
		
		String external = workDir + "/" + sageScript;
		
		JavaRDD<String> output = partitionedRdd.pipe( external.replace("//", "/") );	
		
		// Printa o RDD. Somente para testes....
		
		VoidFunction<String> f = new VoidFunction<String>() {
			private static final long serialVersionUID = 1L;
			@Override
			public void call(String arg0) throws Exception {
				System.out.println("Output RDD: " + arg0 );
			}
		};
		output.foreach(f);		
		
		
		return output;
	}
	
}
