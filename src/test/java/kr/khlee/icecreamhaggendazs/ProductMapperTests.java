package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.mappers.home.ProductMapper;
import kr.khlee.icecreamhaggendazs.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class ProductMapperTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void SelectRecommandation() {
        List<Product> products = productMapper.SelectRecommandation();
        assertNotNull(products, "추천 상품 리스트는 null이 아니어야 합니다.");
        assertFalse(products.isEmpty(), "추천 상품 리스트는 비어 있지 않아야 합니다.");
        log.info(products.toString());
    }

    @Test
    void SelectAllProducts() {
        List<Product> products = productMapper.SelectAllProducts(0, 20);
        assertNotNull(products);
        assertTrue(products.size() <= 20, "최대 20개까지만 조회되어야 합니다.");
        log.info(products.toString());
    }

    @Test
    void SelectById() {
        Product product = productMapper.SelectById(1);
        assertNotNull(product, "id=1인 상품이 존재해야 합니다.");
        assertEquals(1, product.getId(), "조회된 상품 ID가 1이어야 합니다.");
        log.info(product.toString());
    }

    @Test
    void SelectCake() {
        List<Product> products = productMapper.SelectCake();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals("케이크", products.get(0).getCategory(), "케이크 카테고리 상품이어야 합니다.");
        log.info(products.toString());
    }

    @Test
    void SelectCakeAll() {
        List<Product> products = productMapper.SelectCakeAll(0, 20);
        assertNotNull(products);
        log.info(products.toString());
    }

    @Test
    void SelectSingle() {
        List<Product> products = productMapper.SelectSingle();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        log.info(products.toString());
    }

    @Test
    void SelectSingleAll() {
        List<Product> products = productMapper.SelectSingleAll(0, 20);
        assertNotNull(products);
        log.info(products.toString());
    }

    @Test
    void SelectByCategory() {
        String input = "파인트";
        List<Product> products = productMapper.SelectByCategory(input, 0, 20);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(input, products.get(0).getCategory(), "조회된 상품 카테고리가 요청한 값과 같아야 합니다.");
        log.info(products.toString());
    }

    @Test
    void getProductCount() {
        String input = "파인트";
        int count = productMapper.getProductCount(input);
        assertTrue(count >= 0, "상품 개수는 0 이상이어야 합니다.");
        log.info("파인트 카테고리 개수: " + count);
    }

    @Test
    void getAllCategories() {
        List<Product> categories = productMapper.getAllCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        log.info(categories.toString());
    }

    @Test
    void getAllProductCount() {
        int count = productMapper.getAllProductCount();
        assertTrue(count > 0, "전체 상품 개수는 0보다 커야 합니다.");
        log.info("전체 상품 개수: " + count);
    }

    @Test
    void searchProducts() {
        String keyword = "케이크";
        List<Product> products = productMapper.SearchProducts(keyword);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().anyMatch(p -> p.getName().contains(keyword)),
                "검색 결과에는 키워드를 포함한 상품이 있어야 합니다.");
        log.info(products.toString());
    }

    @Test
    void getAllSingleCount() {
        int count = productMapper.getAllSingleCount();
        assertTrue(count >= 0, "파인트/미니컵 개수는 0 이상이어야 합니다.");
        log.info("파인트/미니컵 상품 개수: " + count);
    }

    @Test
    void getAllGiftCount() {
        int count = productMapper.getAllGiftCount();
        assertTrue(count >= 0, "케이크 개수는 0 이상이어야 합니다.");
        log.info("케이크 상품 개수: " + count);
    }
}
