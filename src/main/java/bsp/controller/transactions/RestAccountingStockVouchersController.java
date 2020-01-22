package bsp.controller.transactions;

import bsp.supplemental.Response;
import bsp.model.*;
import bsp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 27-July-2017.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@RestController
@RequestMapping("accountingStockVouchers")
public class RestAccountingStockVouchersController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private GodownService godownService;

    @Autowired
    private LedgerService ledgerService;
//-----------------------------------------------------------------------------------------

    //For when Item Change then effect Unit Name
    @GetMapping(value = {"/unit/{itemId}"})
    public Response getUnit(@PathVariable BigInteger itemId) {
        try {
            Item item = itemService.findById(itemId);
            Response response = new Response("Done",item.getUnit().getUnitSymbol());
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = {"/items/{companyCode}"})
    public Response getItem(@PathVariable int companyCode) {
        try {
            List<Item> items = itemService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
            List<DropDown> dropDowns =new ArrayList<DropDown>();
            for (Item item:items) {
                DropDown dropDown=new DropDown();
                dropDown.setId(item.getId());
                dropDown.setName(item.getName());
                dropDowns.add(dropDown);
            }
            Response response = new Response("Done",dropDowns);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    //Will be Delete. it is Depended for getItem Table
    @GetMapping(value = {"/codes/{companyCode}"})
    public Response getCode(@PathVariable int companyCode) {
        try {
            List<Item> items = itemService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
            List<DropDown> dropDowns =new ArrayList<DropDown>();
            for (Item item:items) {
                DropDown dropDown=new DropDown();
                dropDown.setId(item.getId());
                dropDown.setName(item.getCode());
                dropDowns.add(dropDown);
            }
            Response response = new Response("Done",dropDowns);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = {"/godowns/{companyCode}"})
    public Response getGodowns(@PathVariable int companyCode) {
        try {
            List<Godown> godowns = this.godownService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
            Response response = new Response("Done",godowns);
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = {"/ledgers/{companyCode}"})
    public Response getLedger(@PathVariable int companyCode) {
        try {
            List<Ledger> ledgers = ledgerService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(companyCode);
            Response response = new Response("Done",ledgers);
            return response;
        } catch (Exception e) {
            return null;
        }
    }


}
