package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Wrapper {

	private static List<JobUnity> jobs = new ArrayList<JobUnity>();
	
	private static int getIndex( String key, String header) {
		int index = -1;
		String[] headers = header.split(",");
		for ( int x = 0; x < headers.length; x++  ) {
			if ( headers[x].equals( key )  ) {
				index = x;
			}
		}
		return index;
	}	
	
	public static void processLine(String header, String line ) throws Exception {
		
		String[] lineData = line.split(",");
		String imageFile = lineData[ getIndex("gvfile", header) ];
		String function =  lineData[ getIndex("optifunc", header) ];
		String functionReal =  lineData[ getIndex("function", header) ];
		String evalValue = lineData[ getIndex("evaluatedvalue", header) ];
		String caixa1 = lineData[ getIndex("caixa1", header) ];
		String gorder = lineData[ getIndex("gorder", header) ];
		String maxresults = lineData[ getIndex("maxresults", header) ];
		String theGraph = lineData[ getIndex("grafo", header) ];
		
		jobs.add( new JobUnity(function, imageFile, evalValue, caixa1, maxresults, gorder, theGraph, functionReal) );

	}


	public static void main(String[] args) throws Exception{
		String inputFile = args[0];	
		String outputFile = args[1];	

		List<String> inputData = readFile( inputFile );
		if( inputData.size() > 1 ) {

			String header = inputData.get( 0 ); // Get the CSV header
			String line = "";
			for ( int x = 1; x < inputData.size(); x++  ) {
				line = inputData.get( x );      // REDUCE process every line
				processLine( header, line );
			}
			
			PDFCreator.gerarPDF( jobs, outputFile );
			
		} else {
			//System.out.println("Empty input data file.");
		}
		
	}

	
	
	/**
	 * This is a method to read the CSV data 
	 * @param file
	 * @return StringBuilder : The file data as a list of lines.
	 * @throws Exception
	 */
	public static List<String> readFile(String file) throws Exception {
		String line = "";
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader( new FileReader( file ) );
		while ( (line = br.readLine() ) != null ) {
		    list.add( line );
		}
		if (br != null) {
			br.close();
		}		
		return list;
	}
	

}
