package cmabreu.sagitarii.spectral;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderAndFooter extends PdfPageEventHelper {
    private static Font footerFont = new Font(FontFamily.COURIER, 8, 0, BaseColor.GRAY );

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Phrase("Centro Federal de Educação Tecnológica Celso Suckow da Fonseca - CEFET-RJ",footerFont), 
                document.leftMargin() - 1, document.bottom() - 10, 0);

        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Phrase("Escola de Informática e Computação - EIC",footerFont), 
                document.leftMargin() - 1, document.bottom() - 20, 0);
        
        
        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(String.format(" %d ", writer.getPageNumber()),footerFont), 
                document.right() - 2 , document.bottom() - 10, 0);

        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase("Powered by Apache Spark",footerFont), 
                document.right() - 2 , document.bottom() - 20, 0);
        
        
    }
    
}
