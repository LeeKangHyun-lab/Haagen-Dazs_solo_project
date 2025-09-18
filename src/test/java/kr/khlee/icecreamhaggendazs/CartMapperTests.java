package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.mappers.cart.CartMapper;
import kr.khlee.icecreamhaggendazs.models.Cart;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CartMapperTests {

    @Autowired
    CartMapper cartMapper;

    @Test
    public void insertCart() {
        Cart cart = new Cart();
        cart.setUserId("testUser");
        cart.setProductId(1);
        cart.setQuantity(2);

        int result = cartMapper.insertCart(cart);

        // 검증
        assertEquals(1, result, "장바구니 추가 시 1개의 행이 영향을 받아야 합니다.");
        assertNotNull(cart.getId(), "추가된 장바구니는 ID가 자동 생성되어야 합니다.");
    }

    @Test
    public void selectCartByUserId() {
        String userId = "testUser";

        List<Cart> carts = cartMapper.selectCartByUserId(userId);

        // 검증
        assertNotNull(carts, "조회 결과는 null이 아니어야 합니다.");
        assertFalse(carts.isEmpty(), "조회된 장바구니 목록은 비어 있지 않아야 합니다.");
        assertEquals(userId, carts.get(0).getUserId(), "조회된 장바구니의 userId가 요청한 userId와 같아야 합니다.");
    }

    @Test
    public void updateCartQuantity() {
        // 먼저 기존 데이터 가져오기
        Cart existing = cartMapper.findCartItem("testUser", 1);
        assertNotNull(existing, "수정 전에 장바구니 아이템이 존재해야 합니다.");

        int newQuantity = existing.getQuantity() + 5;
        int result = cartMapper.updateCartQuantity(existing.getId(), newQuantity);

        // 검증
        assertEquals(1, result, "수정 시 1개의 행이 영향을 받아야 합니다.");

        Cart updated = cartMapper.findCartItem("testUser", 1);
        assertEquals(newQuantity, updated.getQuantity(), "수량이 정상적으로 업데이트되어야 합니다.");
    }

    @Test
    public void deleteCart() {
        Cart existing = cartMapper.findCartItem("testUser", 1);
        assertNotNull(existing, "삭제 전 장바구니 아이템이 존재해야 합니다.");

        int result = cartMapper.deleteCart(existing.getId());

        // 검증
        assertEquals(1, result, "삭제 시 1개의 행이 영향을 받아야 합니다.");

        Cart deleted = cartMapper.findCartItem("testUser", 1);
        assertNull(deleted, "삭제 후에는 해당 아이템을 찾을 수 없어야 합니다.");
    }

    @Test
    public void findCartItem() {
        Cart cart = cartMapper.findCartItem("testUser", 1);

        // 검증
        assertNotNull(cart, "장바구니 아이템이 존재해야 합니다.");
        assertEquals("testUser", cart.getUserId(), "UserId가 일치해야 합니다.");
        assertEquals(100, cart.getProductId(), "ProductId가 일치해야 합니다.");
    }
}
