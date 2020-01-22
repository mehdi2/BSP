package bsp.controller.masters.inventoryInfo;

import bsp.model.Item;
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
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 17-April-18.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("service")
public class ServiceController extends Collective{
    @Autowired
    private ItemService itemService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UnitService unitService;

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        List<Item> items = this.itemService.findByCompanyCodeAndIsServiceAndIsLogOrderByNameAsc(278,true,false);
        model.addAttribute("items", items);
        getCompany(model);
        Message("",model);
        return "masters/inventoryInfo/service/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        setCommon(new Item(),"create","",model);
        return "masters/inventoryInfo/Service/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("item") @Valid Item obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.itemService.save(obj);
                else{
                    setCommon(obj,"create","Duplicate Service is Not Save. Please Try Again.", model);
                    return "masters/inventoryInfo/service/form";
                }
                return "redirect:/service";
            } catch (Exception ex) {
                setCommon(obj,"create",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/service/form";
            }
        }
        return "masters/inventoryInfo/service";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {
        try {
            setCommon(itemService.findById(id),"edit","",model);
            return "masters/inventoryInfo/service/form";
        } catch (Exception e) {
            return "redirect:/masters/inventoryInfo/service/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("item") @Valid Item obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            setCommon(obj,"edit","Duplicate Service is Not Save. Please Try Again.",model);
            return "masters/inventoryInfo/service/form";
        }
        //endregion
        obj = SetDefault(obj,2);
        if (!bindingResult.hasErrors()) {
            try {
                this.itemService.save(setLogged(this.itemService.findById(obj.getId())));   //Insert
                this.itemService.save(obj);
                return "redirect:/service";
            } catch (Exception ex) {
                setCommon(obj,"edit",ex.getCause().getCause().getMessage(),model);
                return "redirect:/service/form";
            }
        }
        return "service/form";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model) {
        try {
            Item item = this.itemService.findById(id);
            this.itemService.save(setLogged(item));  //Insert
            this.itemService.save(SetDefault(item, 3));    //Update
            index(model);
            return "redirect:/service";
        } catch (Exception ex) {
            index(model);
            Message(ex.getCause().getCause().getMessage(),model);
            return "masters/inventoryInfo/service/index";
        }
    }

    //region Default Method
        private Item SetDefault(Item item, int action){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            item.setActionDate(timestamp);
            item.setActionType(action);
            item.setCompanyCode(278);
            item.setUserCode(new BigInteger("1"));
            item.setService(true);
            item.setActive(true);
            item.setLog(action==3?true:false);
            return item;
        }

        private void setCommon(Item item,String Action,String message,Model model){
        model.addAttribute("item", item);
        model.addAttribute("groups",groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,2,false));
        model.addAttribute("units",unitService.findByCompanyCodeAndIsLogFalseOrderByUnitSymbolAsc(278));
        model.addAttribute("mode", Action);
        getCompany(model);
        Message(message,model);
    }

        //Duplicate Data Check
        private boolean isReplica(int companyCode,String name,BigInteger id){
            Item item = itemService.findByCompanyCodeAndNameAndIsServiceAndIsLogFalse(companyCode,name,true);
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
            logged.setLog(true); ////

            return logged;
        }
    //endregion

}