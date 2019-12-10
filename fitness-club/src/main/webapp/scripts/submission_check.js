$(document).ready(function(){
    $('.check-submit-form').submit(function(e){
        e.preventDefault();
        if($('.hidden-id').val() !== ''){
            this.submit();
        }
    });
});