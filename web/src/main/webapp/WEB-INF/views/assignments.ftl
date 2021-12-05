<#import "/spring.ftl" as spring/>
<#assign display = JspTaglibs["http://displaytag.sf.net"]/>
<#assign security = JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign fc = JspTaglibs["fitness-club"]/>

<#assign title><@spring.message "assignments.title"/></#assign>
<#assign zero_assignments><@spring.message "assignments.zero_assignments.message"/></#assign>
<#assign zero_assignments_by_trainer><@spring.message "assignments.zero_assignments.trainer.message"/></#assign>
<#assign orders_button><@spring.message "assignments.zero_assignments.button"/></#assign>
<#assign clients_button><@spring.message "assignments.zero_assignments.trainer.button"/></#assign>

<#assign nutrition_type_msg><@spring.message "assignments.nutrition_type"/></#assign>
<#assign exercise><@spring.message "assignments.table.exercise"/></#assign>
<#assign amount_of_sets><@spring.message "assignments.table.amount_of_sets"/></#assign>
<#assign amount_of_reps><@spring.message "assignments.table.amount_of_reps"/></#assign>
<#assign workout_date><@spring.message "assignments.table.workout_date"/></#assign>
<#assign status><@spring.message "assignments.table.status"/></#assign>
<#assign pages_name><@spring.message "assignments.table.pages.name"/></#assign>
<#assign accept_button><@spring.message "assignments.button.accept"/></#assign>
<#assign change_button><@spring.message "assignments.button.change"/></#assign>
<#assign cancel_button><@spring.message "assignments.button.cancel"/></#assign>
<#assign popup_title><@spring.message "assignments.popup_title"/></#assign>

<#assign new_status><@spring.message "assignment_status.new"/></#assign>
<#assign accepted_status><@spring.message "assignment_status.accepted"/></#assign>
<#assign changed_status><@spring.message "assignment_status.changed"/></#assign>
<#assign canceled_status><@spring.message "assignment_status.canceled"/></#assign>

<#assign low_calorie_nutrition><@spring.message "nutrition_type.low_calorie"/></#assign>
<#assign medium_calorie_nutrition><@spring.message "nutrition_type.medium_calorie"/></#assign>
<#assign high_calorie_nutrition><@spring.message "nutrition_type.high_calorie"/></#assign>

<#macro status_localizer value>
    <#switch value>
        <#case "NEW">
            ${new_status}
            <#break>
        <#case "ACCEPTED">
            ${accepted_status}
            <#break>
        <#case "CHANGED">
            ${changed_status}
            <#break>
        <#case "CANCELED">
            ${canceled_status}
            <#break>
    </#switch>
</#macro>

<#macro nutrition_localizer value>
    <#switch value>
        <#case "LOW_CALORIE">
            ${low_calorie_nutrition}
            <#break>
        <#case "MEDIUM_CALORIE">
            ${medium_calorie_nutrition}
            <#break>
        <#case "HIGH_CALORIE">
            ${high_calorie_nutrition}
            <#break>
    </#switch>
</#macro>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/assignments.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/display_table.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/disable_div.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/assignment_popup.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/select.css'/>">
    <script src="<@spring.url '/static/jquery-2.0.2.min.js'/>"></script>
    <script src="<@spring.url '/scripts/table_select.js'/>"></script>
    <script src="<@spring.url '/scripts/transfer.js'/>"></script>
    <script src="<@spring.url '/scripts/popup.js'/>"></script>

</head>
<body>

<#include "header.ftl"/>

    <div id="intro"></div>
    <div id="disable-div"></div>

    <#if assignmentList?size == 0>
        <div id="no-assignments-container">
            <@security.authorize access="hasAuthority('CLIENT')">
                <p>${zero_assignments}</p>
                <form id="no-assignments-form" action="<@spring.url '/order/list'/>">
                    <input type="submit" class="custom-button" id="orders-button" value="${orders_button}"/>
                </form>
            </@security.authorize>
            <@security.authorize access="hasAuthority('TRAINER')">
                <p>${zero_assignments_by_trainer}</p>
                <form id="no-assignments-form" action="<@spring.url '/trainer/clients'/>">
                    <input type="submit" class="custom-button" id="clients-button" value="${clients_button}"/>
                </form>
            </@security.authorize>
        </div>
    <#else>
        <div id="container">
            <form class="check-submit-form" id="assignment-form" action="<@spring.url '/assignment/setStatus'/>" method="post">
                <#if nutrition_type??>
                    <span id="nutrition-type">
                            ${nutrition_type_msg} <@nutrition_localizer value="${nutrition_type}"/>
                    </span>
                </#if>
                <@display.table class="display-table" name=assignmentList uid="assignment" pagesize=5 export=false>
                    <@display.column property="id" class="hidden" headerClass="hidden"/>
                    <@display.column title="${workout_date}" value="${assignment.workoutDate?date}"/>
                    <@display.column title="${exercise}" value="${assignment.exercise.name}"/>
                    <@display.column property="amountOfSets" title="${amount_of_sets}"/>
                    <@display.column property="amountOfReps" title="${amount_of_reps}"/>
                    <@display.column title="${status}">
                        <@status_localizer value="${assignment.status}"/>
                    </@display.column>
                    <@display.setProperty name="paging.banner.items_name" value="${pages_name}"/>
                </@display.table>
                <hr>
                <input type="hidden" name="assignment_id" class="hidden-id"/>
                <input type="hidden" name="assignment_action" id="hidden-action"/>
                <@security.authorize access="hasAuthority('CLIENT')">
                    <input type="button" class="custom-button action-button" value="${accept_button}"
                           onclick="$('#hidden-action').val('accept');
                                   if($('.hidden-id').val() !== ''){ $('#assignment-form').submit();}">
                </@security.authorize>
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
            <form id="change-assignment-popup-form" action="<@spring.url '/assignmentOperations/change'/>" method="post">
                <label for="date">${workout_date}</label>
                <@fc.dateinput name="date" minDate="tomorrow"/>
                <label for="exercise-select-id">${exercise}</label>
                <div class="select-style">
                    <select name="exercise_select" id="exercise-select-id">
                        <#list exerciseList as item>
                            <option value="${item.id}">${item.name}</option>
                        </#list>
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
    </#if>

<#include "footer.ftl"/>

</body>
