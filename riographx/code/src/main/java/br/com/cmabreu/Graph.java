package br.com.cmabreu;

import java.io.Serializable;

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
	}	
	
	@Override
	public String toString() {
		return g6 + "," + ordem + "," + grauminimo + "," + graumaximo;
	}
	
	
	public Integer getIndex_id() {
		return index_id;
	}
	public void setIndex_id(Integer index_id) {
		this.index_id = index_id;
	}
	public String getFunction() {
		return function;
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
	

	

}
