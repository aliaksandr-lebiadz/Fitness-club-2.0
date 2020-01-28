<#import "/spring.ftl" as spring/>
<#assign display = JspTaglibs["http://displaytag.sf.net"]/>

<#assign title><@spring.message "orders.title"/></#assign>
<#assign begin_date><@spring.message "orders.table.begin_date"/></#assign>
<#assign end_date><@spring.message "orders.table.end_date"/></#assign>
<#assign price><@spring.message "orders.table.price"/></#assign>
<#assign leave_feedback_button><@spring.message "orders.button.leave_feedback"/></#assign>
<#assign see_assignments_button><@spring.message "orders.button.see_assignments"/></#assign>
<#assign zero_orders><@spring.message "orders.zero_orders_message"/></#assign>
<#assign buy_membership_button><@spring.message "orders.button.buy_membership"/></#assign>
<#assign feedback_title><@spring.message "orders.feedback.title"/></#assign>
<#assign feedback_placeholder><@spring.message "orders.feedback.placeholder"/></#assign>
<#assign feedback_restriction><@spring.message "orders.feedback.restriction"/></#assign>
<#assign send_feedback_button><@spring.message "orders.feedback.button.send"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <title>${title}</title>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/orders.css'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/display_table.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/disable_div.css'/>">
    <script src="<@spring.url '/static/jquery-2.0.2.min.js'/>"></script>
    <script src="<@spring.url '/scripts/popup.js'/>"></script>
    <script src="<@spring.url '/scripts/table_select.js'/>"></script>
    <script src="<@spring.url '/scripts/submission_check.js'/>"></script>
</head>
<body>
<#include "header.ftl"/>

    <div id="intro"></div>
    <div id="disable-div"></div>
    <#if orderList?size != 0>
        <div id="container">
            <form class="check-submit-form" id="orders-form" action="<@spring.url '/assignment/list'/>">
                <@display.table class="display-table" name=orderList uid="row" pagesize=5 export=false sort="list" defaultsort=1>
                    <@display.column property="id" class="hidden" headerClass="hidden"/>
                    <@display.column title="${begin_date}" value="${(row.beginDate?date)!'UNDEFINED'}"/>
                    <@display.column title="${end_date}" value="${(row.endDate?date)!'UNDEFINED'}"/>
                    <@display.column title="${price}">
                        ${(row.price)!'UNDEFINED'} BYN
                    </@display.column>
                </@display.table>
                <hr>
                <input type="hidden" name="order_id" class="hidden-id" required/>
                <button type="button" class="custom-button" id="feedback-button"
                        onclick="showPopUp('#feedback-popup')">
                    ${leave_feedback_button}
                </button>
                <button type="submit" class="custom-button" id="assignment-button">
                    ${see_assignments_button}
                </button>
            </form>
        </div>
    <#else>
        <div id="no-orders-container">
            <p>${zero_orders}</p>
            <form id="no-orders-form" action="<@spring.url '/order'/>">
                <input type="submit" class="custom-button" id="buy-membership-button" value="${buy_membership_button}"/>
            </form>
        </div>
    </#if>
    <div class="popup" id="feedback-popup">
        <div id="feedback-popup-header">
            <a class="close-icon" href="javascript:hidePopUp('#feedback-popup')">
                <i class="fa fa-times fa-lg"></i>
            </a>
            <span id="feedback-title">${feedback_title}</span>
            <hr/>
        </div>
        <form id="feedback-form" action="<@spring.url '/order/feedback'/>" method="post">
            <label for="feedback-textarea"></label>
            <textarea name="feedback" id="feedback-textarea" rows="7" cols="34" minlength="10" maxlength="1000"
                      placeholder="${feedback_placeholder}" required></textarea>
            <span id="feedback-restriction">${feedback_restriction}</span>
            <input type="hidden" name="order_id" class="hidden-id"/>
            <input type="submit" class="custom-button" id="send-feedback" value="${send_feedback_button}"/>
        </form>
    </div>

<#include "footer.ftl"/>
</body>
</html>