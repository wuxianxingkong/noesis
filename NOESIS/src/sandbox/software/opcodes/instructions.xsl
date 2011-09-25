<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:key name="readings-by-year"  match="library/book/comment"  use="@date" />
<xsl:key name="books-by-category" match="library/book/category" use="." />
<xsl:key name="books-by-year"     match="library/book/edition"  use="year" />


<xsl:template match="/">

<html>
<head>
 <TITLE><xsl:value-of select="/architecture/@name"/></TITLE>
 <META NAME="Author" CONTENT="Fernando Berzal Galiano"/>
</head>

<STYLE TYPE="text/css" TITLE="">
BODY  
{
 background-image: url(../image/csharp.gif);
 color: #000000;
 margin-left: 10%;
 margin-right: 10%;
 margin-top: 5%;
 margin-bottom: 5%;
}
	
P, BLOCKQUOTE, LI
{
 text-align: justify;
}

TABLE, TR, TH, TD, BLOCKQUOTE
{
  padding: 5px;
}

PRE
{
  font-size: 9pt;
}


.title
{
  background-color: #BCBCDE;
}

.author
{
  background-color: #cCcCeE;
}

.edition
{
  background-color: #cCcCeE;
}

.data
{
  background-color: #D9D9D9;
}

.idea
{
  background-color: #FFFFCC;
}



.comment
{
  background-color: #e9e9e9;
}

.classification
{
  background-color: #c9c9c9;
}


.copyright
{
 text-align: right;
}

A
{
  text-decoration: none; 
}

A:hover
{ 
  color: #009999; 
}

UL,OL
{
  list-style: outside;
  margin: 1em;
}


CODE
{
 display: inline;
 white-space: pre;
 text-align: left;
}

H1
{
 font-size: 200%;
 font-weight: bold;	
 color: #000000;
 text-align: center;
}

.title
{
 color: #006699;
}

H2
{
 color: #003366;
 font-size: 150%;
 font-weight: bold;	
 text-align: left;
}

H3
{
 font-size: 125%;
 font-weight: bold;	
 text-align: left;
}

H4
{
 font-size: 100%;
 font-weight: bold;	
 text-align: left;
}
</STYLE>


<BODY>

<H1 ALIGN="center"><FONT COLOR="#006699"><xsl:value-of select="/architecture/@name"/></FONT></H1>

<HR />
<center>
<A HREF="#opcodes">Opcodes</A>
|
<A HREF="#sorted">Alphabetical order</A>
|
<A HREF="#classified">By category</A>
|
<A HREF="#details">Details</A>
</center>
<HR />

<blockquote>

<A NAME="opcodes"/>

<h1>Opcodes</h1>


<center>
<table>
<tr class="title">
 <th width="25" align="left">hex</th>
 <th width="50" align="left">Opcode</th>
 <th width="50" align="left">Category</th>
 <th width="50" align="left">Op</th>
 <th            align="left">Description</th>
</tr>
 <xsl:for-each select="architecture/instruction">
 <!--<xsl:sort select="title" data-type="text" order="ascending"/>-->
 <tr class="data">
  <td><code><xsl:value-of select="hex"/></code></td>
  <td><A>
      <xsl:attribute name="href">#<xsl:value-of select="hex"/></xsl:attribute>
      <xsl:value-of select="opcode"/>
      </A>
  </td>
  <td><xsl:value-of select="category"/></td>
  <td><xsl:value-of select="operation"/></td>
  <td><xsl:value-of select="description"/></td>
 </tr>
 </xsl:for-each>
</table>
</center>



<A NAME="sorted"/>

<h1>In alphabetical order</h1>

<center>
<table>
<tr class="title">
 <th width="25" align="left">hex</th>
 <th width="50" align="left">Opcode</th>
 <th width="50" align="left">Category</th>
 <th width="50" align="left">Op</th>
 <th            align="left">Description</th>
