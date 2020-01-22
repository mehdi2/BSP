package bsp.supplemental;

import bsp.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 25-Sep-18.
 */
public class Collective {
    @Autowired
    private HttpSession bspSession;

    public void getCompany(Model model){
        model.addAttribute("companies", bspSession.getAttribute("sCompany"));
    }

    public String PrimaryGroup(int primary){
        switch (primary){
            case 0:
                return "Primary";
            case 1:
                return "Asset (Primary)";
            case 2:
                return "Liability (Primary)";
            case 3:
                return "Income (Primary)";
            case 4:
                return "Expense (Primary)";
            default :
                return "";
        }
    }

    public void Message(String msg, Model model){
        Message message = new Message();
        message.setDisplay(msg!=""?true:false);
        message.setMessage(msg);
        model.addAttribute("msg",message);
    }

}
