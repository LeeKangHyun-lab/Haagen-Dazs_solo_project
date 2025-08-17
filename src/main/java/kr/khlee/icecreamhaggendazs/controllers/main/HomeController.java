package kr.khlee.icecreamhaggendazs.controllers.main;

import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/products")
    public String productsPage(Model model) {

        List<Product> products = productService.SelectAllProducts();

        model.addAttribute("products", products);
        return "home/products";
    }

}