</tr>
 <xsl:for-each select="architecture/instruction">
 <xsl:sort select="opcode" data-type="text" order="ascending"/>
 <tr class="data">
  <td><code><xsl:value-of select="hex"/></code></td>
  <td><A>
      <xsl:attribute name="href">#<xsl:value-of select="hex"/></xsl:attribute>
      <xsl:value-of select="opcode"/>
      </A>
  </td>
  <td><xsl:value-of select="category"/></td>
  <td><xsl:value-of select="operation"/></td>
  <td><xsl:value-of select="description"/></td>
 </tr>
 </xsl:for-each>
</table>
</center>


<A NAME="classified"/>

<h1>By category</h1>

<center>
<table>
<tr class="title">
 <th width="25" align="left">hex</th>
 <th width="50" align="left">Opcode</th>
 <th width="50" align="left">Category</th>
 <th width="50" align="left">Op</th>
 <th            align="left">Description</th>
</tr>
 <xsl:for-each select="architecture/instruction">
 <xsl:sort select="category" data-type="text" order="ascending"/>
 <xsl:sort select="operation" data-type="text" order="ascending"/>
 <tr class="data">
  <td><code><xsl:value-of select="hex"/></code></td>
  <td><A>
      <xsl:attribute name="href">#<xsl:value-of select="hex"/></xsl:attribute>
      <xsl:value-of select="opcode"/>
      </A>
  </td>
  <td><xsl:value-of select="category"/></td>
  <td><xsl:value-of select="operation"/></td>
  <td><xsl:value-of select="description"/></td>
 </tr>
 </xsl:for-each>
</table>
</center>

<HR />

<!-- Content -->
<A NAME="details"/>

<H1>Instruction details</H1>

<xsl:apply-templates/> 

<!-- Footer -->

</blockquote>


<HR />
 <P CLASS="copyright">© <A HREF="mailto:berzal@acm.org">Fernando Berzal</A></P>

</BODY>


</html>

</xsl:template>


<!-- Subdocumentos -->


<xsl:template match="architecture/instruction">
  <center>
 <A>
 <xsl:attribute name="name"><xsl:value-of select="hex"/></xsl:attribute>
 </A>
  <table width="80%">
   <tr class="title">
    <th colspan="3" align="left" valign="middle">
      <font size="+2" color="#003366">
       <xsl:value-of select="opcode"/>
      </font>
    </th>
   </tr>
   <tr class="data">
    <td width="20%" align="left" valign="top">
     <xsl:value-of select="category"/>
    </td>
    <td width="40%" align="left" valign="middle" rowspan="3">
      <xsl:apply-templates select="before"/> 
    </td>
    <td width="40%" align="left" valign="middle" rowspan="3">
      <xsl:apply-templates select="after"/> 
    </td>
   </tr>
   <tr class="data">
    <td width="20%" align="left" valign="top">
     <xsl:value-of select="operation"/>
    </td>
   </tr>  
   <tr class="data">
    <td width="20%" align="left" valign="top">
     <xsl:value-of select="condition"/>
    </td>
   </tr>
  </table>
  </center>
</xsl:template>


<xsl:template match="stack">

  <li>
   Stack: <xsl:value-of select="."/>  
   <xsl:if test="@type">
    (<i><xsl:value-of select="@type"/></i>)
   </xsl:if>
  </li>

</xsl:template>

<xsl:template match="operand">

  <li>
   Operand: <xsl:value-of select="."/>  
   <xsl:if test="@type">
    (<xsl:value-of select="@type"/>)
   </xsl:if>
  </li>

</xsl:template>


<!-- URLs -->

<xsl:template match="url">

  <A target="_blank">
    <xsl:attribute name="href"><xsl:value-of select="."/></xsl:attribute>
    <xsl:value-of select="."/> 
  </A>

</xsl:template>

<xsl:template match="ref">

  <A>
    <xsl:attribute name="href">#<xsl:value-of select="@hex"/></xsl:attribute>
    <xsl:value-of select="."/> 
  </A>

</xsl:template>



<!-- END -->

</xsl:stylesheet>