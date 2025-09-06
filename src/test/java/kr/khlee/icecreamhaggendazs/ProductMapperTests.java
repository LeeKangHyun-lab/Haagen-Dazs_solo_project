package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.mappers.home.ProductMapper;
import kr.khlee.icecreamhaggendazs.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class ProductMapperTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void SelectRecommandation() {
        List<Product> products = productMapper.SelectRecommandation();

        log.info(products.toString());

    }

    @Test
    void SelectAllProducts() {
        List<Product> products = productMapper.SelectAllProducts();

        log.info(products.toString());
    }

    @Test
    void SelectById(){
        Product product = productMapper.SelectById(1);

        log.info(product.toString());
    }

    @Test
    void SelectCake(){
        List<Product> product = productMapper.SelectCake();

        log.info(product.toString());
    }

    @Test
    void SelectCakeAll(){
        List<Product> products = productMapper.SelectCakeAll();

        log.info(products.toString());
    }

    @Test
    void SelectSingle(){
        List<Product> products = productMapper.SelectSingle();

        log.info(products.toString());
    }

    @Test
    void SelectSingleAll(){
        List<Product> products = productMapper.SelectSingleAll();

        log.info(products.toString());
    }

    @Test
    void SelectByCategory(){
        String input = "파인트";
        List<Product> products = productMapper.SelectByCategory(input);

        log.info(products.toString());
    }

    @Test
    void getProductCount(){
        String input = "파인트";

        int count = productMapper.getProductCount(input);

        log.info(String.valueOf(count));
    }

    @Test
    void getAllProductCount(){
        int count = productMapper.getAllProductCount();

        log.info(String.valueOf(count));
    }

    @Test
    void searchProducts(){
        String keyword = "케이크";

        log.info(String.valueOf(productMapper.SearchProducts(keyword)));
    }
}
