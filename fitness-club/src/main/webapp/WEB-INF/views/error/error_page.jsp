<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="page" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="error_page.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="error" var="error"/>
    <fmt:message key="button.home" var="home_button"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>${title}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/error_page.css">
        <link rel="icon" href="${pageContext.request.contextPath}/icons/title_icon.png"/>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>
        <div id="error">
            <form id="error-form" action="controller?command=showHomePage" method="post">
                <span>${error}</span>
                <input type="submit" id="home-button" value="${home_button}"/>
            </form>
        </div>

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
</html>