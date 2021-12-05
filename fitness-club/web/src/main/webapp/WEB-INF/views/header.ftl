<#import "/spring.ftl" as spring/>
<#assign security = JspTaglibs["http://www.springframework.org/security/tags"]/>

<#assign home_link><@spring.message "header.link.home"/></#assign>
<#assign get_membership_link><@spring.message "header.link.get_membership"/></#assign>
<#assign my_orders_link><@spring.message "header.link.my_orders"/></#assign>
<#assign my_clients_link><@spring.message "header.link.my_clients"/></#assign>
<#assign log_out_link><@spring.message "header.link.log_out"/></#assign>
<#assign users_link><@spring.message "header.link.users"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/header.css'/>">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

    <div id="header">
        <div id="logo">FitnessClub</div>

        <div id="navigation">
            <@security.authorize access="isAuthenticated()">
                <a class="navigation_link simple" href="<@spring.url '/home'/>">${home_link}</a>
                <@security.authorize access="hasAuthority('ADMIN')">
                    <a class="navigation_link simple" href="<@spring.url '/user/list'/>">${users_link}</a>
                </@security.authorize>
                <@security.authorize access="hasAuthority('CLIENT')">
                    <a class="navigation_link simple" href="<@spring.url '/order'/>">${get_membership_link}</a>
                    <a class="navigation_link simple" href="<@spring.url '/order/list'/>">${my_orders_link}</a>
                </@security.authorize>
                <@security.authorize access="hasAuthority('TRAINER')">
                    <a class="navigation_link simple" href="<@spring.url '/trainer/clients'/>">${my_clients_link}</a>
                </@security.authorize>
                <a class="navigation_link simple" href="<@spring.url '/logOut'/>">
                    <i class="fa fa-sign-out fa-lg" aria-hidden="true"></i>
                    <span>${log_out_link}</span>
                </a>
            </@security.authorize>
            <div id="switcher">
                <a href="javascript:void(0)" class="navigation_link switcher_link">
                    ${.lang} <i class="fa fa-caret-down" aria-hidden="true"></i>
                </a>
                <#if RequestParameters.order_id??>
                    <#assign additional_order_id = "&order_id=" + RequestParameters.order_id/>
                <#else>
                    <#assign additional_order_id = ""/>
                </#if>
                <#if RequestParameters.client_id??>
                    <#assign additional_client_id = "&client_id=" + RequestParameters.client_id/>
                <#else>
                    <#assign additional_client_id = ""/>
                </#if>
                <div id="dropdown">
                    <a class="navigation_link" href="${"?lang=en_US" + additional_order_id + additional_client_id}">EN</a>
                    <a class="navigation_link" href="${"?lang=ru_RU" + additional_order_id + additional_client_id}">RU</a>
                    <a class="navigation_link" href="${"?lang=be_BY" + additional_order_id + additional_client_id}">BE</a>
                </div>
            </div>
        </div>
    </div>

</body>
</html>