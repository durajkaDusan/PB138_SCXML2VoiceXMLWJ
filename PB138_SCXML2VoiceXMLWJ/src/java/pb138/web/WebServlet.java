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
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author Dusan Durajka
 */
@javax.servlet.annotation.WebServlet(name = "ActionServlet", urlPatterns = {"/upload", "/downloadBoth", "/index", "/downloadSRGS", "downloadXML"})
public class WebServlet extends HttpServlet {

    private String path;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.net.URISyntaxException
     */    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, URISyntaxException {
        response.setContentType("text/html;charset=UTF-8");
        
         if (request.getServletPath().equals("/upload")) {
             //uploadFile(request, response);
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
         
         if (request.getServletPath().equals("/downloadVXML")) {
              //downloadVoiceXML(request, response);
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
            throws ServletException, IOException {
       
        //Create Path components to save the file
        final String destination = request.getParameter("destination");
        final Part part = request.getPart("file");
        final String name = getFileName(part);
        
        OutputStream out = null;
        InputStream in = null;
        
        final PrintWriter writer = response.getWriter();
        
        try {
            out = new FileOutputStream(new File(path + File.separator + name));
            in = part.getInputStream();

            File source = new File(path + File.separator + name);
            
            int read;
            final byte[] bytes = new byte[(int)source.length()];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            File output = new File(path + File.separator + "output.vxml");
                    
            /**
             * INSERT code handling transformations!
             * transform (File xslt, source, output)
             * request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
             */
            
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
        
        File downloadFile = new File(path);
        FileInputStream in = new FileInputStream(downloadFile);
        
        ServletContext context = getServletContext();
        
        response.setContentType("application/octet-stream");
        response.setContentLength((int) downloadFile.length());
        
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
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
    
    
    public File transform (File xslt, File source, File destination) 
           throws IOException, URISyntaxException, TransformerException {
        
        TransformerFactory factory = TransformerFactory.newInstance();
        Source transformation = new StreamSource(xslt);
        Transformer transformer = factory.newTransformer(transformation);

        Source text = new StreamSource(source);
        transformer.transform(text, new StreamResult(destination));
        return destination;
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
