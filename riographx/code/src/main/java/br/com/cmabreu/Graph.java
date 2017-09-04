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
	
	
	public Graph(Integer index_id, String function, String g6, Integer ordem, Integer grauminimo, Integer graumaximo,
			Integer trianglefree, Integer conexo, Integer bipartite) {
		super();
		this.index_id = index_id;
		this.function = function;
		this.g6 = g6;
		this.ordem = ordem;
		this.grauminimo = grauminimo;
		this.graumaximo = graumaximo;
		this.trianglefree = trianglefree;
		this.conexo = conexo;
		this.bipartite = bipartite;
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
	public Integer getIndex_id() {
		return index_id;
	}
	public void setIndex_id(Integer index_id) {
		this.index_id = index_id;
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
	

}
