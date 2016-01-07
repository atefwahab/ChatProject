<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
<xsl:template match="Chat">
<html>
	<body>
	<table border="1">
<tr bgcolor="#9acd32">
<th style="text-align:left">From</th>
<th style="text-align:left">to</th>
<th style="text-align:left">body</th>
</tr>
<xsl:for-each select="Message">
<tr>
<td>
<xsl:value-of select="@from"/>

</td>
<td>
<xsl:value-of select="@to"/>
</td>
<td>
<xsl:value-of select="body"/>
</td>
</tr>
</xsl:for-each>
</table>
</body>
</html>
</xsl:template>

</xsl:stylesheet>
