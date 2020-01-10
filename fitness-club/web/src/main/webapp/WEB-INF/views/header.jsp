<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale scope="page" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="header.">
    <fmt:message key="link.home" var="home_link"/>
    <fmt:message key="link.get_membership" var="get_membership_link"/>
    <fmt:message key="link.my_orders" var="my_orders_link"/>
    <fmt:message key="link.my_clients" var="my_clients_link"/>
    <fmt:message key="link.log_out" var="log_out_link"/>
    <fmt:message key="link.clients" var="clients_link"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/header.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>

    <body>
        <div id="header">
            <div id="logo">FitnessClub</div>

            <div id="navigation">
                <sec:authorize access="isAuthenticated()">
                    <a class="navigation_link simple" href="${pageContext.request.contextPath}/home">${home_link}</a>
                    <sec:authorize access="hasAuthority('ADMIN')">
                        <a class="navigation_link simple" href="${pageContext.request.contextPath}/client/list">${clients_link}</a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('CLIENT')">
                        <a class="navigation_link simple" href="${pageContext.request.contextPath}/order">${get_membership_link}</a>
                        <a class="navigation_link simple" href="${pageContext.request.contextPath}/order/list">${my_orders_link}</a>
                    </sec:authorize>
                    <sec:authorize access="hasAuthority('TRAINER')">
                        <a class="navigation_link simple" href="${pageContext.request.contextPath}/trainer/clients">${my_clients_link}</a>
                    </sec:authorize>
                    <a class="navigation_link simple" href="${pageContext.request.contextPath}/logOut">
                        <i class="fa fa-sign-out fa-lg" aria-hidden="true"></i>
                        <span>${log_out_link}</span>
                    </a>
                </sec:authorize>
                <div id="switcher">
                    <a href="javascript:void(0)" class="navigation_link switcher_link">
                        ${sessionScope.locale.language} <i class="fa fa-caret-down" aria-hidden="true"></i>
                    </a>
                    <div id="dropdown">
                        <a class="navigation_link" href="${pageContext.request.contextPath}/locale/change?language=en&country=US">EN</a>
                        <a class="navigation_link" href="${pageContext.request.contextPath}/locale/change?language=ru&country=RU">RU</a>
                        <a class="navigation_link" href="${pageContext.request.contextPath}/locale/change?language=be&country=BY">BE</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>