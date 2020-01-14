<#import "/spring.ftl" as spring/>

<#assign title><@spring.message "home.title"/></#assign>
<#assign message_title><@spring.message "home.message.title"/></#assign>
<#assign message_content><@spring.message "home.message.content"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/home.css'/>"/>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
</head>

<body>

<#include "header.ftl"/>

    <div id="intro"></div>
    <div id="main-text">
        <p id="title">${message_title}</p>
        <p id="text">${message_content}</p>
    </div>

<#include "footer.ftl"/>

</body>
</html>