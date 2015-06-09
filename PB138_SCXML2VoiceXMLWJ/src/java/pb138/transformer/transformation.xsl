<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transformation.xsl
    Author     : Filip Kejnar
    Description:
        Transformation of State Chart XML file into Voice XML
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0"
                xmlns="http://www.w3.org/2001/vxml"
                xmlns:sc="http://www.w3.org/2005/07/scxml" 
                xmlns:vxml="http://www.w3.org/2001/vxml" 
                exclude-result-prefixes="vxml sc">
    
    <xsl:output method="xml"
                version="1.0"
                encoding="UTF-8"
                indent="yes" />
                
    <xsl:template match="/sc:scxml"><xsl:text>
</xsl:text>
        <xsl:element name="vxml">
            <xsl:attribute name="version">2.0</xsl:attribute>
            <xsl:element name="form">
                <xsl:attribute name="id">main</xsl:attribute>
                <xsl:apply-templates select="./sc:datamodel" />
                <xsl:variable name="initial_id" select="@initial" />
                <xsl:apply-templates select="sc:state[@id=$initial_id]" mode="initial" />
                <xsl:apply-templates select="sc:state[@id!=$initial_id]" />
                <xsl:apply-templates select="sc:final" />
            </xsl:element>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="sc:state | sc:parallel" mode="initial">
        <xsl:element name="initial">
            <xsl:attribute name="name">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:apply-templates select="./sc:onentry" />
            <xsl:apply-templates select="./sc:onexit" />
            <xsl:apply-templates select="./sc:transition" />
            <xsl:call-template name="inputcheck"/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="sc:state | sc:parallel">
        <xsl:choose>
            <xsl:when test="./@id='All'">
                <xsl:element name="filled">
                    <xsl:attribute name="mode">All</xsl:attribute>
                    <xsl:apply-templates />
                    <xsl:element name="filled">
                        <xsl:element name="if">
                            <xsl:attribute name="expr">All=='<xsl:value-of select="./sc:transition[@target=(../../sc:final[1]/@id)]/@event"/>'</xsl:attribute>
                        </xsl:element>
                        <xsl:element name="goto">
                            <xsl:attribute name="next"><xsl:value-of select="../sc:final[1]/@id"/></xsl:attribute>
                        </xsl:element>
                        <else />
                        <xsl:element name="clear">
                            <xsl:attribute name="namelist">
                                <xsl:for-each select="../sc:state">
                                    <xsl:value-of select="./@id"/>
                                    <xsl:text> </xsl:text>
                                </xsl:for-each>
                            </xsl:attribute>
                        </xsl:element>
                    </xsl:element>
                </xsl:element>
            </xsl:when>
            <xsl:otherwise>
                <xsl:element name="field">
                    <xsl:attribute name="name">
                        <xsl:value-of select="@id"/>
                    </xsl:attribute>
                    <xsl:apply-templates />
                    <xsl:call-template name="inputcheck"/>
                </xsl:element>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template match="sc:final" >
        <xsl:element name="field">
            <xsl:attribute name="name"><xsl:value-of select="@id"/></xsl:attribute>
            <xsl:apply-templates />
        </xsl:element>
    </xsl:template>
        
    <xsl:template match="sc:transition"></xsl:template>
    
    <xsl:template match="sc:onentry"><xsl:apply-templates/></xsl:template>
    
    <xsl:template match="sc:onexit">
        <filled>
            <xsl:apply-templates/>
        </filled>
    </xsl:template>
    
    <xsl:template match="vxml:*">
        <xsl:element name="{local-name()}" >
            <xsl:for-each select="./@*">
                <xsl:copy />
            </xsl:for-each>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="sc:datamodel">
        <xsl:element name="grammar">
            <xsl:attribute name="src">
                <xsl:value-of select="sc:data/@expr" />
            </xsl:attribute>
        </xsl:element>
    </xsl:template>
    
    <xsl:template name="inputcheck">
        <nomatch count="3">
            <exit/>
        </nomatch>
        <noinput count="3">
            <exit/>
        </noinput>
    </xsl:template>
    
    <xsl:template match="*">
        <xsl:comment>input not supported:
            <xsl:copy-of select="current()" inherit-namespaces="no" copy-namespaces="no"/>
        </xsl:comment>
    </xsl:template>
    
</xsl:stylesheet>
