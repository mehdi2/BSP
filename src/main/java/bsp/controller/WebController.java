package bsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Chowdhury Muhammad Mehdi Hasan on 5-May-2018.
 * Mobile: +88 01711972985
 * Email: mehdi.uiu@gmail.com
 * Skype: cm_mehdi
 */

@Controller
public class WebController {

    @RequestMapping(value={"/","home"})
    public String home(){
        return "home";
    }

    @RequestMapping(value="/user")
    public String user(){
        return "user";
    }

    @RequestMapping(value="/admin")
    public String admin(){
        return "admin";
    }

    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }
}
