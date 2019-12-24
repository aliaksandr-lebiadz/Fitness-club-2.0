<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <c:set var="user" scope="page" value="${sessionScope.user}"/>
                <c:if test="${not empty user}">
                    <a class="navigation_link simple" href="controller?command=showHomePage">${home_link}</a>
                    <c:choose>
                        <c:when test="${user.role eq 'ADMIN'}">
                            <a class="navigation_link simple" href="controller?command=showClients">${clients_link}</a>
                        </c:when>
                        <c:when test="${user.role eq 'TRAINER'}">
                            <a class="navigation_link simple" href="controller?command=showTrainerClients">${my_clients_link}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="navigation_link simple" href="controller?command=showOrderPage">${get_membership_link}</a>
                            <a class="navigation_link simple" href="controller?command=showOrders">${my_orders_link}</a>
                        </c:otherwise>
                    </c:choose>
                    <a class="navigation_link simple" href="controller?command=logOut">
                        <i class="fa fa-sign-out fa-lg" aria-hidden="true"></i>
                        <span>${log_out_link}</span>
                    </a>
                </c:if>
                <div id="switcher">
                    <a href="javascript:void(0)" class="navigation_link switcher_link">
                        ${sessionScope.locale.language} <i class="fa fa-caret-down" aria-hidden="true"></i>
                    </a>
                    <div id="dropdown">
                        <a class="navigation_link" href="controller?command=setLocale&locale=en&country=US">EN</a>
                        <a class="navigation_link" href="controller?command=setLocale&locale=ru&country=RU">RU</a>
                        <a class="navigation_link" href="controller?command=setLocale&locale=be&country=BY">BE</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>