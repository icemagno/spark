package br.com.cmabreu;

import java.io.Serializable;
import java.util.UUID;

/*
//Para EIGSOLVE se: optifunc like '%lambda%' or sp.optifunc like '%mu%' or sp.optifunc like '%q!_%'
if ( function.contains("lambda") || function.contains("mu") || function.contains("q_") ) {
	System.out.println(" > Executar EIGSOLVE");	
}

//Para GENI   se: optifunc like '%omega%' or sp.optifunc like '%chi%' or sp.optifunc like '%SIZE%' or sp.optifunc like '%d!_%'
if ( function.contains("omega") || function.contains("chi") || function.contains("SIZE") || function.contains("d_") ) {
	System.out.println(" > EXECUTAR GENI");
	//  geni('/home/magno/riographx_data/','graphtest.g6','-a -b -c -d -e 3 -g');
	// ./rungeni.sh ./geni.py ./ ./graphtest.g6 "-a -b -c -d -e 3 -g"
}

*/

public class Graph implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer index_id;
	private String function;
	private String g6;
	private Integer ordem;
	private Integer grauminimo;
	private Integer graumaximo;
	private Integer trianglefree;
	private Integer conexo;
	private Integer bipartite;	
	private Integer parameter_id;	
	private String caixa1;	
	private String adjacency;	
	private String laplacian;	
	private String slaplacian;	
	private String allowdiscgraphs;	
	private String biptonly;	
	private String maxresults;	
	private String adjacencyb;	
	private String laplacianb;	
	private String slaplacianb;	
	private String chromatic;	
	private String chromaticb;	
	private String click;	
	private String clickb;	
	private String largestdegree;	
	private String numedges;

	/* Estes não pertencem ao grafo. São para controle do workflow. */
	private Integer runGeni;
	private Integer runEigsolve;
	private String serial;
	// --------------------------------------------------------------
	

	// Este só é preenchido quando a função é avaliada (depois do pipe() )
	private String functionResult;
	
	public Graph() {
		super();
	}
	
	public Graph(Integer index_id, String function, String g6, Integer ordem, Integer grauminimo, Integer graumaximo,
			Integer trianglefree, Integer conexo, Integer bipartite, Integer parameter_id, String caixa1,
			String adjacency, String laplacian, String slaplacian, String allowdiscgraphs, String biptonly,
			String maxresults, String adjacencyb, String laplacianb, String slaplacianb, String chromatic,
			String chromaticb, String click, String clickb, String largestdegree, String numedges) {
		this.index_id = index_id;
		this.function = function;
		this.g6 = g6;
		this.ordem = ordem;
		this.grauminimo = grauminimo;
		this.graumaximo = graumaximo;
		this.trianglefree = trianglefree;
		this.conexo = conexo;
		this.bipartite = bipartite;
		this.parameter_id = parameter_id;
		this.caixa1 = caixa1;
		this.adjacency = adjacency;
		this.laplacian = laplacian;
		this.slaplacian = slaplacian;
		this.allowdiscgraphs = allowdiscgraphs;
		this.biptonly = biptonly;
		this.maxresults = maxresults;
		this.adjacencyb = adjacencyb;
		this.laplacianb = laplacianb;
		this.slaplacianb = slaplacianb;
		this.chromatic = chromatic;
		this.chromaticb = chromaticb;
		this.click = click;
		this.clickb = clickb;
		this.largestdegree = largestdegree;
		this.numedges = numedges;

		// Já deixa registrado no próprio grafo se ele será passado para o GENI e/ou EIGSOLVE
		// dependendo da função. Obviamente todos os grafos terão os mesmos valores, mas
		// fica mais prático marcar aqui. Não tenho habilidade suficiente para shell scripts complexos.
		// No futuro pode-se passar a condicional para o script que vai decidir a execução ("sage.sh").
		this.runEigsolve = 0;
		this.runGeni = 0;
		if ( this.function.contains("lambda") || this.function.contains("mu") || this.function.contains("q_") ) {
			this.runEigsolve = 1;	
		}	
		
		if ( this.function.contains("omega") || this.function.contains("chi") || this.function.contains("SIZE") || this.function.contains("d_") ) {
			this.runGeni = 1;
		}		
		
		this.serial = UUID.randomUUID().toString().replaceAll("-", "");
		
	}	
	
	@Override
	public String toString() {
		String result = index_id + "," + getFunction() + "," + g6 + "," + ordem + "," + grauminimo + "," + graumaximo+ "," +trianglefree+ "," +conexo+ "," +
				bipartite+ "," +parameter_id+ "," +caixa1+ "," +adjacency+ "," +laplacian+ "," +slaplacian+ "," +allowdiscgraphs+ "," +
				biptonly+ "," +maxresults+ "," +adjacencyb+ "," +laplacianb+ "," +slaplacianb+ "," +chromatic+ "," +chromaticb+ "," +click+ "," +
				clickb+ "," +largestdegree+ "," +numedges+","+runGeni+","+runEigsolve+","+serial;
		
		//System.out.println("Solicitado grafo " + result);
		
		return result;
	}
	
	
	public Integer getIndex_id() {
		return index_id;
	}
	public void setIndex_id(Integer index_id) {
		this.index_id = index_id;
	}
	public String getFunction() {
		return function.replaceAll("\\\\", "\\\\\\\\");
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getG6() {
		return g6;
	}
	public void setG6(String g6) {
		this.g6 = g6;
	}
	public Integer getOrdem() {
		return ordem;
	}
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
	public Integer getGrauminimo() {
		return grauminimo;
	}
	public void setGrauminimo(Integer grauminimo) {
		this.grauminimo = grauminimo;
	}
	public Integer getGraumaximo() {
		return graumaximo;
	}
	public void setGraumaximo(Integer graumaximo) {
		this.graumaximo = graumaximo;
	}
	public Integer getTrianglefree() {
		return trianglefree;
	}
	public void setTrianglefree(Integer trianglefree) {
		this.trianglefree = trianglefree;
	}
	public Integer getConexo() {
		return conexo;
	}
	public void setConexo(Integer conexo) {
		this.conexo = conexo;
	}
	public Integer getBipartite() {
		return bipartite;
	}
	public void setBipartite(Integer bipartite) {
		this.bipartite = bipartite;
	}
	public Integer getParameter_id() {
		return parameter_id;
	}
	public void setParameter_id(Integer parameter_id) {
		this.parameter_id = parameter_id;
	}
	public String getCaixa1() {
		return caixa1;
	}
	public void setCaixa1(String caixa1) {
		this.caixa1 = caixa1;
	}
	public String getAdjacency() {
		return adjacency;
	}
	public void setAdjacency(String adjacency) {
		this.adjacency = adjacency;
	}
	public String getLaplacian() {
		return laplacian;
	}
	public void setLaplacian(String laplacian) {
		this.laplacian = laplacian;
	}
	public String getSlaplacian() {
		return slaplacian;
	}
	public void setSlaplacian(String slaplacian) {
		this.slaplacian = slaplacian;
	}
	public String getAllowdiscgraphs() {
		return allowdiscgraphs;
	}
	public void setAllowdiscgraphs(String allowdiscgraphs) {
		this.allowdiscgraphs = allowdiscgraphs;
	}
	public String getBiptonly() {
		return biptonly;
	}
	public void setBiptonly(String biptonly) {
		this.biptonly = biptonly;
	}
	public String getMaxresults() {
		return maxresults;
	}
	public void setMaxresults(String maxresults) {
		this.maxresults = maxresults;
	}
	public String getAdjacencyb() {
		return adjacencyb;
	}
	public void setAdjacencyb(String adjacencyb) {
		this.adjacencyb = adjacencyb;
	}
	public String getLaplacianb() {
		return laplacianb;
	}
	public void setLaplacianb(String laplacianb) {
		this.laplacianb = laplacianb;
	}
	public String getSlaplacianb() {
		return slaplacianb;
	}
	public void setSlaplacianb(String slaplacianb) {
		this.slaplacianb = slaplacianb;
	}
	public String getChromatic() {
		return chromatic;
	}
	public void setChromatic(String chromatic) {
		this.chromatic = chromatic;
	}
	public String getChromaticb() {
		return chromaticb;
	}
	public void setChromaticb(String chromaticb) {
		this.chromaticb = chromaticb;
	}
	public String getClick() {
		return click;
	}
	public void setClick(String click) {
		this.click = click;
	}
	public String getClickb() {
		return clickb;
	}
	public void setClickb(String clickb) {
		this.clickb = clickb;
	}
	public String getLargestdegree() {
		return largestdegree;
	}
	public void setLargestdegree(String largestdegree) {
		this.largestdegree = largestdegree;
	}
	public String getNumedges() {
		return numedges;
	}
	public void setNumedges(String numedges) {
		this.numedges = numedges;
	}	
	
	public Integer getRunGeni() {
		return runGeni;
	}
	
	public Integer getRunEigsolve() {
		return runEigsolve;
	}
	
	public String getSerial() {
		return serial;
	}
	
	public void setFunctionResult(String functionResult) {
		this.functionResult = functionResult;
	}
	
	public String getFunctionResult() {
		return functionResult;
	}
	
	public void setSerial(String serial) {
		this.serial = serial;
	}
	
}
