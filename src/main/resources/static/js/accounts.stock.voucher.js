/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 27-July-17.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

$(document).ready(function () {

    $("#save").on('click', function(event) {

       document.getElementById("form").submit();
    });

    $('.preventDefault').on('keydown',function(event){
        var keyCode = event.which || event.keyCode;
        console.log(keyCode);
        if (keyCode==13) {
            event.preventDefault();
        }
    });

    $("#Godown").on('change', function(event) {
        if($('select#Godown option:selected').val()) {
            console.log("Hide:"+$('select#Godown option:selected').val());
            $('.item-list').find('select.godwon').val($('select#Godown option:selected').val()).selectpicker('refresh');
            $('.th-godwon').hide();
            $('.td-godwon').hide();
        }
        else{
            console.log("Show:"+$('select#Godown option:selected').val());
            $('.item-list').find('select.godwon').val('default').selectpicker('refresh');
            $('.th-godwon').show();
            $('.td-godwon').show()
        }
    });
});

function selectItemCode(evt){
    var $this = $(evt);
    var name = $this.parents('tr').find('select.name option:selected').val();
    $this.parents('tr').find('select.code').val(name).selectpicker('refresh');

    $this.parents('tr').find('.unit').val("");
    ajaxGetItemUnit($this.parents('tr').find('.unit'),name);
}

function selectItemName(evt){
    var $this = $(evt);
    var code = $this.parents('tr').find('select.code option:selected').val();
    $this.parents('tr').find('select.name').val(code).selectpicker('refresh');

    $this.parents('tr').find('.unit').val("");
    ajaxGetItemUnit($this.parents('tr').find('.unit'),code);
}

