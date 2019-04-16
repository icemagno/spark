package cmabreu.sagitarii.spectral;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Função responsável por gerar arquivos PDF com imagens GIF de grafos e os
 * valores de suas respectivas funções de otimização.
 * 
 * @author Hugo
 * @author Kaique
 * @author Lucas
 * 
 * Modifcado po Carlos Magno em 23/05/2015
 * 
 */

public class PDFCreator {

	public static void gerarPDF( List<JobUnity> jobs, String outputFile ) throws DocumentException, IOException {
		Font footerFont = new Font(FontFamily.COURIER, 8, 0, BaseColor.BLACK );
		
		Document document = new Document( PageSize.A4 );
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream( outputFile ) );
		document.open();
		
		document.addCreator("Apache Spark");
		document.addAuthor("Carlos Magno Abreu");
		document.addTitle("RioGraphX Portal");
		
		writer.setPageEvent( new HeaderAndFooter() );
		
		UnityComparator comparator = new UnityComparator();
		Collections.sort( jobs, comparator );
		
		int index = 0;
		for ( JobUnity job : jobs ) {
			String imageFileName = job.getImageFile();
			String evalValue = job.getEvalValue();
			int maxResults = Integer.valueOf( job.getMaxresults() );
			
			document.add(new Paragraph("Graph: [" + job.getTheGraph() + "]", footerFont));
			document.add(new Paragraph("Graph Order: " + job.getGorder(), footerFont));
			document.add(new Paragraph("Max Results: " + job.getMaxresults(), footerFont));
			document.add(new Paragraph("Function: " + job.getFunction(), footerFont));

			if ( job.getCaixa1().equals("max") ) {
				document.add(new Paragraph("Operation: Maximize", footerFont));
			} else {
				document.add(new Paragraph("Operation: Minimize", footerFont));
			}
			document.add( new Paragraph("\n\n") );

			if ( maxResults == index ) {
				break;
			}
			index++;
			
			// Graph image
			Image image = Image.getInstance( imageFileName );

			float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
		             - document.rightMargin() ) / image.getWidth()) * 60;
			image.scalePercent(scaler);			
			
			image.setAlignment(Image.MIDDLE);
			image.setBorder( Image.BOX );
			image.setBorderWidth(1);
			
			document.add(image);
			
			document.add( new Paragraph("\n\n") );
			
			// Function image
			LatexFunctionGenerator sc = new LatexFunctionGenerator();
			ByteArrayOutputStream stream = sc.getImageAsBaos( job.getFunctionReal() + " = " + evalValue ) ;			
			Image imgFunc = Image.getInstance( stream.toByteArray() );

			float scaler2 = ((document.getPageSize().getWidth() - document.leftMargin()
		               - document.rightMargin() ) / image.getWidth()) * 30;
			image.scalePercent(scaler2);			

			
			imgFunc.setAlignment(Image.MIDDLE);
			document.add(imgFunc);			
			
			document.newPage();
		}
		
		
		document.close();
		writer.close();
		
	}

}
