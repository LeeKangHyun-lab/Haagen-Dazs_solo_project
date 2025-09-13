package kr.khlee.icecreamhaggendazs.controllers.main;

import kr.khlee.icecreamhaggendazs.helpers.PageHelper;
import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecommendedController {

    private final ProductService productService;

    /** 추천 상품 메인 (싱글/선물세트 소량) */
    @GetMapping("/recommended")
    public String recommendedProducts(Model model) {
        try {
            List<Product> singleProducts = productService.SelectSingle();
            List<Product> giftProducts = productService.SelectGift();

            model.addAttribute("singleProducts", singleProducts);
            model.addAttribute("giftProducts", giftProducts);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "추천 상품을 불러오는 중 오류가 발생했습니다.");
            model.addAttribute("singleProducts", Collections.emptyList());
            model.addAttribute("giftProducts", Collections.emptyList());
        }

        return "recommended/recommended";
    }

    /** 싱글류 전체 */
    @GetMapping("/recommended_single")
    public String recommendedSingleAll(Model model,
                                       @RequestParam(value = "page", defaultValue = "1") int nowPage) {
        try {
            int listCount = 16;   // 페이지당 상품 수
            int groupCount = 5;   // 페이징 네비 수
            int totalCount = productService.getAllSingleCount();

            PageHelper pageHelper = new PageHelper(nowPage, totalCount, listCount, groupCount);

            List<Product> singleProducts =
                    productService.SelectSingleAll(pageHelper.getOffset(), pageHelper.getListCount());

            model.addAttribute("singleProducts", singleProducts);
            model.addAttribute("pageHelper", pageHelper);

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("singleProducts", Collections.emptyList());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "싱글 상품을 불러오는 중 오류가 발생했습니다.");
            model.addAttribute("singleProducts", Collections.emptyList());
        }

        return "recommended/recommended_single";
    }

    /** 선물세트 전체 */
    @GetMapping("/recommended_gift")
    public String recommendedGiftAll(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") int nowPage) {
        try {
            int listCount = 16;
            int groupCount = 5;
            int totalCount = productService.getAllGiftCount();

            PageHelper pageHelper = new PageHelper(nowPage, totalCount, listCount, groupCount);

            List<Product> giftProducts =
                    productService.SelectGiftAll(pageHelper.getOffset(), pageHelper.getListCount());

            model.addAttribute("giftProducts", giftProducts);
            model.addAttribute("pageHelper", pageHelper);

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("giftProducts", Collections.emptyList());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "선물세트 상품을 불러오는 중 오류가 발생했습니다.");
            model.addAttribute("giftProducts", Collections.emptyList());
        }

        return "recommended/recommended_gift";
    }
}
