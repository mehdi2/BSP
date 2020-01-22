package bsp.controller.menu.upHeader.company;

import bsp.model.Company;
import bsp.model.CompanyEntity;
import bsp.model.Message;
import bsp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 10-March-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    private void bspGetReq(Model model, HttpSession bspSession) {
            List<Company> companies = companyService.findAll();
            bspSession.setAttribute("sCompany",companies);
            model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        }

    private void bspGetMsg(Model model, boolean yes, String message){
        Message msg = new Message();
        msg.setDisplay(yes);
        msg.setMessage(message);
        model.addAttribute("msg",msg);
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model, HttpSession bspSession) {
        bspGetReq(model, bspSession);
        return "menu/upHeader/company/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model, HttpSession bspSession) {
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        model.addAttribute("company", new Company());
        model.addAttribute("mode", "create");
        bspGetMsg(model, false, null);
        return "menu/upHeader/company/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("Company") @Valid Company obj, BindingResult bindingResult, Model model, HttpSession bspSession) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        obj.setActionDate(timestamp);
        if (!bindingResult.hasErrors()) {
            try {
                Company company = this.companyService.save(obj);
                return "redirect:/company";
            } catch (Exception ex) {
                System.out.println("err: "+ex.getCause().getCause().getMessage());
                model.addAttribute("companies", bspSession.getAttribute("sCompany"));
                bspGetMsg(model, true, ex.getCause().getCause().getMessage());
                model.addAttribute("company", obj);
                model.addAttribute("mode", "create");
            }
        }
        return "menu/upHeader/company/form";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable int id, Model model, HttpSession bspSession) {//
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        try {
            Company company=this.companyService.findById(id);
            model.addAttribute("company", company);
            model.addAttribute("mode", "edit");
            bspGetMsg(model, false, null);
            return "menu/upHeader/company/form";

        } catch (Exception ex) {
            model.addAttribute("company", new Company());
            model.addAttribute("mode", "edit");
            bspGetMsg(model, true, ex.getCause().getCause().getMessage());
            return "redirect:/menu/upHeader/company/form/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("Company") @Valid Company obj, BindingResult bindingResult, Model model, HttpSession bspSession) {
        if (!bindingResult.hasErrors()) {
            try {
                Company company = this.companyService.save(obj);
                return "redirect:/company/";
            } catch (Exception ex) {
                model.addAttribute("companies", bspSession.getAttribute("sCompany"));
                System.out.println("err: "+ex.getCause().getCause().getMessage());
                bspGetMsg(model, true, ex.getCause().getCause().getMessage());
                model.addAttribute("company", obj);
                model.addAttribute("mode", "edit");
            }
        }

        return "menu/upHeader/company/form";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable int id, Model model, HttpSession bspSession) {//, BindingResult bindingResult
        try {
            this.companyService.delete(id);
            return "redirect:/company";
        } catch (Exception ex) {
            bspGetReq(model,bspSession);
            bspGetMsg(model, true, ex.getCause().getCause().getMessage());
            return "redirect:/company";
        }
    }
}