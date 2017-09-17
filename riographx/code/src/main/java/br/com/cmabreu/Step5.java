package br.com.cmabreu;

import java.io.Serializable;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class Step5 implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public JavaPairRDD<String, Graph> run( JavaRDD<String> functionResults ) {
		
		PairFunction<String, String, Graph> pairFunction = new PairFunction<String, String, Graph>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Tuple2<String, Graph> call(String functionResult) throws Exception {
				String[] results = functionResult.split(",");
				String key = results[0];
				String value = results[1];
				/*
				List<Graph> graphsByKey = grafos.lookup(key);
				Graph graph = graphsByKey.get(0);
				graph.setFunctionResult(value);
				*/
				
				Graph graph = new Graph();
				graph.setG6( key );
				graph.setFunctionResult( value );
				
				return new Tuple2<String, Graph>( key, graph );
			}
			
		};
		
		
		JavaPairRDD<String, Graph> graphsPairRDD = functionResults.mapToPair( pairFunction );			
		return graphsPairRDD;
		
	}

}
