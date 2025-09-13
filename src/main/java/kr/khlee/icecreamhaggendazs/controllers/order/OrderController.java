package kr.khlee.icecreamhaggendazs.controllers.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/order/ready")
    public String orderReadyPage() {
        return "order/ready";
    }
}
