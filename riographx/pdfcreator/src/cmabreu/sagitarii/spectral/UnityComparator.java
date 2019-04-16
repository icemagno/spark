package cmabreu.sagitarii.spectral;

import java.util.Comparator;

public class UnityComparator implements Comparator<JobUnity> {

	private int getLessThan( String caixa1 ) {
		if ( caixa1.equals("min") ) {
			return -1;
		} else {
			return 1;
		}
	}

	private int getMoreThan( String caixa1 ) {
		if ( caixa1.equals("min") ) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public int compare(JobUnity thisJobUnity, JobUnity otherJobUnity) {
		Double me = Double.valueOf( thisJobUnity.getEvalValue() );
		Double other = Double.valueOf( otherJobUnity.getEvalValue() );
		
		int result = 0;
		
		if (me < other ) {
            result = getLessThan( thisJobUnity.getCaixa1() );
        }
		
        if ( me > other ) {
            result = getMoreThan( thisJobUnity.getCaixa1() );
        }
        
		return result;
	}	
	
}
