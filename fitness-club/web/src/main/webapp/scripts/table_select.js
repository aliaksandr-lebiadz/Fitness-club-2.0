$(document).ready(function(){
    $('.display-table tr').on('click', function() {
        $('.display-table tr').removeClass('selected');
        $(this).addClass('selected');
        var $currentRow = $(this).closest('tr');
        var $id = $currentRow.find('td:eq(0)').text();
        $('.hidden-id').val($id);
        if($(this).parent().parent().attr('id') === 'assignment'){
            var $amountOfSets = $currentRow.find('td:eq(3)').text();
            var $amountOfReps = $currentRow.find('td:eq(4)').text();
            $('#amount-of-sets').val($amountOfSets);
            $('#amount-of-reps').val($amountOfReps);
        }
    });
});