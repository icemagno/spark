package br.com.cmabreu;

import java.io.Serializable;
import java.util.Comparator;

public class GraphComparator implements Comparator<String>, Serializable {
	private static final long serialVersionUID = 1L;
	private String sortOrder = "min"; 
	
	public GraphComparator( String sortOrder ){
		this.sortOrder = sortOrder;
	}
	
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
        
        Float result1 = Float.valueOf( parameters1[resultPosition] );
        Float result2 = Float.valueOf( parameters2[resultPosition] );			        
        
        if ( sortOrder.equals("min") ) 
        	return result1.compareTo( result2 ); 
        else 
        	return result2.compareTo( result1 );
        
        
	}
	
	
	
}
