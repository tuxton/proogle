// Funciones de JavaScript

function disableBuscador(){

    if ($('.check_mmr').is(':checked')) {
        $('.buscador').attr('disabled', true);
        $('.buscador').addClass("disableInput");
        $('.buscador').val('');
    } else {
        $('.buscador').removeAttr('disabled');
        $('.buscador').removeClass('disableInput');
    }
}