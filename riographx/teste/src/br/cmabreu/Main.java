package br.cmabreu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	  public static void main(String[] args) throws IOException {
		    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		    String s;
		    while ((s = in.readLine()) != null && s.length() != 0)
		      System.out.println(" Recebido: " + s);
		    // An empty line or Ctrl-Z terminates the program
	  }	
	
}
