<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fc" uri="fitness-club" %>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="clients.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="table.name" var="name"/>
    <fmt:message key="table.email" var="email"/>
    <fmt:message key="table.discount" var="discount"/>
    <fmt:message key="table.pages.name" var="pages_name"/>
    <fmt:message key="button.set_discount" var="set_discount_button"/>
    <fmt:message key="discount_popup.title" var="discount_popup_title"/>
    <fmt:message key="discount_popup.label" var="discount_popup_label"/>
    <fmt:message key="discount_popup.button" var="discount_popup_button"/>
</fmt:bundle>
<fmt:bundle basename="pages_content" prefix="user_role.">
    <fmt:message key="admin" var="admin_role"/>
    <fmt:message key="client" var="client_role"/>
    <fmt:message key="trainer" var="trainer_role"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>${title}</title>
        <link rel="icon" href="${pageContext.request.contextPath}/icons/title_icon.png"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/display_table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/disable_div.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/clients.css">
        <script src="${pageContext.request.contextPath}/static/jquery-2.0.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/popup.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/table_select.js"></script>
    </head>
    <body>

        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>
        <div id="disable-div"></div>

        <c:if test="${fn:length(requestScope.clients) ne 0}">
            <div id="container">
                <display:table class="display-table" name="requestScope.clients" uid="row" pagesize="5" export="false" requestURI="">
                    <display:column property="id" class="hidden" headerClass="hidden"/>
                    <display:column title="${name}">
                        ${row.firstName} ${row.secondName}
                    </display:column>
                    <display:column property="email" title="${email}"/>
                    <display:column property="discount" title="${discount}"/>
                    <display:setProperty name="paging.banner.items_name" value="${pages_name}"/>
                </display:table>
                <hr>
                <button type="button" class="custom-button" id="discount-button"
                        onclick="showPopUp('#discount-popup')">
                        ${set_discount_button}
                </button>
            </div>
        </c:if>
        <div class="popup" id="discount-popup">
            <div id="discount-popup-header">
                <a class="close-icon" href="javascript:hidePopUp('#discount-popup')">
                    <i class="fa fa-times fa-lg"></i>
                </a>
                <span id="discount-popup-title">${discount_popup_title}</span>
                <hr/>
            </div>
            <form id="discount-form" action="controller?command=setUserDiscount" method="post">
                <label for="discount">${discount_popup_label}</label>
                <input type="number" name="discount" id="discount" min="0" max="100" required/>
                <input type="hidden" name="user_id" class="hidden-id"/>
                <input type="submit" class="custom-button" id="set-discount" value="${discount_popup_button}"/>
            </form>
        </div>

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
</html>