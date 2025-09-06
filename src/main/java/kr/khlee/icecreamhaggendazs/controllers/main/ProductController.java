package kr.khlee.icecreamhaggendazs.controllers.main;

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

    @GetMapping("/products")
    public String productsPage(Model model) {

        List<Product> products = productService.SelectAllProducts();

        model.addAttribute("products", products);
        return "products/products";
    }

    @GetMapping("/products/{category}")
    public String productsByCategory(@PathVariable("category") String category, Model model) {
        List<Product> products = productService.SelectByCategory(category);

        model.addAttribute("products", products);
        model.addAttribute("category", category); // 뷰에서 타이틀/탭 표시용
        return "products/products"; // 기존 products.html 재사용
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
