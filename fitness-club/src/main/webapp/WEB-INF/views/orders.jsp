<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="orders.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="table.begin_date" var="begin_date"/>
    <fmt:message key="table.end_date" var="end_date"/>
    <fmt:message key="table.price" var="price"/>
    <fmt:message key="button.leave_feedback" var="leave_feedback_button"/>
    <fmt:message key="button.see_assignments" var="see_assignments_button"/>
    <fmt:message key="zero_orders_message" var="zero_orders"/>
    <fmt:message key="button.buy_membership" var="buy_membership_button"/>
    <fmt:message key="feedback.title" var="feedback_title"/>
    <fmt:message key="feedback.placeholder" var="feedback_placeholder"/>
    <fmt:message key="feedback.restriction" var="feedback_restriction"/>
    <fmt:message key="feedback.button.send" var="send_feedback_button"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

        <title>${title}</title>
        <link rel="icon" href="${pageContext.request.contextPath}/icons/title_icon.png"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/orders.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/display_table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/disable_div.css">
        <script src="${pageContext.request.contextPath}/static/jquery-2.0.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/popup.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/table_select.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/submission_check.js"></script>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>
        <div id="disable-div"></div>
        <c:if test="${fn:length(requestScope.orders) ne 0}">
            <div id="container">
                <form class="check-submit-form" id="orders-form" action="controller" method="get">
                    <display:table class="display-table" name="requestScope.orders" uid="row" pagesize="5" export="false" requestURI="">
                        <display:column property="id" class="hidden" headerClass="hidden"/>
                        <display:column title="${begin_date}">
                            <fmt:formatDate value="${row.beginDate}"/>
                        </display:column>
                        <display:column title="${end_date}">
                            <fmt:formatDate value="${row.endDate}"/>
                        </display:column>
                        <display:column title="${price}">
                            ${row.price} BYN
                        </display:column>
                    </display:table>
                    <hr>
                    <input type="hidden" name="order_id" class="hidden-id" required/>
                    <input type="hidden" name="command" value="showAssignments"/>
                    <button type="button" class="custom-button" id="feedback-button"
                            onclick="showPopUp('#feedback-popup')">
                        ${leave_feedback_button}
                    </button>
                    <button type="submit" class="custom-button" id="assignment-button">
                        ${see_assignments_button}
                    </button>
                </form>
            </div>
        </c:if>
        <c:if test="${fn:length(requestScope.orders) eq 0}">
            <div id="no-orders-container">
                <p>${zero_orders}</p>
                <form id="no-orders-form" action="controller?command=showOrderPage" method="post">
                    <input type="submit" class="custom-button" id="buy-membership-button" value="${buy_membership_button}"/>
                </form>
            </div>
        </c:if>
        <div class="popup" id="feedback-popup">
            <div id="feedback-popup-header">
                <a class="close-icon" href="javascript:hidePopUp('#feedback-popup')">
                    <i class="fa fa-times fa-lg"></i>
                </a>
                <span id="feedback-title">${feedback_title}</span>
                <hr/>
            </div>
            <form id="feedback-form" action="controller?command=sendFeedback" method="post">
                <label for="feedback-textarea"></label>
                <textarea name="feedback" id="feedback-textarea" rows="7" cols="34" minlength="10" maxlength="1000"
                          placeholder="${feedback_placeholder}" required></textarea>
                <span id="feedback-restriction">${feedback_restriction}</span>
                <input type="hidden" name="order_id" class="hidden-id"/>
                <input type="submit" class="custom-button" id="send-feedback" value="${send_feedback_button}"/>
            </form>
        </div>

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
</html>