package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.models.Cart;
import kr.khlee.icecreamhaggendazs.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CartServiceTests {

    @Autowired
    CartService cartService;

    @Test
    public void addToCart() throws Exception{
        String userId = "testUser";
        int productId = 1; // products 테이블에 존재하는 상품 ID여야 함
        int quantity = 2;

        // 실행
        cartService.addToCart(userId, productId, quantity);

        // 검증
        Cart cart = cartService.getCartItems(userId).stream()
                .filter(c -> c.getProductId() == productId)
                .findFirst()
                .orElse(null);

        assertNotNull(cart, "장바구니에 아이템이 추가되어야 합니다.");
        assertEquals(quantity, cart.getQuantity(), "추가된 상품의 수량이 일치해야 합니다.");
    }

    @Test
    public void getCartItems() throws Exception{
        String userId = "testUser";

        List<Cart> carts = cartService.getCartItems(userId);

        assertNotNull(carts, "조회 결과는 null이 아니어야 합니다.");
        assertFalse(carts.isEmpty(), "해당 userId의 장바구니가 비어 있지 않아야 합니다.");
        assertEquals(userId, carts.get(0).getUserId(), "조회된 userId가 일치해야 합니다.");
    }

    @Test
    public void updateQuantity() throws Exception{
        String userId = "testUser";
        int productId = 1;

        // 먼저 추가
        cartService.addToCart(userId, productId, 1);
        Cart cart = cartService.getCartItems(userId).stream()
                .filter(c -> c.getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("업데이트 전 장바구니 아이템이 존재해야 합니다."));

        // 수량 업데이트
        int newQuantity = cart.getQuantity() + 3;
        cartService.updateQuantity(cart.getId(), newQuantity);

        // 검증
        Cart updated = cartService.getCartItems(userId).stream()
                .filter(c -> c.getProductId() == productId)
                .findFirst()
                .orElse(null);

        assertNotNull(updated, "업데이트 후에도 장바구니 아이템이 존재해야 합니다.");
        assertEquals(newQuantity, updated.getQuantity(), "수량이 정상적으로 업데이트되어야 합니다.");
    }

    @Test
    public void deleteCart() throws Exception{
        String userId = "testUser";
        int productId = 1;

        // 먼저 추가
        cartService.addToCart(userId, productId, 1);
        Cart cart = cartService.getCartItems(userId).stream()
                .filter(c -> c.getProductId() == productId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("삭제 전 장바구니 아이템이 존재해야 합니다."));

        // 삭제
        cartService.deleteItem(cart.getId());

        // 검증
        Cart deleted = cartService.getCartItems(userId).stream()
                .filter(c -> c.getProductId() == productId)
                .findFirst()
                .orElse(null);

        assertNull(deleted, "삭제 후에는 해당 아이템이 없어야 합니다.");
    }
}
