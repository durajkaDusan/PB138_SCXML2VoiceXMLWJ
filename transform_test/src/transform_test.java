/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transform_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Dusan
 */
public class Transform_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File input = new File("Y:\\registration.scxml");
        File transformation= new File("Y:\\transformation.xsl");
        
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            //Access xslt file
            //FileInputStream inputStream = new FileInputStream(input);
            Source trans = new StreamSource(transformation);
            //Create Transformer
            Transformer transformer = factory.newTransformer(trans);
            //Add uploaded file to Source
            //InputStream sourceInput = new FileInputStream(path + File.separator + name);
            Source text = new StreamSource(input);
            //Create output file
            //OutputStream outputStream = new FileOutputStream(output);
            Result output2 = new StreamResult(new File("Y:\\output.html"));
            //Transform
            transformer.transform(text, output2);
            
        } catch (TransformerException e) {
            System.out.println("Chyba" + e);
        } 
    }
    
}
