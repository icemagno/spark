package cmabreu.sagitarii.spectral;

import java.util.List;

public class JobUnity {
	private String lapFile;
	private String adjFile;
	private String sgnLapFile;

	private String lapBarFile;
	private String adjBarFile;
	private String sgnLapBarFile;

	private String header;
	private String optimizationFunction;
	private String paramId;
	private String maxResults;
	private String caixa1;
	private String gorder;
	private String grafo;

	private String invariantsFile;
	private String chi;
	private String chiBar;
	private String omega;
	private String omegaBar;
	private String kLargestDegree;
	private String numEdges;

	private String grauMin;
	private String grauMax;
	private String rddKey;
	
	public String getGrafo() {
		return grafo;
	}
	
	public void setGrafo(String grafo) {
		this.grafo = grafo;
	}
	
	public void setMaxResults(String maxResults) {
		this.maxResults = maxResults;
	}
	
	public void setGorder(String gorder) {
		this.gorder = gorder;
	}
	
	public String getGorder() {
		return gorder;
	}
	
	public void setCaixa1(String caixa1) {
		this.caixa1 = caixa1;
	}
	
	public String getCaixa1() {
		return caixa1;
	}

	public String getMaxResults() {
		return maxResults;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamId() {
		return paramId;
	}

	public boolean isLap() {
		return (lapFile != null) && (!lapFile.equals(""));
	}

	public boolean isAdj() {
		return (adjFile != null) && (!adjFile.equals(""));
	}

	public boolean isSgnLap() {
		return (sgnLapFile != null) && (!sgnLapFile.equals(""));
	}

	public boolean isInvariant() {
		return (invariantsFile != null) && (!invariantsFile.equals(""));
	}

	public void setLapFile(String lapFile) {
		this.lapFile = lapFile;
	}

	public void setAdjFile(String adjFile) {
		this.adjFile = adjFile;
	}

	public void setSgnLapFile(String sgnLapFile) {
		this.sgnLapFile = sgnLapFile;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setOptimizationFunction(String optimizationFunction) {
		this.optimizationFunction = optimizationFunction;
	}

	public String getOptimizationFunction() {
		return optimizationFunction;
	}

	public String getLapFile() {
		return lapFile;
	}

	public String getAdjFile() {
		return adjFile;
	}

	public String getSgnLapFile() {
		return sgnLapFile;
	}

	public String getHeader() {
		return header;
	}

	public boolean isAdjBar() {
		return (adjBarFile != null) && (!adjBarFile.equals(""));
	}

	public boolean isLapBar() {
		return (lapBarFile != null) && (!lapBarFile.equals(""));
	}

	public boolean isSgnLapBar() {
		return (sgnLapBarFile != null) && (!sgnLapBarFile.equals(""));
	}

	public String getAdjBarFile() {
		return adjBarFile;
	}

	public String getLapBarFile() {
		return lapBarFile;
	}

	public String getSgnLapBarFile() {
		return sgnLapBarFile;
	}

	public void setLapBarFile(String lapBarFile) {
		this.lapBarFile = lapBarFile;
	}

	public void setAdjBarFile(String adjBarFile) {
		this.adjBarFile = adjBarFile;
	}

	public void setSgnLapBarFile(String sgnlapBarFile) {
		this.sgnLapBarFile = sgnlapBarFile;
	}

	public boolean isUsingGreatestDegrees() {
		return this.omega != null;
	}

	public void setInvariantsFile(String workFolder, String inputFile) throws Exception {
		this.invariantsFile = inputFile;
		List<String> inputData = CsvReader.readFile(workFolder + "/saida.csv");
		
		
		if (inputData.size() > 1) {
			String header = inputData.get(0); // Get the CSV header

			for (int x = 1; x < inputData.size(); x++) {
				String line = inputData.get(x);
				String[] lineData = line.split(",");

				int index = CsvReader.getIndex("ChromaticNumber", header);
				if (index >= 0) {
					this.chi = lineData[index];
				}

				index = CsvReader.getIndex("ChromaticNumberComplement", header);
				if (index >= 0) {
					this.chiBar = lineData[index];
				}

				index = CsvReader.getIndex("LargestCliqueSize", header);
				if (index >= 0) {
					this.omega = lineData[index];
				}

				index = CsvReader.getIndex("LargestCliqueSizeComplement", header);
				if (index >= 0) {
					this.omegaBar = lineData[index];
				}

				index = CsvReader.getIndex("SequenceDegree", header);
				if (index >= 0) {
					this.kLargestDegree = lineData[index];
				}

				index = CsvReader.getIndex("NumberofEdges", header);
				if (index >= 0) {
					this.numEdges = lineData[index];
				}
			}
		}
	}

	public String getInvariantsFile() {
		return invariantsFile;
	}

	public String getKLargestDegree() {
		return kLargestDegree;
	}

	public String getNumEdges() {
		return numEdges;
	}

	public String getChi() {
		return chi;
	}

	public String getChiBar() {
		return chiBar;
	}

	public String getOmega() {
		return omega;
	}

	public String getOmegaBar() {
		return omegaBar;
	}

	public void setGrauMin(String grauMin ) {
		this.grauMin = grauMin;
		
	}
	
	public String getGrauMin() {
		return grauMin;
	}

	public void setGrauMax(String grauMax) {
		this.grauMax = grauMax;		
	}

	public void setRddKey(String rddKey) {
		this.rddKey = rddKey;
	}
	
	public String getGrauMax() {
		return grauMax;
	}
	
	public String getRddKey() {
		return rddKey;
	}
	
}
