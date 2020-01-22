package bsp.controller;

import bsp.model.Company;
import bsp.service.CompanyService;
import bsp.service.VoucherTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class Home {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private VoucherTypeService voucherTypeService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    @RequestMapping(value = {"/", "/main"}, method = RequestMethod.GET)
    public String welcome(Model model, HttpSession bspSession) {
        List<Company> companies = companyService.findAll();
        bspSession.setAttribute("sCompany",companies);
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
//        model.addAttribute("AcVoucherTypes",this.voucherTypeService.findByCompanyCodeAndIsActiveOrderByNameAsc(1,true));
        return "main";
    }
}
