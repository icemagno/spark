package cmabreu.sagitarii.spectral;



public class JobUnity {
	private String function;
	private String functionReal;
	private String imageFile;
	private String evalValue;
	private String caixa1;
	private String maxresults;
	private String gorder;
	private String theGraph;

	public JobUnity( String function, String imageFile, String evalValue, String caixa1, String maxresults, String gorder, 
			String theGraph, String functionReal ) {
		this.function = function;
		this.imageFile = imageFile;
		this.evalValue = evalValue;
		this.caixa1 = caixa1;
		this.maxresults = maxresults;
		this.gorder = gorder;
		this.theGraph = theGraph;
		this.functionReal = functionReal;
	}
	
	public String getFunctionReal() {
		return functionReal;
	}
	
	public String getMaxresults() {
		return maxresults;
	}
	
	public String getImageFile() {
		return imageFile;
	}
	
	public String getFunction() {
		return function;
	}
	
	public String getEvalValue() {
		return evalValue;
	}

	public String getCaixa1() {
		return caixa1;
	}
	
	public String getGorder() {
		return gorder;
	}
	
	public String getTheGraph() {
		return theGraph;
	}
	
}
