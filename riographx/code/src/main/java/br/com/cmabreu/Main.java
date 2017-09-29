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
		
		String sageScript = "sage.sh";
		String showgScript = "showg.sh";
		
		try { sageScript = String.valueOf( args[2] ); } catch ( Exception e ) {}
		try { showgScript = String.valueOf( args[3] ); } catch ( Exception e ) {}

		DriverApplication da = new DriverApplication();
		da.run( indexParameter, workDir, sageScript, showgScript );
		
	}	


}
