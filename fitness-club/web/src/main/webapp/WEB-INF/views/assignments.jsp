<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fc" uri="fitness-club" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale scope="session" value="${sessionScope.locale}"/>

<fmt:bundle basename="pages_content" prefix="assignments.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="zero_assignments.message" var="zero_assignments"/>
    <fmt:message key="zero_assignments.trainer.message" var="zero_assignments_by_trainer"/>
    <fmt:message key="zero_assignments.button" var="orders_button"/>
    <fmt:message key="zero_assignments.trainer.button" var="clients_button"/>
    <fmt:message key="nutrition_type" var="nutrition_type_msg"/>
    <fmt:message key="table.exercise" var="exercise"/>
    <fmt:message key="table.amount_of_sets" var="amount_of_sets"/>
    <fmt:message key="table.amount_of_reps" var="amount_of_reps"/>
    <fmt:message key="table.workout_date" var="workout_date"/>
    <fmt:message key="table.status" var="status"/>
    <fmt:message key="table.pages.name" var="pages_name"/>
    <fmt:message key="button.accept" var="accept_button"/>
    <fmt:message key="button.change" var="change_button"/>
    <fmt:message key="button.cancel" var="cancel_button"/>
    <fmt:message key="popup_title" var="popup_title"/>
</fmt:bundle>
<fmt:bundle basename="pages_content" prefix="assignment_status.">
    <fmt:message key="new" var="new_status"/>
    <fmt:message key="accepted" var="accepted_status"/>
    <fmt:message key="changed" var="changed_status"/>
    <fmt:message key="canceled" var="canceled_status"/>
</fmt:bundle>
<fmt:bundle basename="pages_content" prefix="nutrition_type.">
    <fmt:message key="low_calorie" var="low_calorie_nutrition"/>
    <fmt:message key="medium_calorie" var="medium_calorie_nutrition"/>
    <fmt:message key="high_calorie" var="high_calorie_nutrition"/>
</fmt:bundle>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>${title}</title>
        <link rel="icon" href="${pageContext.request.contextPath}/icons/title_icon.png"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main/assignments.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/display_table.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/disable_div.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/assignment_popup.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/additional/select.css">
        <script src="${pageContext.request.contextPath}/static/jquery-2.0.2.min.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/table_select.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/transfer.js"></script>
        <script src="${pageContext.request.contextPath}/scripts/popup.js"></script>

    </head>
    <body>
        <jsp:include page="/WEB-INF/views/header.jsp"/>

        <div id="intro"></div>
        <div id="disable-div"></div>

        <c:if test="${fn:length(requestScope.assignments) eq 0}">
            <div id="no-assignments-container">
                <c:if test="${sessionScope.user.role eq 'CLIENT'}">
                    <p>${zero_assignments}</p>
                    <form id="no-assignments-form" action="controller?command=showOrders" method="post">
                        <input type="submit" class="custom-button" id="orders-button" value="${orders_button}"/>
                    </form>
                </c:if>
                <c:if test="${sessionScope.user.role ne 'CLIENT'}">
                    <p>${zero_assignments_by_trainer}</p>
                    <form id="no-assignments-form" action="controller?command=showTrainerClients" method="post">
                        <input type="submit" class="custom-button" id="clients-button" value="${clients_button}"/>
                    </form>
                </c:if>
            </div>
        </c:if>
        <c:if test="${fn:length(requestScope.assignments) ne 0}">
            <div id="container">
                <form class="check-submit-form" id="assignment-form" action="controller?command=changeAssignmentStatus" method="post">
                    <c:set var="nutrition_type" scope="page" value="${requestScope.nutrition_type}"/>
                    <c:if test="${not empty nutrition_type}">
                        <span id="nutrition-type">
                                ${nutrition_type_msg} <fc:nutrition-type-localizer>${nutrition_type}</fc:nutrition-type-localizer>
                        </span>
                    </c:if>
                    <display:table class="display-table" name="requestScope.assignments" uid="assignment" pagesize="5" export="false" requestURI="">
                        <display:column property="id" class="hidden" headerClass="hidden"/>
                        <display:column title="${workout_date}">
                            <fmt:formatDate value="${assignment.workoutDate}"/>
                        </display:column>
                        <display:column title="${exercise}">
                            ${assignment.exercise.name}
                        </display:column>
                        <display:column property="amountOfSets" title="${amount_of_sets}"/>
                        <display:column property="amountOfReps" title="${amount_of_reps}"/>
                        <display:column title="${status}">
                            <fc:status-localizer>${assignment.status}</fc:status-localizer>
                        </display:column>
                        <display:setProperty name="paging.banner.items_name" value="${pages_name}"/>
                    </display:table>
                    <hr>
                    <input type="hidden" name="assignment_id" class="hidden-id"/>
                    <input type="hidden" name="assignment_action" id="hidden-action"/>
                    <c:if test="${sessionScope.user.role eq 'CLIENT'}">
                        <input type="button" class="custom-button action-button" value="${accept_button}"
                               onclick="$('#hidden-action').val('accept');
                               if($('.hidden-id').val() !== ''){ $('#assignment-form').submit();}">
                    </c:if>
                    <input type="button" class="custom-button action-button" value="${change_button}"
                           onclick="showPopUp('#change-assignment-popup')">
                    <input type="button" class="custom-button action-button" value="${cancel_button}"
                            onclick="
                            $('#hidden-action').val('cancel');
                            if($('.hidden-id').val() !== ''){ $('#assignment-form').submit(); }"/>
                </form>
            </div>
            <div class="popup" id="change-assignment-popup">
                <div id="change-assignment-popup-header">
                    <a class="close-icon" href="javascript:hidePopUp('#change-assignment-popup')">
                        <i class="fa fa-times fa-lg"></i>
                    </a>
                    <span id="assignment-popup-title">${popup_title}</span>
                    <hr/>
                </div>
                <form id="change-assignment-popup-form" action="controller?command=changeAssignment&operation=change" method="post">
                    <label for="date">${workout_date}</label>
                    <fc:date-input name="date" minDate="tomorrow"/>
                    <label for="exercise-select-id">${exercise}</label>
                    <div class="select-style">
                        <select name="exercise_select" id="exercise-select-id">
                            <c:forEach items="${requestScope.exercises}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="amount-of-sets">${amount_of_sets}</label>
                    <input type="number" name="amount_of_sets" id="amount-of-sets" min="1" max="100" required>
                    <label for="amount-of-reps">${amount_of_reps}</label>
                    <input type="number" name="amount_of_reps" id="amount-of-reps" min="1" max="100" required>
                    <input type="hidden" name="assignment_id" class="hidden-id"/>
                    <input type="submit" class="custom-button" id="add-assignment-button" value="${change_button}"/>
                </form>
            </div>
        </c:if>

        <jsp:include page="/WEB-INF/views/footer.jsp"/>
    </body>
