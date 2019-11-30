<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="page" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="footer.">
    <fmt:message key="copyright" var="copyright"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/footer.css">
    </head>

    <body>
        <div id="footer">
            <span id="copyright">${copyright}</span>
        </div>
    </body>
</html>