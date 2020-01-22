package bsp.controller.menu.upHeader.user;

import bsp.config.ConfigAuth;
import bsp.model.AuthUser;
import bsp.model.Company;
import bsp.model.CompanyEntity;
import bsp.model.Message;
import bsp.service.AuthUserService;
import bsp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 30-April-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private ConfigAuth configAuth;

    private void bspGetUsers(Model model, HttpSession bspSession) {
            List<AuthUser> authUsers = authUserService.findAll();
            model.addAttribute("UserList",authUsers);
            model.addAttribute("companies", bspSession.getAttribute("sCompany"));
    }

    @GetMapping(value = {"", "/", "/index"})
    public String index(Model model, HttpSession bspSession) {
        bspGetUsers(model, bspSession);
        return "menu/upHeader/user/index";
    }

    @GetMapping(value = "/create")
    public String create(Model model, HttpSession bspSession) {
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        model.addAttribute("AuthUser", new AuthUser());
        model.addAttribute("mode", "create");
        Message("",model);
        return "menu/upHeader/user/form";
    }

    @PostMapping(value = "/create")
    public String save(@ModelAttribute("AuthUser") @Valid AuthUser obj, BindingResult bindingResult, Model model, HttpSession bspSession) {
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        obj = SetDefault(obj, 1);
        obj.setPassword(this.configAuth.bCryptPasswordEncoder().encode(obj.getPassword()));
        if (!bindingResult.hasErrors()) {
            try {
                AuthUser authUser = this.authUserService.save(obj);
                return "redirect:/user";
            } catch (Exception ex) {
                Message(ex.getCause().getCause().getMessage(),model);
                model.addAttribute("AuthUser", obj);
                model.addAttribute("mode", "create");
                return "menu/upHeader/user/form";
            }
        }
        return "";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable BigInteger id, Model model, HttpSession bspSession) {//
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        try {
            AuthUser authUser=this.authUserService.findById(id);
            model.addAttribute("AuthUser", authUser);
            model.addAttribute("mode", "edit");
            Message("",model);
            return "menu/upHeader/user/form";

        } catch (Exception ex) {
            model.addAttribute("AuthUser", new AuthUser());
            model.addAttribute("mode", "edit");
            Message(ex.getCause().getCause().getMessage(),model);
            return "redirect:/menu/upHeader/user/form/";
        }
    }

    @PostMapping(value = "/edit")
    public String update(@ModelAttribute("AuthUser") @Valid AuthUser obj, BindingResult bindingResult, Model model, HttpSession bspSession) {
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
        obj.setPassword(this.configAuth.bCryptPasswordEncoder().encode(obj.getPassword()));
        if (!bindingResult.hasErrors()) {
            try {
                AuthUser authUser = this.authUserService.save(obj);
                return "redirect:/user/";
            } catch (Exception ex) {
                Message(ex.getCause().getCause().getMessage(),model);
                model.addAttribute("AuthUser", obj);
                model.addAttribute("mode", "edit");
            }
        }
        return "menu/upHeader/user/form";
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable BigInteger id, Model model, HttpSession bspSession) {//, BindingResult bindingResult
        try {
            this.authUserService.delete(id);
            return "redirect:/user";
        } catch (Exception ex) {
            model.addAttribute("companies", bspSession.getAttribute("sCompany"));
            Message(ex.getCause().getCause().getMessage(),model);
            return "redirect:/user";
        }
    }

    //region All Methods

    private void Message(String msg, Model model){
        Message message = new Message();
        message.setDisplay(msg!=""?true:false);
        message.setMessage(msg);
        model.addAttribute("msg",message);
    }

    private AuthUser SetDefault(AuthUser authUser, int action){
        authUser.setAccountExpired(false);
        authUser.setAccountLocked(false);
        authUser.setCredentialsExpired(false);
        authUser.setEnabled(true);
        return authUser;
    }

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    //endregion
}