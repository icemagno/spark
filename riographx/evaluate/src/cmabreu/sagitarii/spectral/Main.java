package cmabreu.sagitarii.spectral;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.cefetrj.parser.FormulaEvaluator;

public class Main {
	private static String sourceFolder;
	private static String LINE_PARAMETERS;

	public static FunctionResult evaluateOptimizationFunction(EvaluationInfo evalInfo) {

		String optimizationFunction = evalInfo.optimizationFunction;
		String tmpStr;

		int order = Integer.valueOf( evalInfo.gorder );
		
		if ( order != 0 ) {		
			for (int i = 0; i < order; i++) {
				int index = (order - 1) - i;
	
				tmpStr = "\\overline{q_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesSgnlapBars.length > 0) {
					//System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesSgnlapBars[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr,	evalInfo.valuesSgnlapBars[index].toString());
				}
	
				tmpStr = "\\overline{\\mu_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesLapBars.length > 0) {
					//System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesLapBars[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valuesLapBars[index].toString());
				}
	
				tmpStr = "\\overline{\\lambda_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesAdjBars.length > 0) {
					//System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesAdjBars[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr,	evalInfo.valuesAdjBars[index].toString());			
				}
				
				tmpStr = "q_" + Integer.toString(i + 1);
				if (evalInfo.valuesSgnlaps.length > 0) {
					//System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesSgnlaps[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.valuesSgnlaps[index]);
				}
	
				tmpStr = "\\mu_" + Integer.toString(i + 1);
				if (evalInfo.valuesLaps.length > 0) {
					//System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesLaps[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.valuesLaps[index]);
				}
	
				tmpStr = "\\lambda_" + Integer.toString(i + 1);
				if (evalInfo.valuesAdjs.length > 0) { 
					//System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesAdjs[index].toString() );
					optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.valuesAdjs[index]);
				}
			}
		}
		
		tmpStr = "\\overline{\\chi}";
		try {
			//System.out.println("replacing " + tmpStr + " by " + evalInfo.valueChiAdjBar );
			optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valueChiAdjBar);
		} catch ( Exception ignored ) { }

		tmpStr = "\\chi";
		try {
			//System.out.println("replacing " + tmpStr + " by " + evalInfo.valueChiAdj );
			optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valueChiAdj);
		} catch ( Exception ignored ) { }

		tmpStr = "\\overline{\\omega}";
		try {
			//System.out.println("replacing " + tmpStr + " by " + evalInfo.valueOmegaAdjBar );
			optimizationFunction = optimizationFunction.replace(tmpStr,	evalInfo.valueOmegaAdjBar);
		} catch ( Exception ignored ) { }

		tmpStr = "\\omega";
		try {
			//System.out.println("replacing " + tmpStr + " by " + evalInfo.valueOmegaAdj );
			optimizationFunction = optimizationFunction.replace(tmpStr, evalInfo.valueOmegaAdj);
		} catch ( Exception ignored ) { }


		tmpStr = "ORDER";
		try {
			//System.out.println("replacing " + tmpStr + " by " + evalInfo.gorder );
			optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.gorder);
		} catch ( Exception ignored ) { }
		
		
		try {
			//System.out.println("Largest Degree Vector: " + evalInfo.kLargestDegree );
			String[] degrees = evalInfo.kLargestDegree.trim().split("[|]");
			int x = 1;
			for ( String degree : degrees ) {
				tmpStr = "d_" + x;
				//System.out.println("replacing " + tmpStr + " by " + degree );
				optimizationFunction = optimizationFunction.replaceAll(tmpStr, degree);
				x++;
			}
		} catch ( Exception ignored ) { }


		tmpStr = "SIZE";
		try {
			//System.out.println("replacing " + tmpStr + " by " + evalInfo.numEdges );
			optimizationFunction = optimizationFunction.replace(tmpStr, "" + evalInfo.numEdges);
		} catch ( Exception ignored ) { }
			
		//System.out.println("optimization function: " + optimizationFunction);

		FunctionResult result = new FunctionResult();
		result.evaluatedValue = 0.0;
		result.function = optimizationFunction;
		
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream( optimizationFunction.getBytes() );
			FormulaEvaluator eval = new FormulaEvaluator(inputStream);
			result.evaluatedValue = eval.evaluate();
		} catch (Throwable e) {
			//System.out.println("FUNCTION ERROR: " + e.getMessage() );
		}

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
			//System.out.println( "Adj size: " + convertedAdj.size() );
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

		System.out.println( LINE_PARAMETERS + "," + evaluatedValue.evaluatedValue );		
		
	}
	
	// java -jar evaluate.jar /home/magno/riographx/grafos/xxxx optimizationFunction paramId maxResults caixa1 gorder grauminimo graumaximo rddKey
	public static void main( String[] args ) throws Exception {
		sourceFolder = args[0];
		
		File dir = new File( sourceFolder );
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			JobUnity job = new JobUnity();
	
			String line = args[1];
			String[] parameters = line.split(",");
			LINE_PARAMETERS = line;

			String rddkey = parameters[0];
			String index_id = parameters[1];
			String function = parameters[2];
			String g6 = parameters[3];
			String ordem = parameters[4];
			String grauminimo = parameters[5];
			String graumaximo = parameters[6];
			String trianglefree = parameters[7];
			String conexo = parameters[8];
			String bipartite = parameters[9];
			String parameter_id = parameters[10];
			String caixa1 = parameters[11];
			String adjacency = parameters[12];
			String laplacian = parameters[13];
			String slaplacian = parameters[14];
			String allowdiscgraphs = parameters[15];
			String biptonly = parameters[16];
			String maxresults = parameters[17];
			String adjacencyb = parameters[18];
			String laplacianb = parameters[19];
			String slaplacianb = parameters[20];
			String chromatic = parameters[21];
			String chromaticb = parameters[22];
			String click = parameters[23];
			String clickb = parameters[24];
			String largestdegree = parameters[25];
			String numedges = parameters[26];
			String runGeni = parameters[27];
			String runEigsolve = parameters[28];
			String serial = parameters[29];

			job.setOptimizationFunction( function.replaceAll( Pattern.quote("+"), " + ") );
			job.setParamId( parameter_id );
			job.setMaxResults( maxresults );
			job.setCaixa1( caixa1 );
			job.setGrafo( g6 );
			job.setGorder( ordem );
			job.setGrauMin( grauminimo );
			job.setGrauMax( graumaximo );
			job.setRddKey( rddkey );
			
			for (File file : directoryListing) {
				String fileName = file.getName();
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

				if (ext.equals("lap")) {
					//System.out.println(" > is a lap file " + fileName );
					job.setLapFile(fileName);
				}
				if (ext.equals("adj")) {
					//System.out.println(" > is a adj file " + fileName );
					job.setAdjFile(fileName);
				}
				if (ext.equals("sgnlap")) {
					//System.out.println(" > is a sgnlap file " + fileName );
					job.setSgnLapFile(fileName);
				}

				if (ext.equals("lapb")) {
					//System.out.println(" > is a lapb file " + fileName );
					job.setLapBarFile(fileName);
				}
				if (ext.equals("adjb")) {
					//System.out.println(" > is a adjb file " + fileName );
					job.setAdjBarFile(fileName);
				}
				if (ext.equals("sgnlapb")) {
					//System.out.println(" > is a sgnlapb file " + fileName );
					job.setSgnLapBarFile(fileName);
				}
				if (ext.equals("csv")) {
					//System.out.println(" > is a inv file " + fileName );
					job.setInvariantsFile(sourceFolder, fileName);
				}
				
			}
			
			processJob(job);
		}
		
	}
}
