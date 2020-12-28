<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/">
        <html>
            <body>
                <h2>spring </h2>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="clazz">
        <table border="1" width="250" >
            <tr bgcolor="#9acd32">
                <th>name</th>
                <th>age</th>
            </tr>
            <xsl:for-each select="data">
                <tr>
                    <td><xsl:value-of select="name"/></td>
                    <td><xsl:value-of select="age"/></td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>


</xsl:stylesheet>