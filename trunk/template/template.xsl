<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:template match="/suite">
        <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <title>accept4j Test Output : <xsl:value-of select="@name"/></title>
            <link rel="icon"
                  type="image/x-icon"
                  href="resources/favicon.ico"/>
            <link rel="stylesheet" href="resources/test.css"/>
        </head>
        <body>
            <div id="bodyDiv">
                <div class="banner">
                    <img src="resources/logo.png" alt="accept4j" title="accept4j" id="logo"/>
                </div>
                <div class="buildDetails">
                    Acceptance test report for
                    <xsl:value-of select="@name"/> at
                    <xsl:value-of select="@datetime"/>
                </div>
                <div class="testDetails">
                    <xsl:apply-templates/>
                </div>
            </div>
        </body>
        </html>
    </xsl:template>

    <xsl:template match="group">
        <div class="groupDiv">
            <div class="groupName"><xsl:value-of select="@name"/></div>
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="pack">
        <div class="packDiv">
            <div class="packName"><xsl:value-of select="@name"/></div>
            <table class="testDetailsTable">
                <xsl:apply-templates/>
            </table>
        </div>
    </xsl:template>
    
    <xsl:template match="test">
        <tr>
            <td class="iconCol">
                <xsl:choose>
                    <xsl:when test="@name = ''">
                        <img src="resources/alert.png" title="This test has no matching specification" class="icon"/>
                    </xsl:when>
                    <xsl:when test="@methodName != ''">
                        <img src="resources/green.png" title="A test exists for this specification" class="icon"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <img src="resources/red.png" title="No test exists for this specification" class="icon"/>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
            <td class="idCol">
                <xsl:value-of select="@id"/>
            </td>
            <td class="nameCol">
                <xsl:value-of select="@name"/>
            </td>
            <td class="methodNameCol">
                <xsl:value-of select="@methodName"/>
            </td>
            <td class="descCol">
                <pre>
                    <xsl:value-of select="description"/>
                </pre>
            </td>
        </tr>
    </xsl:template>

</xsl:stylesheet>