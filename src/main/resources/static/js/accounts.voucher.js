/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 31-Dec-17.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

$(document).ready(function () {

    $("#save").on('click', function(event) {
        //region Variable
        var tableLength = $('.group-list tr').length-1;
        var totalDebitAmount = parseFloat($('#debitTotalAmount').text());
        var totalCreditAmount = parseFloat($('#creditTotalAmount').text());
        //endregion

        //region Row Wise Alert
        var particulars=true;
        $('.group-list tr').each( function (i, e) {
            //region Remove Row
            if(tableLength>1 && !isNaN(parseFloat(totalDebitAmount)) && !isNaN(parseFloat(totalCreditAmount)) && isBlankRow(tableLength) ){
                $('.group-list tr').eq(tableLength-1).find('.debitAmount').attr("onkeyup","addRow(this);");
                $('.group-list tr').eq(tableLength-1).find('.creditAmount').attr("onkeyup","addRow(this);");

                $('.group-list tr').eq(tableLength).remove();
            }
            //endregion
            var Dr = $(e).find('select.DrCr option:selected').val();
            var led = $(e).find('select.particular option:selected').val();
            var debit = $(e).find('.debitAmount').val();
            var credit = $(e).find('.creditAmount').val();
            console.log("Dr:"+Dr+"   Ledger:"+led.length+"  Debit:"+debit.length+"      Credit:"+credit.length);
            if(led.length==0){
                alert("Ledger is not selected. Row NO."+(parseInt(i)+1));
                particulars=false;
                return false;
            }
            else if(Dr==1 && (debit.length==0 || parseFloat(debit)==0)){
                alert("Debit amount is not enitred. Row NO."+(parseInt(i)+1));
                particulars=false;
                return false;
            }
            else if(Dr==2 && (credit.length==0 || parseFloat(credit)==0)){
                alert("Credit amount is not enitred. Row NO."+(parseInt(i)+1));
                particulars=false;
                return false;
            }
        });

        if (!particulars) return false;
        //endregion

        //region Total Amount is not Same
        if (parseFloat($('#debitTotalAmount').text())!= parseFloat($('#creditTotalAmount').text())){
            alert("Total Debit & Credit Balance is not Same.");
            return;
        }
        //endregion

        document.getElementById("form").submit();
    });

    $('.preventDefault').on('keydown',function(event){
        var keyCode = event.which || event.keyCode;
        console.log(keyCode);
        if (keyCode==13) {
            event.preventDefault();
        }
    });

    $("#CostCenter").on('change', function(event) {
        if($('select#CostCenter option:selected').val()) {
            $('.group-list').find('select.cost-center').val($('select#CostCenter option:selected').val()).selectpicker('refresh');
            $('.th-cost-center').hide();
            $('.td-cost-center').hide();
        }
        else{
            $('.group-list').find('select.cost-center').val('default').selectpicker('refresh');
            $('.th-cost-center').show();
            $('.td-cost-center').show()
        }
    });
});

    //Dr & Cr Amount Disable and Ledger change on Dr/Cr Change Event.

    function DrCr(event){
        //region Input Variable Set
        var $this = $(event);
        var crtInput = $this.parents('tr').find('.creditAmount');
        var dbtInput = $this.parents('tr').find('.debitAmount');
        //endregion

        //region Dr/Cr Enable Disable For Change Event
        if($this.val() == 1){
            $(crtInput).attr("disabled","disabled");
            $(crtInput).val("");
            $(dbtInput).removeAttr("disabled");
            $(dbtInput).val("");
        }
        else{
            $(dbtInput).attr("disabled","disabled");
            $(dbtInput).val("");
            $(crtInput).removeAttr("disabled");
            $(crtInput).val("");
        }
        //endregion

        ajaxGetLedger($this.parents('tr').find('select.particular'),$('#voucherType').val(),$this.val());

        //region Total Debit And Total Credit Without Current Row
        var debitTotal =0.0;
        $('.debitAmount').each(function (i, e) {
            if ($(this).length > 0 && !isNaN(parseFloat($(this).val()))) {
                debitTotal += parseFloat($(this).val());
            }
        });
        var creditTotal = 0.0;
        $('.creditAmount').each(function (i, e) {
            if ($(this).length>0 && !isNaN(parseFloat($(this).val()))) {
                creditTotal += parseFloat($(this).val());
            }
        });
        //endregion

        //region Calculate Value for auto Balance
        if($this.val() == 1 && !$('#isDebit').val()){// && !$('#isDebit').val()
            dbtInput.val((debitTotal-creditTotal)<0?(debitTotal-creditTotal)*1:debitTotal-creditTotal);
        }
        else if($this.val() == 2 && $('#isDebit').val()) {//&& $('#isDebit').val()
            crtInput.val((debitTotal - creditTotal) < 0 ? (debitTotal - creditTotal) * 1 : debitTotal - creditTotal);
        }
        //endregion

        //region Total Debit And Total Credit Show Footer
        var TDebit=parseFloat(debitTotal);
        var CDebit=isNaN(parseFloat(dbtInput.val()))?parseFloat("0.00"):parseFloat(dbtInput.val());

        var TCredit=parseFloat(creditTotal);
        var CCredit=isNaN(parseFloat(crtInput.val()))?parseFloat("0.00"):parseFloat(crtInput.val());

        $('#debitTotalAmount').text(TDebit+CDebit);
        $('#creditTotalAmount').text(TCredit+CCredit);
        //endregion
    }

    function addRow(evt){
        var keyCode = event.which || event.keyCode;
        //console.log("Key:"+event.key+"<<<--->>>"+"Which: "+event.which+"<<<--->>>"+"Code: "+event.keyCode);
        var $this = $(evt);
        $('form').submit(function (ele) {
            ele.preventDefault();
        });

        if (keyCode==13) {
            var led = $this.parents('tr').find('select.particular option:selected').val();

            if(led=='' || led=='undefined'){
                alert("Ledger Name is not Selected. Please Select Ledger Name.");
                return;

            }
            if(isNaN($this.val()) || $this.val() == '' || parseFloat($this.val())==NaN || parseFloat($this.val())<=0){
                alert("Ledger Name is not Selected. Please Select Ledger Name.");
                return;
            }

            var index = $(".group-list tr").length;
            $('tfoot').append('<style>tfoot:before{content: "-"; display: block; line-height:'+ (28-(index*2)) + 'em; color: transparent; position:relative; }</style>');
            if($('#isNarration').val()=='true'){
                $('.group-list').append(
                    '<tr>'+
                        '<td class="td-dr-cr">'+
                            '<select class="selectpicker form-control-custome table-select-1 bsp-tab DrCr" onchange="DrCr(this);" >'+
                                '<option value="1">Dr</option>'+
                                '<option value="2">Cr</option>'+
                            '</select>'+
                        '</td>'+
                        '<td class="td-ledger-name">'+
                            '<select class="selectpicker form-control-custome table-select-2 particular" data-live-search="true" id="accountsVoucher'+index+'.ledger" name="accountsVoucher['+index+'].ledger">' +
                                '<option value="0">Select Accounts Ledgers</option>' +
                            '</select>'+
                        '</td>'+
                        '<td class="td-narration">'+
                            '<input type="text" id="accountsVoucher'+index+'.narration" name="accountsVoucher['+index+'].narration" class="form-control form-control-custome form-width-bsp bsp-tab preventDefaultMe narration" />'+
                        '</td>'+
                        '<td class="td-cost-center">'+
                            '<select class="selectpicker form-control-custome table-select-3  cost-center" id="accountsVoucher'+index+'.costCenter" name="accountsVoucher['+index+'].costCenter"  data-live-search="true">'+
                                '<option value="0">Select Cost Center</option>'+
                            '</select>'+
                        '</td>'+
                        '<td class="td-debit">'+
                            '<input type="text" class="form-control form-control-custome form-width-bsp bsp-tab preventDefaultMe debitAmount" onkeyup="addRow(this);" style="border:1px #0000ff;" id="accountsVoucher'+index+'.debitAmount" name="accountsVoucher['+index+'].debitAmount" value="0.0">'+
                        '</td>'+
                        '<td class="td-credit">'+
                            '<input type="text" class="form-control form-control-custome form-width-bsp bsp-tab preventDefaultMe creditAmount" onkeyup="addRow(this);" id="accountsVoucher'+index+'.creditAmount" name="accountsVoucher['+index+'].creditAmount" value="0.0" disabled="disabled">'+
                        '</td>'+
                    '</tr>'
                );
            }
            else{
                $('.group-list').append(
                    '<tr>'+
                        '<td class="td-dr-cr">'+
                            '<select class="selectpicker form-control-custome table-select-1 bsp-tab DrCr" onchange="DrCr(this);" >'+
                                '<option value="1">Dr</option>'+
                                '<option value="2">Cr</option>'+
                            '</select>'+
                        '</td>'+
                        '<td class="td-ledger-name">'+
                            '<select class="selectpicker form-control-custome table-select-2 particular" data-live-search="true" id="accountsVoucher'+index+'.ledger" name="accountsVoucher['+index+'].ledger">' +
                                '<option value="0">Select Accounts Ledgers</option>' +
                            '</select>'+
                        '</td>'+
                        '<td class="td-cost-center">'+
                            '<select class="selectpicker form-control-custome table-select-3  cost-center" id="accountsVoucher'+index+'.costCenter" name="accountsVoucher['+index+'].costCenter"  data-live-search="true">'+
                                '<option value="0">Select Cost Center</option>'+
                            '</select>'+
                        '</td>'+
                        '<td class="td-debit">'+
                            '<input type="text" class="form-control form-control-custome form-width-bsp bsp-tab preventDefaultMe debitAmount" onkeyup="addRow(this);" style="border:1px #0000ff;" id="accountsVoucher'+index+'.debitAmount" name="accountsVoucher['+index+'].debitAmount" value="0.0">'+
                        '</td>'+
                        '<td class="td-credit">'+
                            '<input type="text" class="form-control form-control-custome form-width-bsp bsp-tab preventDefaultMe creditAmount" onkeyup="addRow(this);" id="accountsVoucher'+index+'.creditAmount" name="accountsVoucher['+index+'].creditAmount" value="0.0" disabled="disabled">'+
                        '</td>'+
                    '</tr>'
                );
            }

            $('.DrCr').selectpicker('refresh');
            ajaxGetLedger($this.parents('tr').next().find('select.particular'),$('#voucherType').val(),$this.parents('tr').find('select.DrCr option:selected').val());
            ajaxGetCostCenter($this.parents('tr').next().find('select.cost-center'));
            $this.parents('tr').find('.debitAmount').removeAttr('onkeyup');
            $this.parents('tr').find('.creditAmount').removeAttr('onkeyup');
            $this.parents('tr').next().find('.btn').focus();
        }
    }

    function ajaxGetLedger(particulars,voucherTypeFlag,val) {
    $.ajax({
        type: "GET",
        url: window.location.protocol + "//" + window.location.host + "/bsp/accountingVouchers" + "/ledger/"+voucherTypeFlag+"/"+val,
        success: function (result) {
            if (result.status == "Done") {
                var ledger = "<option value=''>Select Accounts Ledgers</option>";
                $.each(result.data, function (i, accountsLedgers) {
                    ledger += "<option value=" + accountsLedgers.id + ">" + accountsLedgers.name + "</option>";
                    //console.log(i+". Group ID: "+accountsLedgers.group.id+"<---> Group Name:"+accountsLedgers.group.name);
                });
                $(particulars).html(ledger);
                $(particulars).selectpicker('refresh');
            } else {
                alert("Error");
            }
        },
        error: function (jqXHR, exception) {
            var msg = '';
            if (jqXHR.status === 0) {
                msg = 'Not connect.\n Verify Network.';
            } else if (jqXHR.status == 404) {
                msg = 'Requested page not found. [404]';
            } else if (jqXHR.status == 500) {
                msg = 'Internal Server Error [500].'+jqXHR.status;
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
            alert("JQuery Error [cm-mehdi manual error generate]"+msg);
        }
    });
}

    //Total Amount   keyup
    $(".group-list").on('keyup','input',function(event) {
        //For Total Amount ----Start
        var totalRow = 0;
        var debitTotal = 0.00;
        $('.debitAmount').each(function (i, e) {
            if ($(this).val() != "" && $(this).val() != "0.0") {
                totalRow++;
                debitTotal += parseFloat($(this).val());
            }
        });
        $('#debitTotalAmount').text(debitTotal);

        var creditTotal = 0.00;
        $('.creditAmount').each(function (i, e) {
            if ($(this).val() != "" && $(this).val() != "0.0") {
                totalRow++;
                creditTotal += parseFloat($(this).val());
                //console.log("credit: " + $(this).val() + " <<<>>> " + $('#CountRow').val());
            }
        });
        $('#creditTotalAmount').text(creditTotal);
    //For Total Amount ----End
    });

    //Create CostCenter Dropdown Change Event will be
    function ajaxGetCostCenter(particulars) {
    $.ajax({
        type: "GET",
        url: window.location.protocol + "//" + window.location.host + "/bsp/accountingVouchers" + "/costCenter/",
        success: function (result) {
            if (result.status == "Done") {
                var CostCenter = "<option value=''>Select Cost Centeres</option>";
                $.each(result.data, function (i, CostCenteres) {
                    CostCenter += "<option value=" + CostCenteres.id + ">" + CostCenteres.name + "</option>";
                    //console.log(i+". Group ID: "+accountsLedgers.group.id+"<---> Group Name:"+accountsLedgers.group.name);
                });
                $(particulars).html(CostCenter);
                $(particulars).selectpicker('refresh');
            } else {
                var CostCenter = "<option value=''>Select Cost Centeres</option>";
                $(particulars).html(CostCenter);
                $(particulars).selectpicker('refresh');
                alert("Error");
            }
        },
        error: function (jqXHR, exception) {
            var msg = '';
            if (jqXHR.status === 0) {
                msg = 'Not connect.\n Verify Network.';
            } else if (jqXHR.status == 404) {
                msg = 'Requested page not found. [404]';
            } else if (jqXHR.status == 500) {
                msg = 'Internal Server Error [500].'+jqXHR.status;
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
            alert("JQuery Error [cm-mehdi manual error generate]"+msg);
        }
    });
}

    function isBlankRow(index){
       var isBlank=false;
       var led = $('.group-list tr').eq(index).find('select.particular option:selected').val();
       var deb = $('.group-list tr').eq(index).find('.debitAmount').val();
       var cre = $('.group-list tr').eq(index).find('.creditAmount').val();
       if (led.length==0 && (deb=='' || parseFloat(deb)==parseFloat(0)) && (cre=='' || parseFloat(cre)==parseFloat(0)) ){
           return isBlank=true;
       }
       return isBlank;
   }