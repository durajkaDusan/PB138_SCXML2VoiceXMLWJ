package pb138.web;

import pb138.transformer.Transformer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URISyntaxException;

import javax.servlet.http.Part;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;

import javax.xml.transform.TransformerException;

/**
 *Main Class of whole utility. Handles upload and download.
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
     * @throws javax.xml.transform.TransformerException
     */    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, URISyntaxException, TransformerException{
        
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
    
    /**
     * Handles upload of file, calls transformation method, checks validity. 
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws URISyntaxException
     * @throws TransformerException 
     */
    private void upload(HttpServletRequest request, HttpServletResponse response) 
            throws URISyntaxException, IOException, ServletException{
 
        //Acces server destination
        String destination = System.getProperty("user.dir");
        
         // Create path components to save the file
        path = destination;
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);

        //Create streams
        OutputStream out = null;
        InputStream filecontent = null;
        
        //Create writer 
        final PrintWriter writer = response.getWriter();
        
        try {
            
            int read;
            
            //Get file from path, create streams
            File file = new File(path + File.separator + fileName);
            out = new FileOutputStream(file);
            filecontent = filePart.getInputStream();
            
            //Upload logic
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            //Flush and close.
            out.flush();
            out.close();
            
            //create instance of transformer class
            Transformer transformer = new Transformer();
            
            //transform scxml file to voicexml
            String message = transformer.transform(path, fileName);
            
            //Set attributes for jsp page
            request.setAttribute("message", "Your file " + fileName + "was uploaded at: " + path);
            request.setAttribute("messageSCXML", message);
            request.setAttribute("path", path + File.separator + "output.vxml");
            request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
            
        } catch (FileNotFoundException  fne) {
            writer.println("<script>alert(\"You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.\\nERROR: " + fne.getMessage() + "\");"
                    + "window.location.href = '/index'</script>");            
        } catch (TransformerException trans) {
            writer.println("<script>alert(\"The file " + fileName 
                    + "is not a valid scxml file. Please upload valid SCXML file!\\nERROR: " + trans.getMessage() + "\")</script>");
        } catch (ServletException serv) {
            writer.println("<script>alert(\"Servlet encountered an error.\\nERROR: " + serv.getMessage() + "\")</script>");
        } catch (IOException io) {
            writer.println("<script>alert(\"IO Error: .\\nERROR: " + io.getMessage() + "\")</script>");
        } 
        
        finally {
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
    
    /**
     * Handles downloading of file from server, forcing save as dialog window.
     * 
     * @param request
     * @param response
     * @throws IOException 
     */
    private void download(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        response.setContentType("application/octet-stream");
        //Force save as dialog window
        response.setHeader("Content-Disposition",
        "attachment;filename=output.vxml");
        
        String fileName;
        fileName = path + File.separator + "output.vxml";
        
        // Setting Streams
        File file = new File(fileName);
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
      
        //download logic
        byte[] outputByte = new byte[(int)file.length()];
        //copy binary contect to output stream
        while(fileIn.read(outputByte, 0, (int)file.length()) != -1) {
                out.write(outputByte, 0, (int)file.length());
        }
        
        //flush and close streams
        fileIn.close();
        out.flush();
        out.close(); 
    }
  
    /**
     * Inspects name of a file uploaded by user.
     * 
     * @param part
     * @return Name of the uploaded file
     */
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
