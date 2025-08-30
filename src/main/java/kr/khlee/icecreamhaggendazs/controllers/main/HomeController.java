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
public class HomeController {

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
        List<Product> products = productService.SelectRecommandation();

        model.addAttribute("singleProducts", products);
        model.addAttribute("giftProducts", products);

        return "home/recommended";
    }

    @GetMapping("/products")
    public String productsPage(Model model) {

        List<Product> products = productService.SelectAllProducts();

        model.addAttribute("products", products);
        return "home/products";
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
