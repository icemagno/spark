package cmabreu.sagitarii.spectral;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;


public class LatexFunctionGenerator  {

	public InputStream getImage( String expression ) {

		try {
			TeXFormula fomule = new TeXFormula(expression);
			TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);
			BufferedImage image = new BufferedImage(ti.getIconWidth(),	ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			ti.paintIcon(new JLabel(), image.getGraphics(), 0, 0);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image,"png", os); 
			
			InputStream inputStream = new ByteArrayInputStream(os.toByteArray() );
			return inputStream;

		} catch (Exception ex) {
			//ex.printStackTrace();
			return null;
		}
		
	}

	public ByteArrayOutputStream getImageAsBaos( String expression ) {

		try {
			TeXFormula fomule = new TeXFormula(expression);
			TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);
			BufferedImage image = new BufferedImage(ti.getIconWidth(),	ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			ti.paintIcon(new JLabel(), image.getGraphics(), 0, 0);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image,"png", os); 
			return os;
		} catch (Exception ex) {
			//ex.printStackTrace();
			return null;
		}
		
	}

	
}
