<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transformation.xsl
    Author     : Filip Kejnar
    Description:
        Transformation of State Chart XML file into Voice XML
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns="http://www.w3.org/2001/vxml"
                xmlns:sc="http://www.w3.org/2005/07/scxml" 
                xmlns:vxml="http://www.w3.org/2001/vxml" 
                exclude-result-prefixes="vxml sc">
    
    <xsl:output method="xml"
                version="1.0"
                encoding="UTF-8"
                indent="yes"
                standalone="no" />
    <xsl:strip-space elements="/"/>
                
    <xsl:template match="/sc:scxml">
        <vxml xmlns="http://www.w3.org/2001/vxml" version="2.0">
            <form id="main">
                <xsl:apply-templates select="./sc:datamodel" />
                <xsl:variable name="initial_id" select="@initial" />
                <xsl:apply-templates select="sc:state[@id=$initial_id]" mode="initial" />
                <xsl:apply-templates select="sc:state[@id!=$initial_id]" />
                <xsl:apply-templates select="sc:final" />
            </form>
        </vxml>
    </xsl:template>
    
    <xsl:template match="sc:state | sc:parallel" mode="initial">
        <xsl:element name="initial">
            <xsl:attribute name="name">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:apply-templates select="./sc:onentry" />
            <xsl:apply-templates select="./sc:onexit" />
            <xsl:apply-templates select="./sc:transition" />
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="sc:state | sc:parallel" >
        <xsl:element name="field">
            <xsl:attribute name="name">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:apply-templates />
        </xsl:element>
    </xsl:template>
    
    <xsl:template match="sc:final" >
        
        <xsl:element name="field">
            <xsl:attribute name="name">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:apply-templates />
        </xsl:element>
        
    </xsl:template>
        
    <xsl:template match="sc:transition">
        <xsl:value-of select="@event" />~<xsl:value-of select="@target" />
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="sc:onentry">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="sc:onexit">
        <filled>
            <xsl:apply-templates/>
        </filled>
    </xsl:template>
    
    <xsl:template match="vxml:prompt">
        <xsl:copy-of select="current()"/>
    </xsl:template>
    
    <xsl:template match="vxml:assign">
        <xsl:copy-of select="current()"/>
    </xsl:template>

    <xsl:template match="vxml:*">
        <xsl:copy-of select="current()"/>
    </xsl:template>
    
    <xsl:template match="sc:datamodel">
        <xsl:element name="grammar">
            <xsl:attribute name="src">
                <xsl:value-of select="sc:data/@expr" />
            </xsl:attribute>
        </xsl:element>
    </xsl:template>
    
</xsl:stylesheet>
