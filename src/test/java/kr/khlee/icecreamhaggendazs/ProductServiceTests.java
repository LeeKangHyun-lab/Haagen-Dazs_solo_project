package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService productService;

    @Test
    void selectRecommandation() {
        List<Product> products = productService.SelectRecommandation();
        assertNotNull(products);
        assertFalse(products.isEmpty(), "추천 상품은 비어 있지 않아야 합니다.");
        log.info(products.toString());
    }

    @Test
    void selectAllProducts() {
        List<Product> products = productService.SelectAllProducts(0, 20);
        assertNotNull(products);
        assertTrue(products.size() <= 20, "최대 20개까지만 조회되어야 합니다.");
    }

    @Test
    void selectById_valid() {
        Product product = productService.SelectById(1);
        assertNotNull(product);
        assertEquals(1, product.getId(), "ID가 1인 상품이어야 합니다.");
    }

    @Test
    void selectById_invalid() {
        assertThrows(IllegalArgumentException.class, () -> productService.SelectById(-1));
        assertThrows(IllegalArgumentException.class, () -> productService.SelectById(99999)); // 없는 ID
    }

    @Test
    void selectGift() {
        List<Product> products = productService.SelectGift();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void selectGiftAll() {
        List<Product> products = productService.SelectGiftAll(0, 20);
        assertNotNull(products);
    }

    @Test
    void selectSingle() {
        List<Product> products = productService.SelectSingle();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void selectSingleAll() {
        List<Product> products = productService.SelectSingleAll(0, 20);
        assertNotNull(products);
    }

    @Test
    void selectByCategory_valid() {
        List<Product> products = productService.SelectByCategory("파인트", 0, 20);
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals("파인트", products.get(0).getCategory());
    }

    @Test
    void selectByCategory_invalid() {
        assertThrows(IllegalArgumentException.class,
                () -> productService.SelectByCategory("", 0, 20));
        assertThrows(IllegalArgumentException.class,
                () -> productService.SelectByCategory("파인트", -1, 20));
    }

    @Test
    void getProductCount() throws Exception {
        int count = productService.getProductCount("파인트");
        assertTrue(count >= 0, "상품 개수는 0 이상이어야 합니다.");
    }

    @Test
    void getAllCategories() {
        List<Product> categories = productService.getAllCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
    }

    @Test
    void searchProducts_valid() {
        List<Product> products = productService.SearchProducts("케이크");
        assertNotNull(products);
        assertTrue(products.stream().anyMatch(p -> p.getName().contains("케이크")));
    }

    @Test
    void searchProducts_emptyKeyword() {
        List<Product> products = productService.SearchProducts("");
        assertNotNull(products);
        assertTrue(products.isEmpty(), "검색어가 없으면 빈 리스트를 반환해야 합니다.");
    }

    @Test
    void getAllSingleCount() {
        int count = productService.getAllSingleCount();
        assertTrue(count >= 0);
    }

    @Test
    void getAllGiftCount() {
        int count = productService.getAllGiftCount();
        assertTrue(count >= 0);
    }
}
