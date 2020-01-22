//jQuery( document ).ready( function( $ ) {
$(document).ready(function () {

    $(".bsp-tab").not($(":button")).keydown(function (evt) {
        if(evt.keyCode == 13 && evt.shiftKey){
            evt.preventDefault();
            var fields = $(this).parents('form:eq(0),body').find('.bsp-tab');
            var index = fields.index(this);
            if (index > -1) {
                fields.eq(index - 1).focus();
                return false;
            }
        }
        if (evt.keyCode == 13) {
            //alert("default 13");
            var iname = $(this).val();
            if($(this).attr("id")=='cm-mehdi'){
                var fields = $(this).parents('form:eq(0),body').find('.bsp-tab');
                var index = fields.index(this);
                if (index > -1) {
                    fields.eq(index + 2).focus();
                    fields.eq(index + 2).select();
                }
            }
            else if (iname !== 'Submit') {
                var fields = $(this).parents('form:eq(0),body').find('.bsp-tab');
                var index = fields.index(this);
                if (index > -1 ) {
                    fields.eq(index + 1).focus();
                    fields.eq(index + 1).select();
                }
                return false;
            }
        }
    });

    $('.owl-carousel').owlCarousel({
        loop: true,
        margin: 10,
        responsiveClass:true,
        nav: false,
        dots: false,
        responsive: {
            0: {
                items: 1,
                loop: false
            },
            600: {
                items: 3,
                loop: false
            },
            1000: {
                items: 5,
                loop: false
            }
        }
    });

    $('#Particulars').focus();



    var date = $('#datepicker-menu').datepicker({ dateFormat: 'dd-mm-yyyy', autoclose:'true' }).val();

    var date = $('#date1').datepicker({ dateFormat: 'dd-mm-yyyy', autoclose:'true' }).val();
    var date = $('#date2').datepicker({ dateFormat: 'dd-mm-yyyy', autoclose:'true' }).val();

    $('.expandCourse').on('click',function() {
        $("[class^=link]").not($("."+this.id)).slideUp();
        $("."+this.id).slideToggle();
    });



});


