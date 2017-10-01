package br.com.cmabreu.functions;

import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class ToOrderPairRDD implements PairFunction<String, Integer, String> {

	private static final long serialVersionUID = 1L;

	@Override
	public Tuple2<Integer, String> call(String grafo) throws Exception {
		String[] parameters = grafo.split(",");
		Integer ordem = Integer.valueOf( parameters[4] );
		
		System.out.println("Transformando em par o grafo : " + grafo );
		// 5,16,\lambda_2 + \chi + \overline{\lambda_2},DEk,5,1,3,0,1,0,591,max,1,0,0,0,0,10,1,0,0,1,0,0,0,0,0,1,1,b7c6cc1731bf4607acdeb4713bc09c77,4,7,4.236
		
		return new Tuple2<Integer, String>( ordem, grafo );
	}
	
	
}
