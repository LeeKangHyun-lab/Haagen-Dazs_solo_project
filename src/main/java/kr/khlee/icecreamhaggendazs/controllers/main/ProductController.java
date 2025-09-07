package kr.khlee.icecreamhaggendazs.controllers.main;

import kr.khlee.icecreamhaggendazs.helpers.PageHelper;
import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ProductController {

    @Autowired
    private ProductService  productService;

    @GetMapping({"/products", "/products/{category}"})
    public String productsPage(Model model,
                               @PathVariable(value = "category", required = false) String category,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", defaultValue = "1") int nowPage) throws Exception {

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
            products = productService.SelectByCategory(category, pageHelper.getOffset(),  pageHelper.getListCount());
        } else {
            products = productService.SelectAllProducts(pageHelper.getOffset(), pageHelper.getListCount());

        }

        model.addAttribute("products", products);
        model.addAttribute("category", categoryName);   // 타이틀/탭 표시용
        model.addAttribute("pageHelper", pageHelper);   // 페이징 데이터
        model.addAttribute("keyword", keyword);         // 검색 유지용
        model.addAttribute("categoryId", category);     // 페이징 링크에 필요

        return "products/products";
    }

    @GetMapping("/products/products_detail/{id}")
    public String productsDetailPage(Model model,
                                     @PathVariable (value = "id", required = false) int id) {

        Product product = productService.SelectById(id);

        model.addAttribute("product", product);

        return "products/products_detail";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {

        List<Product> products = productService.SearchProducts(keyword);

        model.addAttribute("products", products);

        return "products/search";
    }
}
