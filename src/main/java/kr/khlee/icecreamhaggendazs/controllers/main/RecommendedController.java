package kr.khlee.icecreamhaggendazs.controllers.main;

import kr.khlee.icecreamhaggendazs.helpers.PageHelper;
import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String recommendedSingleAll(Model model,
                                       @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        int listCount = 16;   // 페이지당 상품 수
        int groupCount = 5;   // 페이징 네비 수
        int totalCount = productService.getAllSingleCount();

        PageHelper pageHelper = new PageHelper(nowPage, totalCount, listCount, groupCount);

        List<Product> singleProducts =
                productService.SelectSingleAll(pageHelper.getOffset(), pageHelper.getListCount());

        model.addAttribute("singleProducts", singleProducts);
        model.addAttribute("pageHelper", pageHelper);

        return "recommended/recommended_single";
    }

    @GetMapping("/recommended_gift")
    public String recommendedGiftAll(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        int listCount = 16;
        int groupCount = 5;
        int totalCount = productService.getAllGiftCount();

        PageHelper pageHelper = new PageHelper(nowPage, totalCount, listCount, groupCount);

        List<Product> giftProducts =
                productService.SelectGiftAll(pageHelper.getOffset(), pageHelper.getListCount());

        model.addAttribute("giftProducts", giftProducts);
        model.addAttribute("pageHelper", pageHelper);

        return "recommended/recommended_gift";
    }

}
