package pb138.transformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Milan Spicuk
 */
public class Transformer {
    
    /**
     * Transforming scxml file using TransformerFactory and XSLT style sheet 
     * 
     * @param path
     * @param name
     * @return Information about validity of the file
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws FileNotFoundException 
     */
    public String transform (String path, String name) throws TransformerConfigurationException, TransformerException, FileNotFoundException{
        if (path == null) {
            throw new NullPointerException("Path is set to null!");
        }
        if (name == null) {
            throw new NullPointerException("Name is set to null!");
        }
        try {
       
            TransformerFactory tf = TransformerFactory.newInstance();
            
            // create XSLT transformer
            javax.xml.transform.Transformer xsltProc = tf.newTransformer(
                    new StreamSource(getClass().getResourceAsStream("transformation.xsl")));
            
            // set it up for formatting
            xsltProc.setOutputProperty(OutputKeys.INDENT, "yes");
            xsltProc.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            // and transform the file
            xsltProc.transform(
                    new StreamSource(new File(path + File.separator + name)),
                    new StreamResult(new File(path + File.separator + "output.vxml")));

            
        } catch (TransformerException e) {
            Logger.getLogger(WebServlet.class.getName()).log(Level.SEVERE, null, e);
            return "SCXML file is not valid! Please, upload valid file.";
        } 
        return "SCXML file is valid. Proceed with download of VoiceXML."; 
    }
    
}
