/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pb138.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream; 
import javax.servlet.annotation.MultipartConfig;

/**
 *
 * @author Dusan Durajka
 */
@javax.servlet.annotation.WebServlet(name = "ActionServlet", urlPatterns = {"/upload", "/downloadBoth", "/index", "/downloadSRGS", "/download"})
@MultipartConfig
public class WebServlet extends HttpServlet {

    private String path;
    public File output;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.net.URISyntaxException
     * @throws javax.xml.transform.TransformerException
     */    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, URISyntaxException, TransformerException {
        response.setContentType("text/html;charset=UTF-8");
        
         if (request.getServletPath().equals("/upload")) {
             upload(request, response);
         }   
         
         if (request.getServletPath().equals("/index")) {
             request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
         } 
         
         if (request.getServletPath().equals("/downloadBoth")) {
              //downloadFull(request, response);
         }
         
         if (request.getServletPath().equals("/downloadSRGS")) {
              //downloadSRGS(request, response);
         }
         
         if (request.getServletPath().equals("/download")) {
              download(request, response);
         }
    }
    
    /**
     * Class which handles uploading of an automaton to the web application.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws URISyntaxException 
     */
    private void upload(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, URISyntaxException, TransformerException {
       
        //Creates Path components to save the file at the server location
        String destination = System.getProperty("user.dir");
        path = destination;
        final Part part = request.getPart("file");
        final String name = getFileName(part);
        
        OutputStream out = null;
        InputStream in = null;
        
        final PrintWriter writer = response.getWriter();
        
        try {
            int read;
            File source = new File(path + File.separator + name);
            out = new FileOutputStream(source);
            in = part.getInputStream();

            
            final byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            File xslt = new File("/transformation.xslt");
            //writer.println("New file " + name + " created at " + path);
            output = new File(path + File.separator + "output.vxml");
            
            this.transform(xslt, source);
            request.getRequestDispatcher("/index.html").forward(request, response);
            
            
        } catch (FileNotFoundException fne) {
        writer.println("File to upload was not specified correctly or"
                + " path is incorrect.");
        
        writer.println("<br/> ERROR: " + fne.getMessage());

        //Close unclosed channels.
        } finally {
            if (out != null) { out.close();
            }
            if (in != null) { in.close();
            }
            if (writer != null) {writer.close();
            }
        }
    }
    
    private void download(HttpServletRequest request, HttpServletResponse response)
            throws FileNotFoundException, IOException {
        
        final PrintWriter writer = response.getWriter();
        
        FileInputStream in = new FileInputStream(output);
        
        ServletContext context = getServletContext();
        
        response.setContentType("application/octet-stream");
        response.setContentLength((int) output.length());
        
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", output.getName());
        response.setHeader(headerKey, headerValue);
        
        try (OutputStream outStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            
            while ((bytesRead = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            in.close();
        } catch (IOException e) {
            writer.println("Error trying to download specified file.");
        
            writer.println("<br/> ERROR: " + e.getMessage());
        }
    }
    
    public void transform (File xslt, File source) 
           throws IOException, URISyntaxException, TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source transformation = new StreamSource(xslt);
        Transformer transformer = factory.newTransformer(transformation);
        Source text = new StreamSource(source);
        transformer.transform(text, new StreamResult(output));
    }
    
    //prerobit
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
        
    }
    
    
}
