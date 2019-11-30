$(document).ready(function(){
    hidePopUp('.popup');
});
function showPopUp(id){
    if(id === '#purchase-popup' || $('.hidden-id').val() !== '') {
        $('#disable-div').show();
        $(id).show();
    }
}
function hidePopUp(id){
    $('#disable-div').hide();
    $(id).hide();
}