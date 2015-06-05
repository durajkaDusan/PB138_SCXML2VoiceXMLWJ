<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">      
    <head>
        <title>SCXML to SVG Web Converter</title>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />        
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/layout.css"/>" />           
        <link rel="stylesheet" type="text/css" href="<c:url value="/codemirror/lib/codemirror.css" />" />                
        <link rel="stylesheet" type="text/css" href="<c:url value="/codemirror/theme/pastel-on-dark.css" />" />                

        <link href="<c:url value="/jquery/css/smoothness/jquery-ui-1.10.4.custom.css"/>" rel="stylesheet">
	<script src="<c:url value="/jquery/js/jquery-1.10.2.js"/>"></script>
	<script src="<c:url value="/jquery/js/jquery-ui-1.10.4.custom.js"/>"></script>
                
        <script type="text/javascript" src="<c:url value="/codemirror/lib/codemirror.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/codemirror/mode/javascript/javascript.js"/>"></script>
        <script>
            $(function() {
                $(".resizable").resizable({
                    handles: 's, n'
                });
            });
        </script>
    </head>

    <body>                   
        <div class="container">
            <div class="uploadBox">
                <h1>Welcome to SCXML to Converter</h1>
                <p>
                    Please select SCXML file from your computer:
                </p>
                <form method="POST" action="upload" enctype="multipart/form-data" >
                    File:<input type="file" name="file" id="file" /><br/></br>
                    <input type="submit" value="Upload" name="upload" id="upload" />                    
                </form>
            </div>
            <hr>
            <div class="codeBox">    
                <div class="scxmlBox">
                    <form method="GET" action="download" >
                        <input type="submit" value="Download SCXML file" name="DownloadXML" style="text-align: left"/>
                    </form>                                         
                    
                </div>
                        
            </div>    
                        
              
            <footer style="color: silver;">
                <hr>
                    <p style="text-align: left;">Project homepage: <a href="www.google.sk">---</a><br />
                        Wiki: <a href="www.google.sk">---</a><br /><br />
                        <b>Developers:</b><ul><li>DD</li><li>JS</li><li>MŠ</li><li>FK</li></ul></p>
            </footer>
        </div>
    </body>
</h