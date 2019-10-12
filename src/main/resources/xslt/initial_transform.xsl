<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/Document">
        <Document>
            <DocumentCode>
                <xsl:value-of select="@code"/>
            </DocumentCode>
            <Points>
                <xsl:value-of select="Points"/>
            </Points>
            <Rebounds>
                <xsl:value-of select="Rebounds"/>
            </Rebounds>
            <Assists>
                <xsl:value-of select="Assists"/>
            </Assists>
            <Steals>
                <xsl:value-of select="Steals"/>
            </Steals>
            <Blocks>
                <xsl:value-of select="Blocks"/>
            </Blocks>
        </Document>


    </xsl:template>

</xsl:stylesheet>