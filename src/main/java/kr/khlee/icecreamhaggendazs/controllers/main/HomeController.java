package kr.khlee.icecreamhaggendazs.controllers.main;

import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class HomeController{

    @Autowired
    private ProductService productService;


    @GetMapping("/")
    public String homePage(Model model) {

        List<Product> products = productService.SelectRecommandation();

        model.addAttribute("products", products);
        return "home/index";
    }

    @GetMapping("/recommended")
    public String recommendedProducts(Model model) {
        List<Product> singleProducts = productService.SelectSingle();
        List<Product> giftProducts = productService.SelectGift();

        model.addAttribute("singleProducts", singleProducts);
        model.addAttribute("giftProducts", giftProducts);

        return "home/recommended";
    }

    @GetMapping("/recommended_single")
    public String recommendedSingleAll(Model model) {
        List<Product> singleProducts = productService.SelectSingleAll();

        model.addAttribute("singleProducts", singleProducts);

        return "home/recommended_single";

    }

    @GetMapping("/recommended_gift")
    public String recommendedGiftAll(Model model) {
        List<Product> giftProducts = productService.SelectGiftAll();

        model.addAttribute("giftProducts", giftProducts);

        return "home/recommended_gift";

    }

    @GetMapping("/products")
    public String productsPage(Model model) {

        List<Product> products = productService.SelectAllProducts();

        model.addAttribute("products", products);
        return "home/products";
    }

    @GetMapping("/products/{category}")
    public String productsByCategory(@PathVariable("category") String category, Model model) {
        List<Product> products = productService.SelectByCategory(category);

        model.addAttribute("products", products);
        model.addAttribute("category", category); // 뷰에서 타이틀/탭 표시용
        return "home/products"; // 기존 products.html 재사용
    }

    @GetMapping("/products/products_detail/{id}")
    public String productsDetailPage(Model model,
                                     @PathVariable (value = "id", required = false) int id) {

        Product product = productService.SelectById(id);

        model.addAttribute("product", product);

        return "home/products_detail";
    }

    @GetMapping("/membership")
    public String membershipPage() {
        return "home/membership";
    }

    @GetMapping("/brand")
    public String brandPage() {
        return "home/brand";
    }

    @GetMapping("/b2b")
    public String b2bPage() {
        return "home/b2b";
    }



}
