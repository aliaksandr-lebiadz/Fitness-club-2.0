<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="page" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="home.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="message.title" var="message_title"/>
    <fmt:message key="message.content" var="message_content"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>${title}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/home.css"/>
        <link rel="icon" href="${pageContext.request.contextPath}/icons/title_icon.png"/>
    </head>

    <body>

        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>
        <div id="main-text">
            <p id="title">${message_title}</p>
            <p id="text">${message_content}</p>
        </div>

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
</html>