package br.com.cmabreu;

public class Main {

	public static void main(String[] args) {

		// O indice dos parametros na tabela devera ser passado no parametro 1
		// O diretorio de trabalho devera ser passado no parametro 2
		
		if ( args.length < 2 ) {
			System.out.println("Falta parametros! Saindo...");
			System.exit(0);
		}
		
		String indexParameter 	= String.valueOf( args[0] );
		String workDir 			= String.valueOf( args[1] );
		String sageScript 		= "sage.sh";
		String showgScript 		= "showg.sh";
		int parallelism 		= 7;
		
		new DriverApplication().run( indexParameter, workDir, sageScript, showgScript, parallelism );
		
	}	


}
