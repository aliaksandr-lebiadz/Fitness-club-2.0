<#import "/spring.ftl" as spring/>

<#assign title><@spring.message "get_membership.title"/></#assign>
<#assign form_title><@spring.message "get_membership.form_title"/></#assign>
<#assign discount_message_first><@spring.message "get_membership.discount_message.first_part"/></#assign>
<#assign discount_message_second><@spring.message "get_membership.discount_message.second_part"/></#assign>
<#assign label><@spring.message "get_membership.membership_choice_label"/></#assign>
<#assign choice><@spring.message "get_membership.membership_choice"/></#assign>
<#assign place_order_button><@spring.message "get_membership.button.place_order"/></#assign>
<#assign card_number><@spring.message "get_membership.purchase_popup.card_number"/></#assign>
<#assign validity><@spring.message "get_membership.purchase_popup.validity"/></#assign>
<#assign cvv><@spring.message "get_membership.purchase_popup.cvv"/></#assign>
<#assign confirm_button><@spring.message "get_membership.purchase_popup.button.confirm"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/get_membership.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/select.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/disable_div.css'/>">
    <script src="<@spring.url '/static/jquery-2.0.2.min.js'/>"></script>
    <script src="<@spring.url '/scripts/popup.js'/>"></script>
</head>
<body>

<#include "header.ftl"/>

    <div id="intro"></div>
    <div id="disable-div"></div>

    <div id="form-container">
        <span id="order-title">${form_title}</span>
        <hr/>
        <#if discount gt 0>
            <span id="discount-message">${discount_message_first} ${discount} ${discount_message_second}</span>
            <hr/>
        </#if>
        <label for="membership-select-id">${label}</label>
        <div class="select-style">
            <select name="membership_select" id="membership-select-id">
                <#list gymMembershipList as item>
                    <#assign current_price>${item.price*(100-discount)/100}</#assign>
                    <option value="${item_index}">
                        ${item.monthsAmount} ${choice} (${current_price}BYN)
                    </option>
                </#list>
            </select>
        </div>
        <input type="button" id="place-order-button" value="${place_order_button}"
               onclick="$('.hidden-id').val($('#membership-select-id').val()); showPopUp('#purchase-popup');"/></div>
    <div class="popup" id="purchase-popup">
        <a class="close-icon" href="javascript:hidePopUp('#purchase-popup')">
            <i class="fa fa-times fa-lg"></i>
        </a>
        <form id="purchase-form" action="<@spring.url '/payment/getMembership'/>" method="post">
            <i class="fa fa-cc-visa fa-2x purchase-icon"></i>
            <i class="fa fa-cc-mastercard fa-2x purchase-icon"></i>
            <label for="card-number-input"></label>
            <input type="text" name="card_number" id="card-number-input" placeholder="${card_number}"
                   pattern="\d{16}" required autofocus/>
            <label for="valid-thru-input"></label>
            <input type="text" name="valid_thru" id="valid-thru-input" placeholder="${validity}"
                   pattern="((0[1-9])|(1[0-2]))/\d{2}" required/>
            <label for="cvv-input"></label>
            <input type="password" name="cvv" id="cvv-input" placeholder="${cvv}"
                   pattern="\d{3}" required/>
            <input type="hidden" name="membership_select" class="hidden-id"/>
            <input type="submit" id="purchase-button" value="${confirm_button}"/>
        </form>
    </div>

<#include "footer.ftl"/>

</body>
</html>