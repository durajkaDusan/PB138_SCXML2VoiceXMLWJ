/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.22
 * Generated at: 2015-06-06 11:07:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_classes = null;
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"DTD/xhtml1-strict.dtd\">\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">     \n");
      out.write("\n");
      out.write("    <head>\n");
      out.write("        <title>SCXML to VoiceXML Web Converter</title>\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />        \n");
      out.write("        <link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/css/layout.css\"> \n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("    <body>                   \n");
      out.write("        <div class=\"container\">\n");
      out.write("            <div class=\"uploadBox\">\n");
      out.write("                <h1>SCXML to VoiceXML Web Converter</h1>\n");
      out.write("                <p>Please choose a SCXML file from your computer:</p>\n");
      out.write("                <form method=\"POST\" action=\"upload\" enctype=\"multipart/form-data\" >\n");
      out.write("                    File:<input type=\"file\" name=\"file\" id=\"file\" /><br/></br>\n");
      out.write("                    <input type=\"submit\" value=\"Upload\" name=\"upload\" id=\"upload\" />                    \n");
      out.write("                </form>\n");
      out.write("            </div>\n");
      out.write("            <hr>\n");
      out.write("            <div class=\"codeBox\">    \n");
      out.write("                <div class=\"scxmlBox\">\n");
      out.write("                    <form method=\"GET\" action=\"download\" >\n");
      out.write("                        <input type=\"submit\" value=\"Download SCXML file\" name=\"DownloadXML\" style=\"text-align: left\"/>\n");
      out.write("                    </form>                                         \n");
      out.write("                    \n");
      out.write("                </div>\n");
      out.write("                        \n");
      out.write("            </div>    \n");
      out.write("              \n");
      out.write("            <footer>\n");
      out.write("                <p style=\"text-align: left;\"><a href=\"http://durajkadusan.github.io/PB138_SCXML2VoiceXMLWJ/\">Project homepage</a><br />\n");
      out.write("                <a href=\"https://github.com/durajkaDusan/PB138_SCXML2VoiceXMLWJ/wiki\">Wiki</a><br /><br />\n");
      out.write("                <b>Developers:</b><ul>\n");
      out.write("                                      <li>Dusan Durajka, <a href=\"https://is.muni.cz/auth/osoba/410406\">410406</a></li>\n");
      out.write("                                      <li>Jana Szabadosova, <a href=\"https://is.muni.cz/auth/osoba/409839\">409839</a></li>\n");
      out.write("                                      <li>Filip Kejnar, <a href=\"https://is.muni.cz/auth/osoba/410229\">410229</a></li>\n");
      out.write("                                      <li>Milan Spicuk. <a href=\"https://is.muni.cz/auth/osoba/409899\">409899</a></li>\n");
      out.write("                                  </ul>\n");
      out.write("                </p>\n");
      out.write("            </footer>\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</h");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
