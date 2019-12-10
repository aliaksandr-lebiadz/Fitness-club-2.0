<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fc" uri="fitness-club" %>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="trainer_clients.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="clients_list.title" var="clients_list_title"/>
    <fmt:message key="table.title" var="table_title"/>
    <fmt:message key="table.button.add_assignment" var="add_assignment_button"/>
    <fmt:message key="table.button.see_assignments" var="see_assignments_button"/>
    <fmt:message key="table.button.nutrition" var="nutrition_button"/>
    <fmt:message key="assignment_popup.title" var="assignment_popup_title"/>
    <fmt:message key="assignment_popup.label.workout_date" var="workout_date"/>
    <fmt:message key="assignment_popup.label.exercise" var="exercise"/>
    <fmt:message key="assignment_popup.label.amount_of_sets" var="amount_of_sets"/>
    <fmt:message key="assignment_popup.label.amount_of_reps" var="amount_of_reps"/>
    <fmt:message key="assignment_popup.button" var="assignment_popup_button"/>
    <fmt:message key="nutrition_popup.title" var="nutrition_popup_title"/>
    <fmt:message key="nutrition_popup.label.nutrition" var="nutrition"/>
    <fmt:message key="nutrition_popup.button" var="nutrition_popup_button"/>
    <fmt:message key="zero_clients.message" var="zero_clients_message"/>
    <fmt:message key="zero_clients.button" var="home_button"/>
</fmt:bundle>
<fmt:bundle basename="pages_content" prefix="orders.">
    <fmt:message key="table.begin_date" var="begin_date"/>
    <fmt:message key="table.end_date" var="end_date"/>
</fmt:bundle>
<fmt:bundle basename="pages_content" prefix="nutrition_type.">
    <fmt:message key="low_calorie" var="low_calorie"/>
    <fmt:message key="medium_calorie" var="medium_calorie"/>
    <fmt:message key="high_calorie" var="high_calorie"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>${title}</title>
        <link rel="icon" href="${pageContext.request.contextPath}/icons/title_icon.png"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/trainer_clients.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/display_table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/select.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/disable_div.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/assignment_popup.css">
        <script src="${pageContext.request.contextPath}/static/jquery-2.0.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/table_select.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/transfer.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/popup.js"></script>

    </head>
    <body>
        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>

        <div id="disable-div"></div>
        <c:if test="${fn:length(requestScope.clients) eq 0}">
            <div id="no-clients-container">
                <p>${zero_clients_message}</p>
                <form id="no-clients-form" action="controller?command=showHomePage" method="post">
                    <input type="submit" class="custom-button" id="home-button" value="${home_button}"/>
                </form>
            </div>
        </c:if>
        <c:if test="${fn:length(requestScope.clients) ne 0}">
            <div id="clients-container">
                <span id="clients-title">${clients_list_title}</span>
                <hr>
                <form id="clients-form" action="controller" method="get">
                    <c:forEach items="${requestScope.clients}" var="item">
                        <input type="hidden" id="hidden-client-id" name="client_id"/>
                        <input type="hidden" name="command" value="showTrainerClients"/>
                        <div class="client" onclick="transferIdAndSubmitForm(${item.id}, '#clients-form')">
                                ${item.firstName} ${item.secondName}
                        </div>
                    </c:forEach>
                </form>
            </div>
            <div id="orders-container">
                <span id="orders-title">${table_title}</span>
                <hr>
                <display:table class="display-table" name="requestScope.client_orders" uid="row" pagesize="5" export="false" requestURI="">
                    <display:column property="id" class="hidden" headerClass="hidden"/>
                    <display:column title="${begin_date}">
                        <fmt:formatDate value="${row.beginDate}"/>
                    </display:column>
                    <display:column title="${end_date}">
                        <fmt:formatDate value="${row.endDate}"/>
                    </display:column>
                </display:table>
                <hr>
                <form class="assignment-form" id="see-assignments-form" action="controller" method="get">
                    <button type="button" class="custom-button" id="nutrition-button"
                            onclick="showPopUp('#nutrition-popup')">
                            ${nutrition_button}
                    </button>
                    <button type="button" class="custom-button" id="assignment-button"
                            onclick="showPopUp('#assignment-popup')">
                            ${add_assignment_button}
                    </button>
                    <input type="button" class="custom-button" id="see-assignments-button"
                           value="${see_assignments_button}"
                           onclick="if($('.hidden-id').val() !== ''){ $('#see-assignments-form').submit(); }"/>
                    <input type="hidden" name="order_id" class="hidden-id"/>
                    <input type="hidden" name="command" value="showAssignments"/>
                </form>
            </div>
            <div class="popup" id="nutrition-popup">
                <div id="nutrition-popup-header">
                    <a class="close-icon" href="javascript:hidePopUp('#nutrition-popup')">
                        <i class="fa fa-times fa-lg"></i>
                    </a>
                    <span id="nutrition-popup-title">${assignment_popup_title}</span>
                    <hr/>
                </div>
                <form id="nutrition-form" action="controller?command=assignNutritionType" method="post">
                    <label for="nutrition-type-choice">${nutrition}</label>
                    <div class="select-style">
                        <select name="nutrition_type" id="nutrition-type-choice">
                            <option value="low calorie">${low_calorie}</option>
                            <option value="medium calorie">${medium_calorie}</option>
                            <option value="high calorie">${high_calorie}</option>
                        </select>
                    </div>
                    <input type="hidden" name="order_id" class="hidden-id"/>
                    <input type="submit" class="custom-button" id="assign-nutrition-type-button" value="${nutrition_popup_button}"/>
                </form>
            </div>
            <div class="popup" id="assignment-popup">
                <div id="assignment-popup-header">
                    <a class="close-icon" href="javascript:hidePopUp('#assignment-popup')">
                        <i class="fa fa-times fa-lg"></i>
                    </a>
                    <span id="assignment-popup-title">${assignment_popup_title}</span>
                    <hr/>
                </div>
                <form class="assignment-form" action="controller?command=changeAssignment&operation=add" method="post">
                    <label>${workout_date}</label>
                    <fc:date-input name="date" minDate="today"/>
                    <label for="exercise-select-id">${exercise}</label>
                    <div class="select-style">
                        <select name="exercise_select" id="exercise-select-id">
                            <c:forEach items="${requestScope.exercises}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="amount-of-sets">${amount_of_sets}</label>
                    <input type="number" name="amount_of_sets" id="amount-of-sets" min="1" required>
                    <label for="amount-of-reps">${amount_of_reps}</label>
                    <input type="number" name="amount_of_reps" id="amount-of-reps" min="1" required>
                    <input type="hidden" name="order_id" class="hidden-id"/>
                    <input type="submit" class="custom-button" id="add-assignment-button" value="${assignment_popup_button}"/>
                </form>
            </div>
        </c:if>

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
</html>