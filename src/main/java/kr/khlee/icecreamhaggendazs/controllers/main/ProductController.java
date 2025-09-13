package kr.khlee.icecreamhaggendazs.controllers.main;

import kr.khlee.icecreamhaggendazs.helpers.PageHelper;
import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /** 상품 목록 페이지 */
    @GetMapping({"/products", "/products/{category}"})
    public String productsPage(Model model,
                               @PathVariable(value = "category", required = false) String category,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", defaultValue = "1") int nowPage) {

        try {
            List<Product> categories = productService.getAllCategories();
            String categoryName = "ALL PRODUCTS";

            if (category != null) {
                for (Product c : categories) {
                    if (c.getCategory().equals(category)) {
                        categoryName = c.getCategory();
                        break;
                    }
                }
            }

            int listCount = 16;   // 한 페이지당 상품 수
            int groupCount = 5;   // 페이징 네비게이션 개수
            int totalCount = productService.getProductCount(category);

            PageHelper pageHelper = new PageHelper(nowPage, totalCount, listCount, groupCount);

            Product.setOffset(pageHelper.getOffset());
            Product.setListCount(pageHelper.getListCount());

            List<Product> products;
            if (keyword != null && !keyword.trim().isEmpty()) {
                products = productService.SearchProducts(keyword);
            } else if (category != null && !category.trim().isEmpty()) {
                products = productService.SelectByCategory(category, pageHelper.getOffset(), pageHelper.getListCount());
            } else {
                products = productService.SelectAllProducts(pageHelper.getOffset(), pageHelper.getListCount());
            }

            model.addAttribute("products", products);
            model.addAttribute("category", categoryName);
            model.addAttribute("pageHelper", pageHelper);
            model.addAttribute("keyword", keyword);
            model.addAttribute("categoryId", category);

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("products", Collections.emptyList());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품을 불러오는 중 오류가 발생했습니다.");
            model.addAttribute("products", Collections.emptyList());
        }

        return "products/products";
    }

    /** 상품 상세 페이지 */
    @GetMapping("/products/products_detail/{id}")
    public String productsDetailPage(Model model,
                                     @PathVariable("id") int id) {
        try {
            Product product = productService.SelectById(id);
            model.addAttribute("product", product);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/404"; // 상품 못 찾았을 때 별도 에러 페이지
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 정보를 불러오는 중 오류가 발생했습니다.");
            return "error/500";
        }

        return "products/products_detail";
    }

    /** 상품 검색 */
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        try {
            List<Product> products = productService.SearchProducts(keyword);
            model.addAttribute("products", products);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "검색 중 오류가 발생했습니다.");
            model.addAttribute("products", Collections.emptyList());
        }

        return "products/search";
    }
}
