package cmabreu.sagitarii.spectral;

public class EvaluationInfo {
	public String optimizationFunction;
	public String[] valuesAdjs;
	public String[] valuesLaps;
	public String[] valuesSgnlaps;
	public String kLargestDegree;
	public String numEdges;
	public String[] valuesDs;
	public String[] valuesAdjBars;
	public String[] valuesLapBars;
	public String[] valuesSgnlapBars;
	public String valueChiAdj;
	public String valueChiAdjBar;
	public String valueOmegaAdj;
	public String valueOmegaAdjBar;
	public String gorder;

	public EvaluationInfo(
			String optimizationFunction, 
			String[] valuesAdjs,
			String[] valuesLaps, 
			String[] valuesSgnlaps, 
			String kLargestDegree,
			String numEdges, 
			String[] valuesDs, 
			String[] valuesAdjBars,
			String[] valuesLapBars, 
			String[] valuesSgnlapBars,
			String valueChiAdj, 
			String valueChiAdjBar, 
			String valueOmegaAdj,
			String valueOmegaAdjBar,
			String gorder) {
		this.optimizationFunction = optimizationFunction;
		this.valuesAdjs = valuesAdjs;
		this.valuesLaps = valuesLaps;
		this.valuesSgnlaps = valuesSgnlaps;
		this.kLargestDegree = kLargestDegree;
		this.numEdges = numEdges;
		this.gorder = gorder;
		this.valuesDs = valuesDs;
		this.valuesAdjBars = valuesAdjBars;
		this.valuesLapBars = valuesLapBars;
		this.valuesSgnlapBars = valuesSgnlapBars;
		this.valueChiAdj = valueChiAdj;
		this.valueChiAdjBar = valueChiAdjBar;
		this.valueOmegaAdj = valueOmegaAdj;
		this.valueOmegaAdjBar = valueOmegaAdjBar;
	}
}