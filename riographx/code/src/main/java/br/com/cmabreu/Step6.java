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
	
	public JavaPairRDD<Integer, List<String> > run( JavaPairRDD<Integer, Iterable<String> > agrupadoPorOrdemRdd ) {
		
		PairFunction<Tuple2<Integer, Iterable<String>>, Integer, List<String> > pf = new PairFunction<Tuple2<Integer, Iterable<String>>, Integer, List<String> >(){
			private static final long serialVersionUID = 1L;
			private String sortOrder = "min"; 
			private Integer maxresults = 10;
			
			class GraphComparator implements Comparator<String> {

				@Override
				public int compare(String paramT1, String paramT2) {
			        if (paramT1 == null) {
			            if (paramT2 == null) {
			                return 0; 
			            } else {
			                return -1; 
			            }
			        } else {
			            if (paramT2 == null) {
			                return 1;
			            }
			        }					
					
			        String[] parameters1 = paramT1.split(",");
			        String[] parameters2 = paramT2.split(",");
			        
			        int resultPosition = parameters1.length - 1; // O valor do resultado da funcao eh o ultimo.
			        
			        System.out.println("Comparando " + parameters1[resultPosition] + " contra " + parameters2[resultPosition] );
			        
			        Float result1 = Float.valueOf( parameters1[resultPosition] );
			        Float result2 = Float.valueOf( parameters2[resultPosition] );			        
			        
			        if ( sortOrder.equals("min") ) 
			        	return result1.compareTo( result2 ); 
			        else 
			        	return result2.compareTo( result1 );
			        
			        
				}
				
			}
			
			
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
				Collections.sort( graphList, new GraphComparator() );
				
				List<String> resultGraphList = graphList.stream().limit( maxresults ).collect(Collectors.toList());
				
				return new Tuple2<Integer, List<String>>( t._1, resultGraphList );
			}
			
			
		};		
		
		
		return agrupadoPorOrdemRdd.mapToPair( pf );
	}
	

}
