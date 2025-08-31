package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class productServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void selectRecommandation() throws Exception{
        List<Product> products = productService.SelectRecommandation();

        log.info(products.toString());
    }

    @Test
    void selectAllProducts() throws Exception{
        List<Product> products = productService.SelectAllProducts();

        log.info(products.toString());
    }

    @Test
    void selectById() throws Exception{
        Product product = productService.SelectById(1);

        log.info(product.toString());
    }

    @Test
    void selectCake() throws Exception{
        List<Product> product = productService.SelectGift();

        log.info(product.toString());
    }

    @Test
    void selectCakeAll() throws Exception{
        List<Product> product = productService.SelectGiftAll();

        log.info(product.toString());
    }

    @Test
    void selectSingle() throws Exception{
        List<Product> product = productService.SelectSingle();

        log.info(product.toString());
    }
    @Test
    void selectSingleAll() throws Exception{
        List<Product> product = productService.SelectSingleAll();

        log.info(product.toString());
    }

    @Test
    void selectSingleProduct() throws Exception{
        List<Product> product = productService.SelectByCategory("파인트");

        log.info(product.toString());
    }

    @Test
    void getProductCount() throws Exception{
        String input = "파인트";

        int count = productService.getProductCount(input);

        log.info(String.valueOf(count));
    }

}
