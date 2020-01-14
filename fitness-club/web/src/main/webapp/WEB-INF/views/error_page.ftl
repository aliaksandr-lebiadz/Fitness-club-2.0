<#import "/spring.ftl" as spring/>

<#assign title><@spring.message "error_page.title"/></#assign>
<#assign error><@spring.message "error_page.error"/></#assign>
<#assign home_button><@spring.message "error_page.button.home"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/error_page.css'/>">
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
</head>
<body>

<#include "header.ftl"/>

    <div id="intro"></div>
    <div id="error">
        <form id="error-form" action="<@spring.url '/home'/>">
            <span>${error}</span>
            <input type="submit" id="home-button" value="${home_button}"/>
        </form>
    </div>

<#include "footer.ftl"/>

</body>
</html>