function ajaxGetItemUnit(pUnit,pItemId) {
    $.ajax({
        type: "GET",
        url: window.location.protocol + "//" + window.location.host + "/bsp/accountingStockVouchers" + "/unit/"+pItemId,
        success: function (result) {
            if (result.status == "Done") {
                //$(pUnit).val(result.data.unit["unitSymbol"]);
                $(pUnit).val(result.data);
            }
            else {
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

function calculation(evt) {
    var $this = $(evt);
    checkValue($this);
    if($this.parents('tbody').attr('class')=="item-list")
        itemSubTotal($this);
    else if($this.parents('tbody').attr('class')=="ledger-list")
        ledgerSubTotal($this);
    sumTotalAmount();
}

/*-------------------------------------------------------*/
//Done
function addRow(evt){
    var keyCode = event.which || event.keyCode;
    var $this = $(evt);
    calculation(evt);
    $('form').submit(function (ele) {
        ele.preventDefault();
    });

    if (keyCode==13) {
        //region table Related
        var itemLength = $(".item-list tr").length;
        var additionalLegerLength = $(".ledger-list tr").length;
        //var index = itemLength+additionalLegerLength;
        var table = $this.parents('tbody').attr("class");
        //endregion

        if (table=="item-list")
            itemTable(evt,itemLength);
        else if (table=="ledger-list")
            ledgerTable(evt,additionalLegerLength)
    }
}


//function addLedgerRow(evt){
//    var keyCode = event.which || event.keyCode;
//    var $this = $(evt);
//    $('form').submit(function (ele) {
//        ele.preventDefault();
//    });
//
//    if (keyCode==13) {
//        var index = $(".group-list tr.par-amount").length;
//        var led = $this.parents('tr').find('select.ledger option:selected').val();
//        var percentage = $this.parents('tr').find('.percentage').val();
//        var amount = $this.parents('tr').find('.par-amount').val();
//        console.log(index);
//        //if (led==''&&isNaN(amount)&&percentage==0.0&& $this.val()<=0&&index>1){
//        //    alert("Ledger function");
//        //    return;
//        //}
//
//        console.log($this.attr('class'));
//
//        if((led=='' || led=='undefined')&& $this.attr('class')!='form-width-bsp bsp-tab amount' ){
//            alert("Item Name is not Selected. Please Select Item Name.");
//            return;
//
//        }
//        if((isNaN($this.val()) || $this.val() == '' || parseFloat($this.val())==NaN || parseFloat($this.val())<=0)&& $this.attr('class')!='form-width-bsp bsp-tab amount'){
//            alert("Amount is not Blank. Please Enter the Currect Amount.");
//            return;
//        }
//
//
//
//
//        $('tfoot').append('<style>tfoot:before{content: "-"; display: block; line-height:'+ (28-(index*2)) + 'em; color: transparent; position:relative; }</style>');
//        $('.group-list').append(
//            '<tr class="ledger">'+
//                '<td>'+
//                    '<select class="selectpicker form-control ledger" th:field="*{accountsVoucher[0].ledger}" onchange="ItemChange(this);" data-live-search="true">'+
//                    '<option value="">Select Ledgers</option>'+
//                    '</select>'+
//                '</td>'+
//                '<td colspan="2"></td>'+
//                '<td><input type="text" class="form-width-bsp bsp-tab percentage" style="border:1px #0000ff;"/></td>'+
//                '<td></td> ' +
//                '<td><input type="text" class="form-width-bsp bsp-tab par-amount" /></td>'+
//            '</tr>'
//        );
//        ajaxGetLedgers($('.group-list tr').find('select.ledger'),$('#companyCode').val());
//        $('.group-list tr').find('select.ledger').selectpicker('refresh');
//
//        $('.group-list tr').find('.par-amount').removeAttr('onkeyup');
//        //$this.parents('tr').next().find('.btn').focus();
//    }
//}

//Done
function ajaxGetItems(pItems,pCompanyCode) {
        $.ajax({
            type: "GET",
            url: window.location.protocol + "//" + window.location.host + "/bsp/accountingStockVouchers" + "/items/"+pCompanyCode,
            success: function (result) {
                //console.log(result);
                if (result.status == "Done") {
                    var vItems = "<option value=''>Select Items</option>";
                    $.each(result.data, function (i, item) {
                        vItems += "<option value=" + item.id + ">" + item.name + "</option>";
                    });
                    $(pItems).html(vItems);
                    $(pItems).selectpicker('refresh');
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
                alert("JQuery Error "+jqXHR.status+" [cm-mehdi manual error generate]"+msg);
            }
        });
}
//Done
function ajaxGetCodes(pItems,pCompanyCode) {
    $.ajax({
        type: "GET",
        url: window.location.protocol + "//" + window.location.host + "/bsp/accountingStockVouchers" + "/codes/"+pCompanyCode,
        success: function (result) {
            //console.log(result);
            if (result.status == "Done") {
                var vItems = "<option value=''>Select Codes</option>";
                $.each(result.data, function (i, item) {
                    vItems += "<option value=" + item.id + ">" + item.name + "</option>";
                });
                $(pItems).html(vItems);
                $(pItems).selectpicker('refresh');
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
//Done
function ajaxGetGodowns(pGodowns,pCompanyCode) {
    $.ajax({
        type: "GET",
        url: window.location.protocol + "//" + window.location.host + "/bsp/accountingStockVouchers" + "/godowns/"+pCompanyCode,
        success: function (result) {
            if (result.status == "Done") {
                var vGodowns = "<option value=''>Select Ledger</option>";
                $.each(result.data, function (i, ledger) {
                    vGodowns += "<option value=" + ledger.id + ">" + ledger.name + "</option>";
                });
                $(pGodowns).html(vGodowns);
                $(pGodowns).selectpicker('refresh');
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

function ajaxGetLedgers(pLedger,pCompanyCode){
    $.ajax({
        type: "GET",
        url: window.location.protocol + "//" + window.location.host + "/bsp/accountingStockVouchers" + "/ledgers/"+pCompanyCode,
        success: function (result) {
            console.log(result);
            if (result.status == "Done") {
                var vLedgers = "<option value=''>Select Additional Ledgers</option>";
                $.each(result.data, function (i, item) {
                    vLedgers += "<option value=" + item.id + ">" + item.name + "</option>";
                });
                $(pLedger).html(vLedgers);
                $(pLedger).selectpicker('refresh');
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
            alert("JQuery Error "+jqXHR.status+" [cm-mehdi manual error generate]"+msg);
        }
    });
}

//Done
function itemTable(evt,index){
    var $this = $(evt);
    var led = $this.parents('tr').find('select.name option:selected').val();
    var qty = $this.parents('tr').find('.qty').val();
    var rate = $this.parents('tr').find('.rate').val();
    var amount = $this.parents('tr').find('.amount').val();

    //region Checking requirement field
    //console.log("Item: "+led+" Qty: "+qty+" Rate: "+rate+" Amount: "+amount);
    if(led==""){
        alert("Item is not selected. Please Select the Item.");
        return;
    }
    else if(parseInt(qty)==0 || parseInt(qty)==NaN){
        alert("Quantity is not Enter. Please Entered the Quantity.");
        return;
    }
    else if (parseFloat(rate)==0 || parseFloat(rate)==NaN){
        alert("Rate is not Enter. Please Entered the Rate Amount.");
        return;
    }
    else if (parseFloat(amount)==0 || parseFloat(amount)==NaN || amount ==""){
        alert("Amount Field should not be blank.");
        return;
    }
    //endregion

    //region Row
    $('tbody.item-list').append(
        '<tr>'+
            '<td class="td-name">'+
                '<select class="selectpicker form-control-custome table-select-1 name" data-live-search="true" id="inventoryVoucher'+index+'.item.id" name="inventoryVoucher['+index+'].item.id" onchange="selectItemCode(this);">'+
                    '<option value="" >Select Item Name</option>'+
                '</select>'+
            '</td>'+
            '<td class="td-code">'+
                '<select class="selectpicker form-control-custome table-select-2 code" data-live-search="true" onchange="selectItemName(this);">'+
                    '<option value="" >Select Item Code</option>'+
                '</select>'+
            '</td>'+
            '<td class="td-narration">'+
                '<input type="text" id="inventoryVoucher'+index+'.narration" name="inventoryVoucher['+index+'].narration"  class="form-control form-control-custome form-width-bsp bsp-tab preventDefaultMe narration"/>'+
            '</td>'+
            '<td class="td-godwon">'+
                '<select class="selectpicker form-control-custome table-select-3 godown" id="inventoryVoucher'+index+'.godown.id" name="inventoryVoucher['+index+'].godown.id" data-live-search="true">'+
                    '<option value="">Select Godown</option>'+
                '</select>'+
            '</td>'+
            '<td class="td-qty">'+
                '<input type="text" class="form-control form-control-custome qty" id="inventoryVoucher'+index+'.quantity" name="inventoryVoucher['+index+'].quantity" value="0" onClick="this.select();" onkeyup="calculation(this);"/>'+
            '</td>'+
            '<td class="td-rate">'+
                '<input type="text" class="form-control form-control-custome rate" id="inventoryVoucher'+index+'.rate" name="inventoryVoucher['+index+'].rate" value="0" onClick="this.select();" onkeyup="calculation(this);"/>'+
            '</td>'+
            '<td class="td-unit">'+
                '<input type="text" class="form-control form-control-custome unit"/>'+
            '</td>'+
            '<td class="td-amount">'+
                '<input type="text" id="inventoryVoucher'+index+'.amount" class="form-control form-control-custome amount" readonly="readonly" name="inventoryVoucher['+index+'].amount" value="0" onkeyup="addRow(this);"/>'+
            '</td>'+
        '</tr>'
    );
    EachNarrationCol();
    GodownCol();

    //endregion

    ajaxGetItems($this.parents('tr').next().find('select.name'), $('#companyCode').val());
    ajaxGetCodes($this.parents('tr').next().find('select.code'),$('#companyCode').val());
    ajaxGetGodowns($this.parents('tr').next().find('select.godown'),$('#companyCode').val());
    $this.parents('tr').find('.amount').removeAttr('onkeyup');
    $this.parents('tr').next().find('select.name').focus();
}

//Done
function ledgerTable(evt,index){
    var $this = $(evt);
    var led = $this.parents('tr').find('select.name option:selected').val();
    var rate = $this.parents('tr').find('.rate').val();
    var amount = $this.parents('tr').find('.amount').val();

    //region Checking requirement field
    //console.log("Item: "+led+" Qty: "+qty+" Rate: "+rate+" Amount: "+amount);
    if(led==""){
        alert("Ledger is not selected. Please Select the Ledger.");
        return;
    }
    else if (parseFloat(rate)==0 || parseFloat(rate)==NaN){
        alert("Rate is not Enter. Please Entered the Rate Amount. Additional Ledger Table.");
        return;
    }
    else if (parseFloat(amount)==0 || parseFloat(amount)==NaN || amount ==""){
        alert("Amount Field should not be blank. Additional Table.");
        return;
    }
    //endregion

    //region Row
    $('tbody.ledger-list').append(
        '<tr>'+
            '<td class="td-name">'+
                '<select class="selectpicker form-control-custome table-select-1 name" id="accountsVoucher'+index+'.ledger.id" name="accountsVoucher['+index+'].ledger.id" data-live-search="true">'+
                    '<option value="" >Select Additional Ledger </option>'+
                '</select>'+
            '</td>'+
            '<td class="td-qty-table2">'+
                '<div class="qty-table2">'+
                '</div>'+
            '</td>'+
            '<td class="td-rate">'+
                '<input type="text" class="form-control form-control-custome rate" value="0.0" onclick="this.select();" onkeyup="calculation(this);"/>'+
            '</td>'+
            '<td class="td-unit">'+
                '<div class="unit">'+
                    '<input type="hidden" id="accountsVoucher'+index+'.creditAmount" name="accountsVoucher['+index+'].creditAmount" value="0.00" />'+
                '</div>'+
            '</td>'+
            '<td class="td-amount">'+
                '<input type="text" class="form-control form-control-custome amount" value="0.0" id="accountsVoucher'+index+'.debitAmount" name="accountsVoucher['+index+'].debitAmount" readonly="readonly"  onkeyup="addRow(this);"/>'+
            '</td>'+
        '</tr>'
    );

    //endregion

    ajaxGetLedgers($this.parents('tr').next().find('select.name'), $('#companyCode').val());

    $this.parents('tr').find('.amount').removeAttr('onkeyup');
    $this.parents('tr').next().find('select.name').focus();
}

//Done For Godown
function GodownCol(){
    if($('select#Godown option:selected').val()) {
        $('.item-list').find('select.godwon').val($('select#Godown option:selected').val()).selectpicker('refresh');
        $('.th-godwon').hide();
        $('.td-godwon').hide();
    }
    else{
        $('.item-list').find('select.godwon').val('default').selectpicker('refresh');
        $('.th-godwon').show();
        $('.td-godwon').show()
    }
}

//Done For Each Narration
function EachNarrationCol(){
    if($('#eachNarration').val()!="true") {
        $('.th-narration').hide();
        $('.td-narration').hide();
    }
    else{
        $('.th-narration').show();
        $('.td-narration').show()
    }
}

//Done
function checkValue(field){
    if (field.val().length>1 && field.val().slice(-1)!=".")
        field.val(parseFloat(field.val()));
    if (isNaN(parseFloat(field.val())))
        field.val("0");
}

//Done
function itemSubTotal(field){
    var $quantity = field.parents('tr').find('.qty');
    var $rate = field.parents('tr').find('.rate');
    var $amount = field.parents('tr').find('.amount');

    $amount.val(isNaN(parseInt($quantity.val())*parseFloat($rate.val()))?"0.00":parseInt($quantity.val())*parseFloat($rate.val()));

    var totalAmount=0.00;
    $('.item-list .amount').each(function (i, e) {
        totalAmount += parseFloat($(this).val());
    });
    $('.item-total input').val(totalAmount);
}

//Done
function ledgerSubTotal(field){
    var $rate = field.parents('tr').find('.rate');
    var $amount = field.parents('tr').find('.amount');

    $amount.val(isNaN(parseFloat($rate.val()))?"0.00":parseFloat($rate.val()));

    var totalAmount=0.00;
    $('.ledger-list .amount').each(function (i, e) {
        totalAmount += parseFloat($(this).val());
    });
    $('.ledger-total input').val(totalAmount);
}

//Done
function sumTotalAmount(){
    $('.td-all-total input').val(parseFloat($('.item-total input').val())+parseFloat($('.ledger-total input').val()));
}


function partyAcChangeEvent(evt){
    $('#party').val($(evt).parents('div').find('select#PartyAccountId option:selected').val());
}

function purchaseChangeEvent(evt){
    $('#purchase').val($(evt).parents('div').find('select#PurchaseLedgerId option:selected').val());
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
