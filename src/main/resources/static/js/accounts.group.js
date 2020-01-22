/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 18-Feb-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

$(document).ready(function () {

    //$(function() {
    //    $('input').on('keypress', function(e) {
    //        e.preventDefault();
    //        e.which !== 13 || $('[tabIndex=' + (+this.tabIndex + 1) + ']')[0].focus();
    //        //alert($(this).val());
    //    });
    //});

    $('#FileName').on('keydown', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $('[tabIndex=' + (+this.tabIndex + 1) + ']')[0].focus();
        }
    });

    $("#FileCode").on('select2:select', function (e) {

    });

    $("#FileTypeCode").on('select2:select', function (e) {
        $("#btnSave").focus();
    });

});