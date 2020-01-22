package bsp.controller.masters.inventoryInfo;

import bsp.model.Group;
import bsp.service.GroupService;
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
@RequestMapping("stockGroup")
public class StockGroupController extends Collective{
    @Autowired
    private GroupService groupService;

    private List<BigInteger> ids=new ArrayList<BigInteger>();

    private void getGroups(Model model) {
        List<Group> groups = new ArrayList<Group>();
        groups.add(setPrimary());
        groups.addAll(this.groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,2,false));
        model.addAttribute("InvGroupList", groups);
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model) {
        List<Group> groups = this.groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,2,false);
        groups.forEach(group -> group.setUnder(group.getParentCode().compareTo(new BigInteger("0"))==0?"Primary":this.groupService.findById(group.getParentCode()).getName()));
        model.addAttribute("InvGroupList",groups);
        getCompany(model);
        if (!model.asMap().containsKey("msg"))
            Message("",model);
        return "masters/inventoryInfo/group/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        setCommonValue(new Group(),"create","",model);
        return "masters/inventoryInfo/group/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("InvGroup") @Valid Group obj, BindingResult bindingResult, Model model) {
        obj = SetDefault(obj,1);
        if (!bindingResult.hasErrors()) {
            try {
                if(!isReplica(278,obj.getName(),obj.getId()))
                    this.groupService.save(obj);
                else{
                    setCommonValue(obj,"create","Duplicate Group is Not Save. Please Try Again.", model);
                    return "masters/inventoryInfo/group/form";
                }
                return "redirect:/stockGroup/";
            }catch (Exception ex) {
                setCommonValue(obj,"create",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/group/form";
            }
        }
        return "";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model) {//
        try {
            setCommonValue(this.groupService.findById(id),"edit","",model);
            return "masters/inventoryInfo/group/form";

        } catch (Exception e) {
            return "redirect:/masters/inventoryInfo/group/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("InvGroup") @Valid Group obj, BindingResult bindingResult, Model model) {
        //region Replica/Duplicate Check
        if (isReplica(278, obj.getName(), obj.getId())) {
            setCommonValue(obj,"edit","Duplicate Group is Not Save. Please Try Again.",model);
            return "masters/inventoryInfo/group/form";
        }
        //endregion
        obj = SetDefault(obj, 2);
        if (!bindingResult.hasErrors()) {
            try {
                this.groupService.save(setLogged(this.groupService.findById(obj.getId())));   //Insert
                this.groupService.save(obj);                                                  //Update
                return "redirect:/stockGroup/";
            } catch (Exception ex) {
                setCommonValue(obj,"edit",ex.getCause().getCause().getMessage(),model);
                return "masters/inventoryInfo/group/form";
            }
        }
    return "";
}

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model) {
        try {
            if(!isChild(id)) {
                Group group = this.groupService.findById(id);
                this.groupService.save(setLogged(group));       //Insert
                this.groupService.save(SetDefault(group, 3));   //Update
                index(model);
            }
            else {
                Message("Message: Have a Child For This Group.", model);
                index(model);
            }
            return "masters/inventoryInfo/group/index";
        } catch (Exception ex) {
            index(model);
            Message(ex.getCause().getCause().getMessage(),model);
            return "masters/inventoryInfo/group/index";  //"redirect:/stockGroup"
        }
    }

    //region Default Method
        private Group SetDefault(Group group, int action){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            group.setActionDate(timestamp);
            group.setCompanyCode(278);
            group.setUserCode(new BigInteger("1"));
            group.setActionType(action);
            group.setIndexing(0);
            group.setType(2);
            group.setLog(action==3?true:false);
            return group;
        }

        private void setCommonValue(Group group, String action, String message, Model model){
            model.addAttribute("InvGroup", group);
            if(action=="edit") {
                model.addAttribute("InvGroupList", getParentList(group.getId()));
            }
            else{
                List<Group> groups = new ArrayList<Group>();
                groups.add(setPrimary());
                groups.addAll(this.groupService.findByCompanyCodeAndTypeAndIsLogOrderByNameAsc(278,2,false));
                model.addAttribute("InvGroupList", groups);
            }
            model.addAttribute("mode", action);
            getCompany(model);
            Message(message,model);
        }

        //Remove Child From CostCenter
        private List<Group> getParentList(BigInteger id){
            List<Group> groups = new ArrayList<Group>();
            groups.add(setPrimary());

            ids.clear();
            ids.add(id);
            RemoveFunc(id);

            groups.addAll(this.groupService.findByCompanyCodeAndTypeAndIsLogFalseAndIdNotIn(278,2,ids));

            return groups;
        }

        //Under Group Name Remove
        private void RemoveFunc(BigInteger id){
            List<Group> groups = this.groupService.findByParentCodeAndIsLog(id,false);
            if (!groups.isEmpty()){
                for (Group group:groups) {
                    ids.add(group.getId());
                    RemoveFunc(group.getId());
                }
            }
        }

        private Group setPrimary(){  //GodownList
            Group group=new Group();
            group.setId(new BigInteger("0"));
            group.setName("Primary");
            return group;
        }

        //Duplicate Data Check
        private boolean isReplica(int companyCode,String name,BigInteger id){
            Group group = groupService.findByCompanyCodeAndTypeAndNameAndIsLogFalse(companyCode,2,name);
            if(group!=null && id!=null)
                if(group.getId().compareTo(id)==0)
                    return false;
            if (group==null)
                return false;
            else
                return true;
        }

        private Group setLogged(Group group){
            Group logged = new Group();

            logged.setActionDate(group.getActionDate());
            logged.setActionType(group.getActionType());
            logged.setCompanyCode(group.getCompanyCode());
            logged.setUserCode(group.getUserCode());

            logged.setIndexing(group.getIndexing());
            logged.setType(group.getType());
            logged.setCode(group.getCode());
            logged.setName(group.getName());
            logged.setParentCode(group.getParentCode());
            logged.setPrimaryCode(group.getPrimaryCode());
            logged.setLog(true); ////

            return logged;
        }

        private boolean isChild(BigInteger id){
            List<Group> groupList = this.groupService.findByParentCodeAndIsLog(id,false);
            return (groupList.size()>0)?true:false;
        }
    //endregion




}