<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:template match="Chat">
<html>
	<head>
		<link href="test.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
	

		<div id="m1">
		<xsl:apply-templates select="Message[@from='nihal']/body"/>
		</div>
		<div id="m2">
		<xsl:apply-templates select="Message[@from='eman']/body"/>
		</div>
		</body>
	</html>
	</xsl:template>
	
<xsl:template match="Message[@from='nihal']/body">
		
		
		
		<h1 >
			<xsl:value-of select="text()"/>
		</h1>
		<br/>
	</xsl:template>
	
	<xsl:template match="Message[@from='eman']/body">
		<h1>
			<xsl:value-of select="text()"/>
		</h1>
		<br/>
	</xsl:template>




</xsl:stylesheet>
