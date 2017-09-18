package br.com.cmabreu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class Step6 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public JavaPairRDD<Integer, List<Graph> > run( JavaPairRDD<Integer, Iterable<Graph> > agrupadoPorOrdemRdd ) {
		
		
		PairFunction<Tuple2<Integer, Iterable<Graph>>, Integer, List<Graph> > pf =	new PairFunction<Tuple2<Integer, Iterable<Graph>>, Integer, List<Graph> >(){
			private static final long serialVersionUID = 1L;
			private String ordem = "min"; 
			private Integer maxresults = 10;
			
			class GraphComparator implements Comparator<Graph> {

				@Override
				public int compare(Graph paramT1, Graph paramT2) {
			        if (paramT1 == null) {
			            if (paramT2 == null) {
			                return 0; // Both students are null
			            } else {
			                return -1; // paramT1 is NULL, so put paramT1 in the end of
			                // the sorted list
			            }
			        } else {
			            if (paramT2 == null) {
			                return 1;
			            }
			        }					
					
			        Float result1 = Float.valueOf( paramT1.getFunctionResult() );
			        Float result2 = Float.valueOf( paramT2.getFunctionResult() );			        
			        
			        if ( ordem.equals("min") ) 
			        	return result1.compareTo( result2 ); 
			        else 
			        	return result2.compareTo( result1 );
			        
			        
				}
				
			}
			
			
			@Override
			public Tuple2<Integer, List<Graph> > call(Tuple2<Integer, Iterable<Graph> > t) throws Exception {
				Iterable<Graph> grafos = t._2;
				List<Graph> graphList = new ArrayList<Graph>();
				
				for( Graph grafo : grafos ) {
					ordem = grafo.getCaixa1();
					maxresults = Integer.valueOf( grafo.getMaxresults() );
					graphList.add( grafo );
				}
				Collections.sort( graphList, new GraphComparator() );
				
				List<Graph> resultGraphList = graphList.stream().limit( maxresults ).collect(Collectors.toList());
				
				return new Tuple2<Integer, List<Graph>>( t._1, resultGraphList );
			}
			
			
		};		
		
		
		return agrupadoPorOrdemRdd.mapToPair( pf );
	}
	

}
