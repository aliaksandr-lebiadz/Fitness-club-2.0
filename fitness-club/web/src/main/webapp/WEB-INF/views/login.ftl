<#import "/spring.ftl" as spring/>

<#assign email_label><@spring.message "login.label.email"/></#assign>
<#assign password_label><@spring.message "login.label.password"/></#assign>
<#assign login_button><@spring.message "login.button"/></#assign>
<#assign login_fail_message><@spring.message "login.message.fail"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Fitness club</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/login.css'/>"/>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
</head>

<body>

<#include "header.ftl"/>

    <div id="intro"></div>
    <div id="login-container">
        <form id="login-form" action="doLogin" method="post">
            <label for="email">${email_label}</label>
            <input class="text-input" type="email" name="email" id="email" required autofocus/>
            <label for="password">${password_label}</label>
            <input class="text-input" type="password" name="password" id="password" required/>
            <hr>
            <input type="submit" id="login-button" value="${login_button}"/>
            <#if login_fail??>
                <span id="login-fail">${login_fail_message}</span>
            </#if>
        </form>
    </div>

<#include "footer.ftl"/>

</body>
</html>