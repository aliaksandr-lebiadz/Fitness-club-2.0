<#import "/spring.ftl" as spring/>

<#assign email_label><@spring.message "login.label.email"/></#assign>
<#assign password_label><@spring.message "login.label.password"/></#assign>
<#assign first_name_label><@spring.message "sign_up.label.first_name"/></#assign>
<#assign second_name_label><@spring.message "sign_up.label.second_name"/></#assign>
<#assign sign_up_button><@spring.message "sign_up.button"/></#assign>
<#assign sign_up_fail_message><@spring.message "sign_up.message.fail"/></#assign>
<#assign login_switcher><@spring.message "sign_up.message.login_switcher"/></#assign>
<#assign login_label><@spring.message "login.button"/></#assign>

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
    <div id="sign-up-container">
        <form id="login-form" action="user/sign-up" method="post">
            <label for="email">${email_label}</label>
            <input class="text-input" type="email" name="email" id="email" required autofocus minLength="7" maxLength="30"/>
            <label for="password">${password_label}</label>
            <input class="text-input" type="password" name="password" id="password" required minLength="5" maxLength="20"/>
            <label for="first-name">${first_name_label}</label>
            <input class="text-input" name="first_name" id="first-name" required minLength="3" maxLength="30"/>
            <label for="second-name">${second_name_label}</label>
            <input class="text-input" name="second_name" id="second-name" required minLength="3" maxLength="30"/>
            <hr>
            <input type="submit" id="login-button" value="${sign_up_button}"/>
            <div class="hint">
                ${login_switcher} <a class="link" href="<@spring.url '/login'/>">${login_label}</a></div>
            <#if sign_up_fail??>
                <span id="login-fail">${sign_up_fail_message}</span>
            </#if>
        </form>
    </div>

<#include "footer.ftl"/>

</body>
</html>