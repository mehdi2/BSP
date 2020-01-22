package bsp.controller.masters.inventoryInfo;

import bsp.model.Godown;
import bsp.model.Item;
import bsp.model.ItemDetail;
import bsp.service.GodownService;
import bsp.service.GroupService;
import bsp.service.ItemService;
import bsp.service.UnitService;
import bsp.supplemental.Collective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 17-April-18.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("item")
public class ItemController extends Collective {
    @Autowired
    private ItemService itemService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private GodownService godownService;

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        List<Item> items = this.itemService.findByCompanyCodeAndIsServiceAndIsLogOrderByNameAsc(278,false,false);
        model.addAttribute("items", items);
        getCompany(model);
        Message("",model);
        return "masters/inventoryInfo/item/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        SetCommon(new Item(),"create","",model);
        return "masters/inventoryInfo/item/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("item") @Valid Item obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1,false);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.itemService.save(setItem(obj));
                else{
                    SetCommon(obj,"create","Duplicate Item is Not Save. Please Try Again.", model);
                    return "masters/inventoryInfo/item/form";
                }
                return "redirect:/item/";
            } catch (Exception ex) {
                SetCommon(obj,"create",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/item/form";
            }
        }
        return "masters/inventoryInfo/item";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {
        try {
            SetCommon(itemService.findById(id),"edit","",model);
            return "masters/inventoryInfo/item/form";

        } catch (Exception e) {
            return "redirect:/masters/inventoryInfo/unit/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("item") @Valid Item obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            SetCommon(obj,"edit","Duplicate Item is Not Save. Please Try Again.",model);
            return "masters/inventoryInfo/item/form";
        }
        //endregion
        obj = SetDefault(obj,2,false);
        if (!bindingResult.hasErrors()) {
            try {
                this.itemService.save(setLogged(this.itemService.findById(obj.getId())));   //Insert
                this.itemService.save(setId(obj));
                return "redirect:/item/";
            } catch (Exception ex) {
                SetCommon(obj,"edit",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/item/form";
            }
        }
        return "item/form";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model) {
        try {
            Item item = itemService.findById(id);
            itemService.save(setLogged(item));  //Insert
            itemService.save(SetDefault(item, 3, false));    //Update
            Message("",model);
            return "redirect:/item";
        } catch (Exception ex) {
            index(model);
            Message(ex.getCause().getCause().getMessage(),model);
            return "masters/inventoryInfo/item/index";
        }
    }


    //region Default Method
    private Item SetDefault(Item item, int action, boolean isService){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        item.setActionDate(timestamp);
        item.setActionType(action);
        item.setCompanyCode(278);
        item.setUserCode(new BigInteger("1"));
        item.setService(isService);
        item.setActive(true);
        item.setLog(action==3?true:false);
//        if (action==1 ){
            Item obj =item;
            item.getItemDetails().forEach(itemDetail-> itemDetail.setItem(obj));
//        }
        return item;
    }

    private void SetCommon(Item item, String action, String message, Model model){
        if(item.getItemDetails()==null || item.getItemDetails().size()==0){
            List<ItemDetail> itemDetails = new ArrayList<ItemDetail>();
            itemDetails.add(new ItemDetail());
            item.setItemDetails(itemDetails);
        }
        model.addAttribute("item", item);

        model.addAttribute("groups",groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,2,false));
        model.addAttribute("units",unitService.findByCompanyCodeAndIsLogFalseOrderByUnitSymbolAsc(278));
        model.addAttribute("mode", action);

        //region Godown
        List<Godown> godowns = this.godownService.findByCompanyCodeAndIsLogFalseOrderByNameAsc(278);
        model.addAttribute("godowns",godowns);
        if (godowns.size()>1)
            model.addAttribute("isMultiGodown", true);
        else
            model.addAttribute("isMultiGodown", false);
        //endregion

        getCompany(model);
        Message(message,model);
    }

    //Duplicate Data Check
    private boolean isReplica(int companyCode,String name,BigInteger id){
        Item item = itemService.findByCompanyCodeAndNameAndIsServiceAndIsLogFalse(companyCode,name,false);
        if(item!=null && id!=null)
            if(item.getId().compareTo(id)==0)
                return false;
        if (item==null)
            return false;
        else
            return true;
    }

    private Item setLogged(Item item){
        Item logged = new Item();

        logged.setActionDate(item.getActionDate());
        logged.setActionType(item.getActionType());
        logged.setCompanyCode(item.getCompanyCode());
        logged.setUserCode(item.getUserCode());

        logged.setCode(item.getCode());
        logged.setName(item.getName());
        logged.setGroup(item.getGroup());
        logged.setUnit(item.getUnit());
        logged.setService(item.isService());
        logged.setActive(item.isActive());
//
        List<ItemDetail> itemDetails = new ArrayList<ItemDetail>();
        for (ItemDetail itemDetail: item.getItemDetails()  ) {
            ItemDetail itemDit=new ItemDetail();
            itemDit.setGodown(itemDetail.getGodown());
            itemDit.setItem(logged);
            itemDit.setQuantity(itemDetail.getQuantity());
            itemDit.setRate(itemDetail.getRate());
            itemDit.setAmount(itemDetail.getAmount());
            itemDetails.add(itemDit);
        }
        logged.setItemDetails(itemDetails);

        logged.setLog(true); ////

        return logged;
    }

    private Item setItem(Item item){
        Item obj =item;
        item.getItemDetails().forEach(itemDetail-> itemDetail.setItem(obj));
        return item;
    }

    private  Item setId(Item item){
        Item obj = this.itemService.findById(item.getId());
        for (int i=0;i<item.getItemDetails().size();i++) {
            item.getItemDetails().get(i).setId(obj.getItemDetails().get(i).getId());
        }
        return item;
    }
    //endregion

}