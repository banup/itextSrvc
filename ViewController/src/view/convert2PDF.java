package view;

import java.io.FileOutputStream;
import java.io.File;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.BindingType;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.http.client.fluent.Request;

@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@MTOM
public class convert2PDF {
    public convert2PDF() {
        super();
        
    }

    @WebMethod
    public String convert(@WebParam(name = "strURL") String strURL,
                          @WebParam(name = "pdfFileName") String pdfFileName){
        /*
        */
        /*//path for the PDF file to be generated*/
        File directory = new File (".");
        String path;
        path="";
      
        PdfWriter pdfWriter = null;
        /*//create a new document*/
        Document document = new Document();
        try {
            path = directory.getCanonicalPath()+ "\\"+ pdfFileName;;

            //get Instance of the PDFWriter
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(path));

            //document header attributes
            document.addAuthor("betterThanZero");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("MySampleCode.com");
            document.addTitle("Demo for iText XMLWorker");
            document.setPageSize(PageSize.A4.rotate());

            //open document
            document.open();

            //To convert a HTML file from the filesystem
            //String File_To_Convert = "docs/SamplePDF.html";
            //FileInputStream fis = new FileInputStream(File_To_Convert);
            //URL for HTML page
            URL myWebPage = new URL(strURL);


            Request.Get(strURL).execute().returnContent();
            InputStream is;
            is = Request.Get(strURL).execute().returnContent().asStream();
            InputStreamReader fis = new InputStreamReader(is);
            //InputStreamReader fis = new InputStreamReader(myWebPage.openStream());
            // InputStreamReader fis2 = new InputStreamReader(print_content(con));


            //get the XMLWorkerHelper Instance
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            //convert to PDF
            worker.parseXHtml(pdfWriter, document, fis);

            //close the document
            document.close();
            //close the writer
            pdfWriter.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return path;
    }
}
