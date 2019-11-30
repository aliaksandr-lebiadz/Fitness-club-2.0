<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale scope="page" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="get_membership.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="form_title" var="form_title"/>
    <fmt:message key="discount_message.first_part" var="discount_message_first"/>
    <fmt:message key="discount_message.second_part" var="discount_message_second"/>
    <fmt:message key="membership_choice_label" var="label"/>
    <fmt:message key="membership_choice" var="choice"/>
    <fmt:message key="button.place_order" var="place_order_button"/>
    <fmt:message key="purchase_popup.card_number" var="card_number"/>
    <fmt:message key="purchase_popup.validity" var="validity"/>
    <fmt:message key="purchase_popup.cvv" var="cvv"/>
    <fmt:message key="purchase_popup.button.confirm" var="confirm_button"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>${title}</title>
        <link rel="icon" href="${pageContext.request.contextPath}/icons/title_icon.png"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/get_membership.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/select.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/disable_div.css">
        <script src="${pageContext.request.contextPath}/static/jquery-2.0.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/popup.js"></script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>
        <div id="disable-div"></div>

        <div id="form-container">
            <c:set var="memberships" scope="page" value="${requestScope.memberships}"/>
            <c:set var="user" scope="page" value="${sessionScope.user}"/>
            <span id="order-title">${form_title}</span>
            <hr/>
            <c:if test="${user.discount gt 0}">
                <span id="discount-message">${discount_message_first} ${user.discount} ${discount_message_second}</span>
                <hr/>
            </c:if>
            <label for="membership-select-id">${label}</label>
            <div class="select-style">
                <select name="membership_select" id="membership-select-id">
                    <c:forEach items="${memberships}" var="item">
                        <c:set var="current_price" scope="page" value="${item.price*(100-user.discount)/100}"/>
                        <option value="${item.id}">
                                ${item.monthsAmount} ${choice} (${current_price}BYN)
                        </option>
                    </c:forEach>
                </select>
            </div>
            <input type="button" id="place-order-button" value="${place_order_button}"
                   onclick="$('.hidden-id').val($('#membership-select-id').val()); showPopUp('#purchase-popup');"/></div>
        <div class="popup" id="purchase-popup">
            <a class="close-icon" href="javascript:hidePopUp('#purchase-popup')">
                <i class="fa fa-times fa-lg"></i>
            </a>
            <form id="purchase-form" action="controller?command=getMembership" method="post">
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

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
</html>