package br.cefetrj.sagitarii.wrappers;

import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cmabreu.sagitarii.sdk.IWrapperProcessor;
import cmabreu.sagitarii.sdk.LineData;
import cmabreu.sagitarii.sdk.WrapperHelper;

public class Processor implements IWrapperProcessor {
	private List<String> outputData;
	private List<LineData> csvLines;

	
	public Processor() {
		outputData = new ArrayList<String>();
		csvLines = new ArrayList<LineData>();
	}
	
	
	@Override
	public List<String> onNeedOutputData() {
		return outputData;
	}

	
	public String toHexString(byte[] bytes) {
	    StringBuilder hexString = new StringBuilder();

	    for (int i = 0; i < bytes.length; i++) {
	        String hex = Integer.toHexString(0xFF & bytes[i]);
	        if (hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }

	    return hexString.toString();
	}	
	
	@Override
	public void processLine( LineData ld, WrapperHelper helper ) throws Exception {
		
		outputData.add( ld.getCsvHeader() + ",eigsolve,opcode" );
		
		String geniFile = helper.getWrapperFolder() + "Eigenvalue.py";
		String inboxFolder = helper.getInboxFolder();
		
		//String g6File = ld.getData("g6splitedfile");
		String g6File = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10) + ".g6";
		
		String parameters = "";
		
		String output = helper.getOutboxFolder();
		
		String adjacency = ld.getData("adjacency"); 
		String laplacian = ld.getData("laplacian"); 
		String slaplacian = ld.getData("slaplacian"); 

		String adjacencyB = ld.getData("adjacencyb"); 
		String laplacianB = ld.getData("laplacianb"); 
		String slaplacianB = ld.getData("slaplacianb"); 
		
		String theGraph = ld.getData("grafo");

		byte[] bytesOfMessage = theGraph.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		String opcode = toHexString( thedigest );
		
		System.out.println("User Options: (A): " + adjacency + " (Q): "+ slaplacian + " (L): " + laplacian + 
				" (Ab): " + adjacencyB + " (Qb): "+ slaplacianB + " (Lb): " + laplacianB );
		
		System.out.println("Graph: [" + theGraph + "]" );
		
		if( laplacianB.equals("on")  ) {
			parameters = parameters + " -y";
			outputData.add( ld.getCsvLine() + "," + g6File + ".lapb," + opcode );
		}
		if( adjacencyB.equals("on")  ) {
			parameters = parameters + " -x";
			outputData.add( ld.getCsvLine() + "," + g6File + ".adjb," + opcode );
		}
		if( slaplacianB.equals("on")  ) {
			parameters = parameters + " -z";
			outputData.add( ld.getCsvLine() + "," + g6File + ".sgnlapb," + opcode );
		}
		if( laplacian.equals("on")  ) {
			parameters = parameters + " -l";
			outputData.add( ld.getCsvLine() + "," + g6File + ".lap," + opcode );
		}
		if( adjacency.equals("on")  ) {
			parameters = parameters + " -a";
			outputData.add( ld.getCsvLine() + "," + g6File + ".adj," + opcode );
		}
		if( slaplacian.equals("on")  ) {
			parameters = parameters + " -q";
			outputData.add( ld.getCsvLine() + "," + g6File + ".sgnlap," + opcode );
		}
		
		System.out.println("Parameters: " + parameters);
		System.out.println("File: " + g6File);
		System.out.println("Opcode: " + opcode);
		String inputFile = inboxFolder + g6File;

		PrintWriter out = new PrintWriter( inputFile );
		out.println( theGraph );
		out.close();
		
		String runGeni = helper.getWrapperFolder() + "runEigenvalue.sh";
		String runFile = runGeni + " "+geniFile+ " " +output + " " + inputFile + " \"" +parameters.trim() + "\"";
		
		System.out.println( runFile );
		
		// How to run external applications
		System.out.println( "set permission" );
		helper.runExternal( "chmod 0777 " + runGeni );
		
		helper.runExternal( runFile );
		
	}

	
	@Override
	public void onProcessFinish() {
		
		// Get the first data just to create the header. Can be anyone since 
		// the header is present in all items
		// In most cases this method can left untouched because it can
		// be used by MAP (1:1), REDUCE (n:1) or SPLIT (1:n) operators.
		/*
		if ( csvLines.size() > 0 ) {
			outputData.add( csvLines.get(0).getCsvHeader() );	
			for ( LineData ld : csvLines ) {
				outputData.add( ld.getCsvLine() );
			}
		} else {
			System.out.println("No data for output");
		}
		*/
	}

	
	@Override
	public void onProcessBeforeStart( LineData headerData ) {
		System.out.println("starting...");
	}


}
