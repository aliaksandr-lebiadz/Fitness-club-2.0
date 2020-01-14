function transferIdAndSubmitForm(id, formId){
    $('#hidden-client-id').val(id);
    $(formId).submit();
}