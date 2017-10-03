package br.com.cmabreu.functions;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class Flatenize implements PairFunction< Tuple2<Integer, List<String> >, Integer, String  > , Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public Tuple2<Integer, String> call(Tuple2<Integer, List<String>> t) throws Exception {
		
		List<String> lista = t._2;
		StringBuilder sb = new StringBuilder();
		String prefix = "";
		
		for( String ss : lista  ) {
			sb.append( prefix + ss.toString().replaceAll("\\\\", "\\\\\\\\") );
			prefix = "|||" ;
		}
		
		
		String listAsString = sb.toString();
		
		return new Tuple2<Integer,String>( t._1, listAsString );
	}	
	
}
