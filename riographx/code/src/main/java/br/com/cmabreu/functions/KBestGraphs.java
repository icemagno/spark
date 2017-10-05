package br.com.cmabreu.functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.api.java.function.PairFunction;

import br.com.cmabreu.GraphComparator;
import scala.Tuple2;

public class KBestGraphs implements PairFunction<Tuple2<Integer, Iterable<String>>, Integer, List<String> > {
	private static final long serialVersionUID = 1L;
	private String sortOrder = "min"; 
	private Integer maxresults = 10;

	@Override
	public Tuple2<Integer, List<String> > call(Tuple2<Integer, Iterable<String> > t) throws Exception {

		
		Iterable<String> grafos = t._2;
		List<String> graphList = new ArrayList<String>();
		
		
		for( String grafo : grafos ) {
			String[] parameters = grafo.split(",");
			sortOrder = parameters[11];
			maxresults = Integer.valueOf( parameters[17] );
			graphList.add( grafo );
		}
		
		Collections.sort( graphList, new GraphComparator( sortOrder ) );
		
		List<String> resultGraphList = graphList.stream().limit( maxresults ).collect(Collectors.toList());
		
		return new Tuple2<Integer, List<String>>( t._1, resultGraphList );
	}
	
	
	
}
