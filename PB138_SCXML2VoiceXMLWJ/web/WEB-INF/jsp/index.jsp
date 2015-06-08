<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "DTD/xhtml1-strict.dtd">

<%-->Author: Jana Szabadosova<--%>

<html xmlns="http://www.w3.org/1999/xhtml">     

    <head>
        <title>SCXML to VoiceXML Web Converter</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css"> 
    </head>

    <body>
        <header>
            <h1>SCXML2VXML</h1>
            <p><b>State Chart XML to Voice XML Converter</b></p>
            <p><i>School project for course PB138 at FI MUNI Brno.</i></p>
        </header>
        
        <div class="container">
            
            <div class="uploadBox">
                <p class="innerP">Please choose a SCXML file from your computer:</p>
                <form method="POST" action="upload" enctype="multipart/form-data" >
                    File:<input type="file" name="file" id="file" /><br/></br>
                    <input type="submit" value="Upload" name="upload" id="upload" />                    
                </form>
            </div>
            
            <div class="codeBox">    
                <div class="scxmlBox">
                    <form method="GET" action="download" >
                        <input type="submit" value="Download" name="DownloadXML" style="text-align: left"/>
                    </form>                                         
                    
                </div>
                        
            </div>    
        </div>
        
        <div class="statusBar">
            <p><b>Upload Status:</b> ${message}</p>
            <p><b>SCXML Status:</b> ${messageSCXML}</p>
        </div>
        
        <footer>
                <p><b>Here you can find out more about our application:</b></p>
                <p><a href="http://durajkadusan.github.io/PB138_SCXML2VoiceXMLWJ/">Project homepage</a><br />
                <a href="https://github.com/durajkaDusan/PB138_SCXML2VoiceXMLWJ/wiki">Wiki</a><br /><br />
                <b>Developers:</b><ul>
                                      <li>Dusan Durajka, <a href="https://is.muni.cz/auth/osoba/410406">410406</a></li>
                                      <li>Jana Szabadosova, <a href="https://is.muni.cz/auth/osoba/409839">409839</a></li>
                                      <li>Filip Kejnar, <a href="https://is.muni.cz/auth/osoba/410229">410229</a></li>
                                      <li>Milan Spicuk. <a href="https://is.muni.cz/auth/osoba/409899">409899</a></li>
                                  </ul>
                </p>
            </footer>
        
    </body>
</h