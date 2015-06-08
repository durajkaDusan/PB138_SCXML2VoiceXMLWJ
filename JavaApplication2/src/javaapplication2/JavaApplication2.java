/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Dusan
 */
public class JavaApplication2 {



public static void main(String[] args) throws IOException, URISyntaxException, TransformerException {
        
        TransformerFactory factory = TransformerFactory.newInstance();
        
        Source xslt = new StreamSource(new File("Y:\\transformation.xsl"));
        
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("Y:\\registration.scxml"));
        
        transformer.transform(text, new StreamResult(new File("Y:\\output.html")));
}

    
}
