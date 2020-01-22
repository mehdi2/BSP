/**
 * Created by Dell on 12/25/2017.
 */

$( document ).ready(function() {



    // GET REQUEST
    $("#getAllCustomerId").click(function(event){
        event.preventDefault();
        ajaxGet();
    });

    // DO GET
    function ajaxGet(){
        $.ajax({
            type : "GET",
            url : window.location + "accountingVouchers/ledger/{id}",
            success: function(result){
                if(result.status == "Done"){
                    $('#getResultDiv ul').empty();
                    var custList = "";
                    $.each(result.data, function(i, customer){
                        var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";
                        $('#getResultDiv .list-group').append(customer)
                    });
                    console.log("Success: ", result);
                }else{
                    $("#getResultDiv").html("<strong>Error</strong>");
                    console.log("Fail: ", result);
                }
            },
            error : function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    }
})