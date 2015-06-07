package pb138.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.servlet.ServletContext;

import javax.servlet.http.Part;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;

import javax.xml.parsers.ParserConfigurationException;
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
 * @author Dusan Durajka
 */
@WebServlet(name = "AppServlet", urlPatterns = {"/upload", "/download", "/index"})
@MultipartConfig
public class AppServlet extends HttpServlet {

    
    private String path;
    private File output;
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
            throws ServletException, IOException, URISyntaxException, TransformerException {
        response.setContentType("text/html;charset=UTF-8");
        
         if (request.getServletPath().equals("/upload")) {
             upload(request, response);
         }   
         
         if (request.getServletPath().equals("/index")) {
             request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
         } 
         
         if (request.getServletPath().equals("/download")) {
              download(request, response);
         }
         
         
    }
    
    private void upload(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, URISyntaxException, TransformerException {
        
// Uploaded file destination
        final PrintWriter writer = response.getWriter();
        
        String destination = System.getProperty("user.dir");
       
        path = destination;
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        OutputStream out = null;
        InputStream filecontent = null;
        

        try {
            int read;
            File file = new File(path + File.separator + fileName);
            
            final byte[] bytes = new byte[1024];
            
            out = new FileOutputStream(file);
           
            filecontent = filePart.getInputStream(); 
           
            
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            String outputPath = path + File.separator + "output.vxml";
            output = new File(outputPath);
            output.getParentFile().mkdirs(); 
            output.createNewFile();
                                           
            transform(path, fileName); 
            
            request.setAttribute("path", path + File.separator + "output.vxml");
            request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
            
        } catch (FileNotFoundException | TransformerException fne) {
            writer.println("<script>alert(\"You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.\\nERROR: " + fne.getMessage() + "\");"
                    + "window.location.href = '/index'</script>");            
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }            
        }        
    }
    
    private void download(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
        "attachment;filename=output.vxml");
        
        String fileName;
        fileName = path + File.separator + "output.vxml";
        
        // Setting Streams
        File file = new File(fileName);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
      
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileIn.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        
        fileIn.close();
        out.flush();
        out.close(); 
        
        
        /*
        response.setContentType("application/voicexml+xml"); 
        response.setHeader("Content-Disposition", "attachment filename="+output.getName()+".vxml"); 
        
        OutputStream out = response.getOutputStream();
        
        FileInputStream in = new FileInputStream(output);
        
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        
        in.close();
        out.flush();
        */
    }
    
    public void transform (String path, String name) throws TransformerConfigurationException, TransformerException, FileNotFoundException{
        if (path == null) {
            throw new NullPointerException("Path is set to null!");
        }
        if (name == null) {
            throw new NullPointerException("Name is set to null!");
        }
        try {
       
            TransformerFactory tf = TransformerFactory.newInstance();
            
            Transformer xsltProc = tf.newTransformer(
                    
                    new StreamSource(getClass().getResourceAsStream("transformation.xsl")));

            xsltProc.transform(
                    new StreamSource(new File(path + File.separator + name)),
                    new StreamResult(new File(path + File.separator + "output.vxml")));

            
        } catch (TransformerException e) {
            Logger.getLogger(WebServlet.class.getName()).log(Level.SEVERE, null, e);
        } 
         
    }
    
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
        try {
            processRequest(request, response);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AppServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(AppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AppServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(AppServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
