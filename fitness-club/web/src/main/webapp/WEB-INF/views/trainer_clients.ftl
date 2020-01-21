<#import "/spring.ftl" as spring/>
<#assign display = JspTaglibs["http://displaytag.sf.net"]/>
<#assign security = JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign fc = JspTaglibs["fitness-club"]/>

<#assign title><@spring.message "trainer_clients.title"/></#assign>
<#assign clients_list_title><@spring.message "trainer_clients.clients_list.title"/></#assign>
<#assign table_title><@spring.message "trainer_clients.table.title"/></#assign>
<#assign add_assignment_button><@spring.message "trainer_clients.table.button.add_assignment"/></#assign>
<#assign see_assignments_button><@spring.message "trainer_clients.table.button.see_assignments"/></#assign>
<#assign nutrition_button><@spring.message "trainer_clients.table.button.nutrition"/></#assign>
<#assign assignment_popup_title><@spring.message "trainer_clients.assignment_popup.title"/></#assign>
<#assign workout_date><@spring.message "trainer_clients.assignment_popup.label.workout_date"/></#assign>
<#assign exercise><@spring.message "trainer_clients.assignment_popup.label.exercise"/></#assign>
<#assign amount_of_sets><@spring.message "trainer_clients.assignment_popup.label.amount_of_sets"/></#assign>
<#assign amount_of_reps><@spring.message "trainer_clients.assignment_popup.label.amount_of_reps"/></#assign>
<#assign assignment_popup_button><@spring.message "trainer_clients.assignment_popup.button"/></#assign>
<#assign nutrition_popup_title><@spring.message "trainer_clients.nutrition_popup.title"/></#assign>
<#assign nutrition><@spring.message "trainer_clients.nutrition_popup.label.nutrition"/></#assign>
<#assign nutrition_popup_button><@spring.message "trainer_clients.nutrition_popup.button"/></#assign>
<#assign zero_clients_message><@spring.message "trainer_clients.zero_clients.message"/></#assign>
<#assign home_button><@spring.message "trainer_clients.zero_clients.button"/></#assign>

<#assign begin_date><@spring.message "orders.table.begin_date"/></#assign>
<#assign end_date><@spring.message "orders.table.end_date"/></#assign>

<#assign low_calorie><@spring.message "nutrition_type.low_calorie"/></#assign>
<#assign medium_calorie><@spring.message "nutrition_type.medium_calorie"/></#assign>
<#assign high_calorie><@spring.message "nutrition_type.high_calorie"/></#assign>

<!DOCTYPE html>
<html lang="${.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>${title}</title>
    <link rel="icon" href="<@spring.url '/icons/title_icon.png'/>"/>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/main/trainer_clients.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/display_table.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/select.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/disable_div.css'/>">
    <link rel="stylesheet" type="text/css" href="<@spring.url '/styles/additional/assignment_popup.css'/>">
    <script src="<@spring.url '/static/jquery-2.0.2.min.js'/>"></script>
    <script src="<@spring.url '/scripts/table_select.js'/>"></script>
    <script src="<@spring.url '/scripts/transfer.js'/>"></script>
    <script src="<@spring.url '/scripts/popup.js'/>"></script>

</head>
<body>

<#include "header.ftl"/>

    <div id="intro"></div>

    <div id="disable-div"></div>
    <#if clients?size == 0>
        <div id="no-clients-container">
            <p>${zero_clients_message}</p>
            <form id="no-clients-form" action="<@spring.url '/home'/>">
                <input type="submit" class="custom-button" id="home-button" value="${home_button}"/>
            </form>
        </div>
    <#else>
        <div id="clients-container">
            <span id="clients-title">${clients_list_title}</span>
            <hr>
            <form id="clients-form" action="<@spring.url '/trainer/clients'/>">
                <input type="hidden" id="hidden-client-id" name="client_id"/>
                <#list clients as item>
                    <div class="client" onclick="transferIdAndSubmitForm(${item.id}, '#clients-form')">
                        ${item.firstName} ${item.secondName}
                    </div>
                </#list>
            </form>
        </div>
        <div id="orders-container">
            <span id="orders-title">${table_title}</span>
            <hr>
            <#if client_orders??>
                <@display.table class="display-table" name=client_orders uid="row" pagesize=5 export=false>
                    <@display.column property="id" class="hidden" headerClass="hidden"/>
                    <@display.column title="${begin_date}" value="${(row.beginDate?date)!'UNDEFINED'}"/>
                    <@display.column title="${end_date}" value="${(row.endDate?date)!'UNDEFINED'}"/>
                </@display.table>
            <hr>
            </#if>
            <form class="assignment-form" id="see-assignments-form" action="<@spring.url '/assignment/list'/>">
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
            <form id="nutrition-form" action="<@spring.url '/order/setNutrition'/>" method="post">
                <label for="nutrition-type-choice">${nutrition}</label>
                <div class="select-style">
                    <select name="nutrition_type" id="nutrition-type-choice">
                        <option value="LOW_CALORIE">${low_calorie}</option>
                        <option value="MEDIUM_CALORIE">${medium_calorie}</option>
                        <option value="HIGH_CALORIE">${high_calorie}</option>
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
            <form class="assignment-form" action="<@spring.url '/assignmentOperations/add'/>" method="post">
                <label>${workout_date}</label>
                <@fc.dateinput name="date" minDate="today"/>
                <label for="exercise-select-id">${exercise}</label>
                <div class="select-style">
                    <select name="exercise_select" id="exercise-select-id">
                        <#if exercises??>
                            <#list exercises as item>
                                <option value="${item.id}">${item.name}</option>
                            </#list>
                        </#if>
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
    </#if>

<#include "footer.ftl"/>

</body>
</html>