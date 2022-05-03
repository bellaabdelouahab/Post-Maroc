package Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javafx.stage.FileChooser;
import oracle.net.aso.s;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;


public class pdfGenerator {
    public static void SavePdfForm(String Courier_id) throws IOException, FileNotFoundException,Exception{
        try {
            PdfReader reader = new PdfReader("src/Resources/PDF/Courier_form.pdf");
            // ask user for path 
            FileChooser fileChooser = new FileChooser();    
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fileChooser.showSaveDialog(App.getpStage());
            // create a pdf stamper
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(file));
            // create a pdf form
            // PdfFormField form = PdfFormField.createEmpty(stamper.getWriter());
            PdfContentByte pageContentByte =  stamper.getOverContent(1);
    
            //Create BaseFont instance.
            BaseFont baseFont = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            // add image
            // pageContentByte.addImage(Image.getInstance("src/Resources/Images/alert.png"));
            pageContentByte.beginText();
            //Set text font and size.
            pageContentByte.setFontAndSize(baseFont, 14);
            pageContentByte.setTextMatrix(260,640);
            System.out.println("stamper");
            //Write text
            pageContentByte.showText(Courier_id);
            pageContentByte.endText();
            // close the pdf
            stamper.close();
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            
        }
        
    }
    
}
