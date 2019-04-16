package matrix.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Matrix2Dot {

	
	public static void main(String[] args) throws Exception{
		converterMatriz( args[0], args[1] );		
	}

	
	
	/**
	 * Função que converte um arquivo com matriz de adjacência e converte para
	 * um arquivo DOT no formato de entrada do graphViz.
	 * 
	 * @param inputFile
	 *            Nome do arquivo com a matriz a ser convertida.
	 * @throws IOException
	 *             Quando não achar o arquivo especificado no parâmetro.
	 */
	public static void converterMatriz(String inputFile, String outputFile) throws IOException {
		// Try it!! 
		// http://graphviz-dev.appspot.com/
		File f = new File(inputFile);
		BufferedReader in = new BufferedReader(new FileReader(f));
		Map<String,String> controle = new HashMap<String,String>();

		File g = new File( outputFile );
		BufferedWriter out = new BufferedWriter(new FileWriter(g));
		out.write("graph Graph_1 {");
		out.newLine();
		String linha = in.readLine();

		int verticeOrigem = 1;
		int verticeDestino = 1;
		while ( in.ready() ) {
			linha = in.readLine();
			//System.out.println( "linha " + verticeOrigem + ": " + linha );
			for (int i = 0; i < linha.length(); i++) {
				if (linha.charAt(i) == '0') {
					verticeDestino++;
				}
				
				if (linha.charAt(i) == '1') {
					String vO = "V" + verticeOrigem;
					String vD = "V" + verticeDestino;
					String chave = vO+vD;
					String invertedChave = vD+vO;
					if ( !controle.containsKey( invertedChave ) ) {
						out.write("   " + vO + " -- " + vD );
						out.newLine();
						controle.put(chave, chave);
					}
					verticeDestino++;
				}
			}
			verticeOrigem++;
			verticeDestino = 1;
		}

		in.close();
		out.write('}');
		out.close();
	}
}
