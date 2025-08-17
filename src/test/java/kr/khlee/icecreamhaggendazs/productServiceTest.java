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

}
