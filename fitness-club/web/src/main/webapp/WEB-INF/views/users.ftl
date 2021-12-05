<#assign display = JspTaglibs["http://displaytag.sf.net"]/>
<#import "/spring.ftl" as spring/>

<#assign title><@spring.message "users.title"/></#assign>
<#assign name><@spring.message "users.table.name"/></#assign>
<#assign email><@spring.message "users.table.email"/></#assign>
<#assign discount><@spring.message "users.table.discount"/></#assign>
<#assign pages_name><@spring.message "users.table.pages.name"/></#assign>
<#assign set_discount_button><@spring.message "users.button.set_discount"/></#assign>
<#assign discount_popup_title><@spring.message "users.discount_popup.title"/></#assign>
<#assign discount_popup_label><@spring.message "users.discount_popup.label"/></#assign>
<#assign discount_popup_button><@spring.message "users.discount_popup.button"/></#assign>
<#assign add_trainer_label><@spring.message "users.button.add_trainer"/></#assign>
<#assign add_trainer_popup_button><@spring.message "users.add_trainer_popup.button"/></#assign>
<#assign email_label><@spring.message "login.label.email"/></#assign>
<#assign password_label><@spring.message "login.label.password"/></#assign>
<#assign first_name_label><@spring.message "sign_up.label.first_name"/></#assign>
<#assign second_name_label><@spring.message "sign_up.label.second_name"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/display_table.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/disable_div.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/users.css'/>">
    <script src="<@spring.url '/static/jquery-2.0.2.min.js'/>"></script>
    <script src="<@spring.url '/scripts/popup.js'/>"></script>
    <script src="<@spring.url '/scripts/table_select.js'/>"></script>
</head>
<body>

<#include "header.ftl"/>

    <div id="intro"></div>
    <div id="disable-div"></div>


    <div id="container">
        <@display.table class="display-table" name=userList uid="row" pagesize=5 export=false sort="list" defaultsort=1>
            <@display.column property="id" class="hidden" headerClass="hidden"/>
            <@display.column title="${name}">
                <#if row.firstName??>${row.firstName}</#if> <#if row.secondName??>${row.secondName}</#if>
            </@display.column>
            <@display.column property="email" title="${email}"/>
            <@display.column property="discount" title="${discount}"/>
            <@display.setProperty name="paging.banner.items_name" value="${pages_name}"/>
        </@display.table>
        <hr>
        <button type="button" class="custom-button" id="discount-button" onclick="showPopUp('#discount-popup')">
            ${set_discount_button}
        </button>
        <button type="button" class="custom-button" id="add-trainer-button" onclick="showPopUp('#add-trainer-popup')">
            ${add_trainer_label}
        </button>
    </div>
    <div class="popup" id="add-trainer-popup">
        <div id="add-trainer-popup-header">
            <a class="close-icon" href="javascript:hidePopUp('#add-trainer-popup')">
                <i class="fa fa-times fa-lg"></i>
            </a>
            <span id="add-trainer-popup-title">${add_trainer_label}</span>
            <hr/>
        </div>
        <form id="add-trainer-form" action="<@spring.url '/trainer/add'/>" method="post">
            <label for="email">${email_label}</label>
            <input class="text-input" type="email" name="email" id="email" required autofocus minLength="7" maxLength="30"/>
            <label for="password">${password_label}</label>
            <input class="text-input" type="password" name="password" id="password" required minLength="5" maxLength="20"/>
            <label for="first-name">${first_name_label}</label>
            <input class="text-input" name="first_name" id="first-name" required minLength="3" maxLength="30"/>
            <label for="second-name">${second_name_label}</label>
            <input class="text-input" name="second_name" id="second-name" required minLength="3" maxLength="30"/>
            <input type="submit" class="custom-button" id="create-trainer" value="${add_trainer_popup_button}"/>
        </form>
    </div>
    <div class="popup" id="discount-popup">
        <div id="discount-popup-header">
            <a class="close-icon" href="javascript:hidePopUp('#discount-popup')">
                <i class="fa fa-times fa-lg"></i>
            </a>
            <span id="discount-popup-title">${discount_popup_title}</span>
            <hr/>
        </div>
        <form id="discount-form" action="<@spring.url '/user/set-discount'/>" method="post">
            <label for="discount">${discount_popup_label}</label>
            <input type="number" name="discount" id="discount" required/>
            <input type="hidden" name="user_id" class="hidden-id"/>
            <input type="submit" class="custom-button" id="set-discount" value="${discount_popup_button}"/>
        </form>
    </div>

<#include "footer.ftl"/>

</body>
</html>