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
        List<Product> products = productService.SelectAllProducts( 0, 20);

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
        List<Product> product = productService.SelectGiftAll(0, 20);

        log.info(product.toString());
    }

    @Test
    void selectSingle() throws Exception{
        List<Product> product = productService.SelectSingle();

        log.info(product.toString());
    }
    @Test
    void selectSingleAll() throws Exception{
        List<Product> product = productService.SelectSingleAll(0, 20);

        log.info(product.toString());
    }

    @Test
    void selectbyCategory() throws Exception{
        List<Product> product = productService.SelectByCategory("파인트", 0, 20);

        log.info(product.toString());
    }

    @Test
    void getProductCount() throws Exception{
        String input = "파인트";

        int count = productService.getProductCount(input);

        log.info(String.valueOf(count));
    }

    @Test
    void getAllCategories() throws Exception{
        List<Product> categories = productService.getAllCategories();

        log.info(categories.toString());
    }

    @Test
    void SearchProducts() throws Exception{
        String keyword = "케이크";
        log.info(productService.SearchProducts(keyword).toString());
    }

    @Test
    void getAllSingleCount(){
        System.out.println(productService.getAllSingleCount());
    }

    @Test
    void getAllGiftCount(){
        System.out.println(productService.getAllGiftCount());
    }
}
