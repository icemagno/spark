package cmabreu.sagitarii.spectral;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;

public class Test {
	private static List<JobUnity> jobs = new ArrayList<JobUnity>();
	
	public static void main(String[] args) throws DocumentException, IOException {
		String imageFile = "d:/indice.png";
		String function =  "\\frac{q_3}{d_4} + \\lambda_1 + \\overline{q_3} + \\mu_2 + \\overline{\\mu_2} + \\overline{\\lambda_1} + \\chi + \\overline{\\chi} + \\omega + \\overline{\\omega}";
		String evalValue = "23";
		String caixa1 = "max";
		String maxresults = "20";		
		
		jobs.add( new JobUnity(function, imageFile, evalValue, caixa1, maxresults, "2", "LLL", "LLL") );
		jobs.add( new JobUnity(function, imageFile, evalValue, caixa1, maxresults, "3", "LLL", "LLL") );
		jobs.add( new JobUnity(function, imageFile, evalValue, caixa1, maxresults, "6", "LLL", "LLL") );
		
		PDFCreator.gerarPDF( jobs, "d:/" );
	}

}
