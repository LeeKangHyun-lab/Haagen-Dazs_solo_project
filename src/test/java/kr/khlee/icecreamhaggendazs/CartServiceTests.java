package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.models.Cart;
import kr.khlee.icecreamhaggendazs.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CartServiceTests {

    @Autowired
    CartService cartService;

    @Test
    public void addToCart() throws Exception {
        Cart cart = new Cart();
        cart.setUserId("1");
        cart.setProductId(1);
        cart.setQuantity(1);

        cartService.addToCart(cart.getUserId(), cart.getProductId(), cart.getQuantity());
    }

    @Test
    public void getCartItems() throws Exception {
        Cart cart = new Cart();
        cart.setUserId("1");
        cartService.getCartItems(cart.getUserId());
    }

    @Test
    public void updateQuantity() throws Exception {
        Cart cart = new Cart();
        cart.setId(3L);
        cart.setQuantity(3);

        cartService.updateQuantity(cart.getId(),cart.getQuantity());
    }

    @Test
    public void deleteCart() throws Exception {
        Cart cart = new Cart();
        cart.setId(3L);

        cartService.deleteItem(cart.getId());
    }
}
