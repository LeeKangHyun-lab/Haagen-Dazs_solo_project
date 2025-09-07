package kr.khlee.icecreamhaggendazs.controllers.account;

import kr.khlee.icecreamhaggendazs.helpers.SessionCheckHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping({"/account"})
    public String index(){
        return "account/index";
    }


    @GetMapping("/account/join")
    public String join(){
        return "account/join";
    }

    @GetMapping("/account/join_result")
    @SessionCheckHelper(enable = false)
    public String joinResult(){
        return "account/join_result";
    }

    @GetMapping("/account/login")
    @SessionCheckHelper(enable = false)
    public String login(){
        return "account/login";
    }

    @GetMapping("/account/find_id")
    @SessionCheckHelper(enable = false)
    public String findId(){
        return "account/find_id";
    }

    @GetMapping("/account/reset_pw")
    @SessionCheckHelper(enable = false)
    public String resetPw(){
        return "account/reset_pw";
    }

    @GetMapping("/account/edit")
    @SessionCheckHelper(enable = true)
    public String edit(){
        return "account/edit";
    }

    @GetMapping("/account/out")
    @SessionCheckHelper(enable = true)
        public String out(){
        return "account/out";
        }
}
