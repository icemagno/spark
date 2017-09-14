package cmabreu.sagitarii.spectral;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import br.cefetrj.parser.FormulaEvaluator;

public class Main {
	private static List<String> outputData = new ArrayList<String>();
	private static String sourceFolder;

	public static FunctionResult evaluateOptimizationFunction(EvaluationInfo evalInfo) {

		String optimizationFunction = evalInfo.optimizationFunction;
		System.out.println("Original optimization function: " + optimizationFunction);

		String tmpStr;

		int order = Integer.valueOf( evalInfo.gorder );
		
		if ( order != 0 ) {		
			for (int i = 0; i < order; i++) {
				int index = (order - 1) - i;
	
				tmpStr = "\\overline{q_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesSgnlapBars.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesSgnlapBars[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr,	evalInfo.valuesSgnlapBars[index].toString());
				}
	
				tmpStr = "\\overline{\\mu_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesLapBars.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesLapBars[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valuesLapBars[index].toString());
				}
	
				tmpStr = "\\overline{\\lambda_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesAdjBars.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesAdjBars[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr,	evalInfo.valuesAdjBars[index].toString());			
				}
				
				tmpStr = "q_" + Integer.toString(i + 1);
				if (evalInfo.valuesSgnlaps.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesSgnlaps[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.valuesSgnlaps[index]);
				}
	
				tmpStr = "\\mu_" + Integer.toString(i + 1);
				if (evalInfo.valuesLaps.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesLaps[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.valuesLaps[index]);
				}
	
