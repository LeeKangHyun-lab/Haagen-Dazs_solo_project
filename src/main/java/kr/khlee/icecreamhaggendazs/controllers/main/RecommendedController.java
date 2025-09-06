package kr.khlee.icecreamhaggendazs.controllers.main;

import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecommendedController {

    @Autowired
    private ProductService productService;

    @GetMapping("/recommended")
    public String recommendedProducts(Model model) {
        List<Product> singleProducts = productService.SelectSingle();
        List<Product> giftProducts = productService.SelectGift();

        model.addAttribute("singleProducts", singleProducts);
        model.addAttribute("giftProducts", giftProducts);

        return "recommended/recommended";
    }

    @GetMapping("/recommended_single")
    public String recommendedSingleAll(Model model) {
        List<Product> singleProducts = productService.SelectSingleAll();

        model.addAttribute("singleProducts", singleProducts);

        return "recommended/recommended_single";

    }

    @GetMapping("/recommended_gift")
    public String recommendedGiftAll(Model model) {
        List<Product> giftProducts = productService.SelectGiftAll();

        model.addAttribute("giftProducts", giftProducts);

        return "recommended/recommended_gift";

    }
}
