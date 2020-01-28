<#assign display = JspTaglibs["http://displaytag.sf.net"]/>
<#import "/spring.ftl" as spring/>

<#assign title><@spring.message "clients.title"/></#assign>
<#assign name><@spring.message "clients.table.name"/></#assign>
<#assign email><@spring.message "clients.table.email"/></#assign>
<#assign discount><@spring.message "clients.table.discount"/></#assign>
<#assign pages_name><@spring.message "clients.table.pages.name"/></#assign>
<#assign set_discount_button><@spring.message "clients.button.set_discount"/></#assign>
<#assign discount_popup_title><@spring.message "clients.discount_popup.title"/></#assign>
<#assign discount_popup_label><@spring.message "clients.discount_popup.label"/></#assign>
<#assign discount_popup_button><@spring.message "clients.discount_popup.button"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/display_table.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/disable_div.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/clients.css'/>">
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
                ${row.firstName} ${row.secondName}
            </@display.column>
            <@display.column property="email" title="${email}"/>
            <@display.column property="discount" title="${discount}"/>
            <@display.setProperty name="paging.banner.items_name" value="${pages_name}"/>
        </@display.table>
        <hr>
        <button type="button" class="custom-button" id="discount-button"
                onclick="showPopUp('#discount-popup')">
            ${set_discount_button}
        </button>
    </div>
    <div class="popup" id="discount-popup">
        <div id="discount-popup-header">
            <a class="close-icon" href="javascript:hidePopUp('#discount-popup')">
                <i class="fa fa-times fa-lg"></i>
            </a>
            <span id="discount-popup-title">${discount_popup_title}</span>
            <hr/>
        </div>
        <form id="discount-form" action="<@spring.url '/client/setDiscount'/>" method="post">
            <label for="discount">${discount_popup_label}</label>
            <input type="number" name="discount" id="discount" min="0" max="100" required/>
            <input type="hidden" name="user_id" class="hidden-id"/>
            <input type="submit" class="custom-button" id="set-discount" value="${discount_popup_button}"/>
        </form>
    </div>

<#include "footer.ftl"/>

</body>
</html>