				tmpStr = "\\lambda_" + Integer.toString(i + 1);
				if (evalInfo.valuesAdjs.length > 0) { 
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesAdjs[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.valuesAdjs[index]);
				}
			}
		}
		tmpStr = "\\overline{\\chi}";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueChiAdjBar );
			optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valueChiAdjBar);
		} catch ( Exception ignored ) { }

		tmpStr = "\\chi";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueChiAdj );
			optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valueChiAdj);
		} catch ( Exception ignored ) { }

		tmpStr = "\\overline{\\omega}";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueOmegaAdjBar );
			optimizationFunction = optimizationFunction.replace(tmpStr,	evalInfo.valueOmegaAdjBar);
		} catch ( Exception ignored ) { }

		tmpStr = "\\omega";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueOmegaAdj );
			optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valueOmegaAdj);
		} catch ( Exception ignored ) { }


		tmpStr = "ORDER";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.gorder );
			optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.gorder);
		} catch ( Exception ignored ) { }
		
		
		try {
			System.out.println("Largest Degree Vector: " + evalInfo.kLargestDegree );
			String[] degrees = evalInfo.kLargestDegree.trim().split("[|]");
			int x = 1;
			for ( String degree : degrees ) {
				tmpStr = "d_" + x;
				System.out.println("replacing " + tmpStr + " by " + degree );
				optimizationFunction = optimizationFunction.replaceAll(tmpStr, degree);
				x++;
			}
		} catch ( Exception ignored ) { }


		tmpStr = "SIZE";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.numEdges );
			optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.numEdges);
		} catch ( Exception ignored ) { }
			
		System.out.println("optimization function: " + optimizationFunction);

		FunctionResult result = new FunctionResult();
		result.evaluatedValue = 0.0;
		result.function = optimizationFunction;
		
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream( optimizationFunction.getBytes() );
			FormulaEvaluator eval = new FormulaEvaluator(inputStream);
			result.evaluatedValue = eval.evaluate();
		} catch (Throwable e) {
			System.out.println("FUNCTION ERROR: " + e.getMessage() );
		}

		System.out.println("optimization function result: "	+ result.evaluatedValue);

		return result;

	}

	public static void processJob(JobUnity job) throws Exception {
		List<String> convertedAdj = new ArrayList<String>();
		List<String> convertedLap = new ArrayList<String>();
		List<String> convertedSgnLap = new ArrayList<String>();

		List<String> convertedAdjBar = new ArrayList<String>();
		List<String> convertedLapBar = new ArrayList<String>();
		List<String> convertedSgnLapBar = new ArrayList<String>();

		List<Integer> convertedGreatestDegrees = new ArrayList<Integer>();

		if (job.isAdj()) {
			String adjFile = sourceFolder + job.getAdjFile();
			convertedAdj = CsvReader.readFile(adjFile);
			System.out.println( "Adj size: " + convertedAdj.size() );
		}

		if (job.isLap()) {
			String lapFile = sourceFolder + job.getLapFile();
			convertedLap = CsvReader.readFile(lapFile);
		}

		if (job.isSgnLap()) {
			String sgnlapFile = sourceFolder + job.getSgnLapFile();
			convertedSgnLap = CsvReader.readFile(sgnlapFile);
		}

		if (job.isAdjBar()) {
			String adjBarFile = sourceFolder + job.getAdjBarFile();
			convertedAdjBar = CsvReader.readFile(adjBarFile);
		}

		if (job.isLapBar()) {
			String lapBarFile = sourceFolder + job.getLapBarFile();
			convertedLapBar = CsvReader.readFile(lapBarFile);
		}

		if (job.isSgnLapBar()) {
			String sgnlapBarFile = sourceFolder + job.getSgnLapBarFile();
			convertedSgnLapBar = CsvReader.readFile(sgnlapBarFile);
		}

		
		String[] valuesAdj = new String[convertedAdj.size()];
		valuesAdj = convertedAdj.toArray(valuesAdj);

		String[] valuesLap = new String[convertedLap.size()];
		valuesLap = convertedLap.toArray(valuesLap);

		String[] valuesSgnLap = new String[convertedSgnLap.size()];
		valuesSgnLap = convertedSgnLap.toArray(valuesSgnLap);

		String[] valuesAdjBar = new String[convertedAdjBar.size()];
		valuesAdjBar = convertedAdjBar.toArray(valuesAdjBar);

		String[] valuesLapBar = new String[convertedLapBar.size()];
		valuesLapBar = convertedLapBar.toArray(valuesLapBar);

		String[] valuesSgnLapBar = new String[convertedSgnLapBar.size()];
		valuesSgnLapBar = convertedSgnLapBar.toArray(valuesSgnLapBar);

		String[] valuesD = new String[convertedGreatestDegrees.size()];
		valuesD = convertedGreatestDegrees.toArray(valuesD);

		FunctionResult evaluatedValue = evaluateOptimizationFunction(
				new EvaluationInfo(
						job.getOptimizationFunction(), 
						valuesAdj, 
						valuesLap,
						valuesSgnLap, 
						job.getKLargestDegree(), 
						job.getNumEdges(), 
						valuesD, 
						valuesAdjBar,
						valuesLapBar, 
						valuesSgnLapBar, 
						job.getChi(), 
						job.getChiBar(),
						job.getOmega(), 
						job.getOmegaBar(),
						job.getGorder() ) );

		
		outputData.add("optifunc,paramid,evaluatedvalue,maxresults,caixa1,gorder,function,grafo");
		outputData.add(job.getOptimizationFunction() + "," + job.getParamId() + 
				"," + evaluatedValue.evaluatedValue + "," + job.getMaxResults() + "," + job.getCaixa1() + "," + job.getGorder() + 
				"," + evaluatedValue.function + "," + job.getGrafo() );
		
		saveOutput();
	}
	
	public static void saveOutput() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream( sourceFolder	+ "/sagi_output.txt"));
		for (String line : outputData) {
			pw.println(line);
		}
		pw.close();
	}	


	// java -jar evaluate.jar /home/magno/riographx/grafos/xxxx optimizationFunction paramId maxResults caixa1 gorder
	public static void main( String[] args ) throws Exception {
		
		sourceFolder = args[0];
		
		File dir = new File( sourceFolder );
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			
			JobUnity job = new JobUnity();
	
			/*
			String optimizationFunction = lineData[CsvReader.getIndex("optifunc", header)];
			String paramId = lineData[CsvReader.getIndex("paramid", header)];
			String maxResults = lineData[CsvReader.getIndex("maxresults", header)];
			String caixa1 = lineData[CsvReader.getIndex("caixa1", header)];
			String gorder = lineData[CsvReader.getIndex("gorder", header)];
			String grafo = lineData[CsvReader.getIndex("grafo", header)];
			*/

			job.setOptimizationFunction( args[1] );
			job.setParamId( args[2] );
			job.setMaxResults( args[3] );
			job.setCaixa1( args[4] );
			job.setGrafo( args[5] );
			job.setGorder( args[6] );
			
			for (File file : directoryListing) {
				String fileName = file.getName();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

				if (ext.equals("lap")) {
					System.out.println(" > is a lap file " + fileName );
					job.setLapFile(fileName);
				}
				if (ext.equals("adj")) {
					System.out.println(" > is a adj file " + fileName );
					job.setAdjFile(fileName);
				}
				if (ext.equals("sgnlap")) {
					System.out.println(" > is a sgnlap file " + fileName );
					job.setSgnLapFile(fileName);
				}

				if (ext.equals("lapb")) {
					System.out.println(" > is a lapb file " + fileName );
					job.setLapBarFile(fileName);
				}
				if (ext.equals("adjb")) {
					System.out.println(" > is a adjb file " + fileName );
					job.setAdjBarFile(fileName);
				}
				if (ext.equals("sgnlapb")) {
					System.out.println(" > is a sgnlapb file " + fileName );
					job.setSgnLapBarFile(fileName);
				}
				if (ext.equals("csv")) {
					System.out.println(" > is a inv file " + fileName );
					job.setInvariantsFile(sourceFolder, fileName);
				}
				
			}
			
			processJob(job);
		}
		
		/*
		List<String> inputData = CsvReader.readFile(workFolder + "/" + "sagi_input.txt");
		if (inputData.size() > 1) {
			String header = inputData.get(0); // Get the CSV header

			

			System.out.println("Input files: ");
			for (int x = 1; x < inputData.size(); x++) { // REDUCE read all
				// lines
				String line = inputData.get(x);
				String[] lineData = line.split(",");

				//Get tEigSolve file
				String inputFile = lineData[CsvReader.getIndex("workfile",	header)];

				

				String ext = inputFile.substring(inputFile.lastIndexOf(".") + 1);
				
				System.out.println("Ext: " + ext);
				

			}
			
			

		} else {
			System.out.println("Empty input data file.");
		}
		*/

	}
}
