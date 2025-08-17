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
}
