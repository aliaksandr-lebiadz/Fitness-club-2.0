<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale scope="page" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="login.">
    <fmt:message key="label.email" var="email_label"/>
    <fmt:message key="label.password" var="password_label"/>
    <fmt:message key="button" var="button"/>
    <fmt:message key="message.fail" var="fail_message"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Fitness club</title>
        <link rel="stylesheet" href="styles/main/login.css"/>
        <link rel="icon" href="icons/title_icon.png"/>
    </head>

    <body>
        <c:if test="${not empty sessionScope.user}">
            <jsp:forward page="WEB-INF/views/home.jsp"/>
        </c:if>

        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>
        <div id="login-container">
            <form id="login-form" action="controller?command=login" method="post">
                <label for="email">${email_label}</label>
                <input class="text-input" type="email" name="email" id="email" required autofocus/>
                <label for="password">${password_label}</label>
                <input class="text-input" type="password" name="password" id="password" required/>
                <hr>
                <input type="submit" id="login-button" value="${button}"/>
                <c:if test="${sessionScope.login_fail eq true}">
                    <span id="login-fail">${fail_message}</span>
                    <c:remove var="login_fail" scope="session"/>
                </c:if>
            </form>
        </div>

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
</html>