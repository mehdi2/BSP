/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 15-Aug-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

$(document).ready(function () {

    $("#LedgerId").change(function (event) {
        alert($('#LedgerId').val());
        //alert($('#from').val());
        //alert($('#to').val());
        $.ajax({
            type: "GET",
            url: window.location.protocol + "//" + window.location.host + "/bsp/restStatement/statement/"+$('#LedgerId').val()+"/"+$('#from').val()+"/"+$('#to').val(), //1 is CompanyCode
            success: function (result) {
                if (result.status == "Done") {
                    //region Initiate
                    var Row=null;
                    var DebitAmount=0.0;
                    var CreditAmount=0.0;
                    var ClosingBalance=0.0;
                    $('tbody').html("");
                    //endregion
                    $("tbody").html("");
                    $.each(result.data, function (i, StatementEntity) {
                        //console.log("Ledger ID: "+StatementEntity.particulars+"  <------>   Ledger Name: "+StatementEntity.voucherType);
                        console.log("LOL"+i);
                        var Row = '<tr class="row100">'+
                            '<td class="column100 column1" data-column="column1">'+StatementEntity.date+'</td>'+
                            '<td class="column100 column2" data-column="column2">'+StatementEntity.particulars+'</td>'+
                            '<td class="column100 column3" data-column="column3">'+StatementEntity.voucherType+'</td>'+
                            '<td class="column100 column4" data-column="column4">'+StatementEntity.voucherNo+'</td>'+
                            '<td class="column100 column5" data-column="column5">'+StatementEntity.debitAmount+'</td>'+
                            '<td class="column100 column6" data-column="column6">'+StatementEntity.creditAmount+'</td>'+
                            '<td class="column100 column7" data-column="column7">'+StatementEntity.balance+'</td>'+
                            '</tr>';
                        $("tbody").append(Row);
                        //DebitAmount += ledgerVoucher.debitTotalAmount;
                        //CreditAmount += ledgerVoucher.creditTotalAmount;
                        //ClosingBalance = ledgerVoucher.closingBalance;
                    });

                    //region Footer Total Amount
                    //$('#DebitTotal').html(DebitAmount);
                    //$('#CreditTotal').html(CreditAmount);
                    //$('#ClosingBalance').html(ClosingBalance);
                    //endregion
                } else {
                    alert("Error");
                }
            },
            error: function (e) {
                console.log("ERROR: ", e);
            }
        });
    });
    //$("#from").change(function (event) {
    //    $("#LedgerId").one("change");
    //});
    //$("#to").change(function (event) {
    //    $("#LedgerId").one("change");
    //});

    //$("#LedgerId").on("change", "#from",function(){});
    //$("#from").on("change", "#LedgerId",function(){});

});