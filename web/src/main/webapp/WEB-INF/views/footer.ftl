<#import "/spring.ftl" as spring/>

<#assign copyright_message><@spring.message "footer.copyright"/></#assign>
<#assign version_message><@spring.message "footer.version"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/footer.css'/>">
</head>

<body>
    <div id="footer">
        <div id="copyright">${copyright_message}</div>
        <#if version??>
            <#assign version_actual = version>
        <#else>
            <#assign version_actual = "UNDEFINED">
        </#if>
        <#if timestamp??>
            <#assign timestamp_actual = timestamp>
        <#else>
            <#assign timestamp_actual = "UNDEFINED">
        </#if>
        <div id="info">${version_message}: ${version_actual} | Timestamp: ${timestamp_actual}</div>
    </div>
</body>
</html>