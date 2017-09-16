package br.com.cmabreu;

public class Main {

	public static void main(String[] args) {

		// O indice dos parametros na tabela devera ser passado
		if ( args.length == 0 ) {
			System.out.println("Falta parametros! Saindo...");
			System.exit(0);
		}
		String indexParameter = String.valueOf( args[0] );
		String workDir = String.valueOf( args[1] );
		String sageScript = String.valueOf( args[2] );

		DriverApplication da = new DriverApplication();
		da.run(indexParameter, workDir, sageScript);
		
	}	


}
