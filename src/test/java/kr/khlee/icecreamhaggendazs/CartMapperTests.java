package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.mappers.cart.CartMapper;
import kr.khlee.icecreamhaggendazs.models.Cart;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class CartMapperTests {

    @Autowired
    CartMapper cartMapper;

    @Test
    public void insertCart() {
        Cart cart = new Cart();

        cart.setUserId("1");
        cart.setProductId(1);
        cart.setQuantity(1);

        cartMapper.insertCart(cart);

    }

    @Test
    public void selectCartByUserId(){
        Cart cart = new Cart();
        cart.setUserId("1");

        cartMapper.selectCartByUserId(cart.getUserId());
    }

    @Test
    public void updateCartQuantity(){
        Cart cart = new Cart();
        cart.setId(2L);
        cart.setProductId(1);
        cart.setQuantity(2);

        cartMapper.updateCartQuantity(cart.getId(), cart.getQuantity());
    }

    @Test
    public void deleteCart(){
        Cart cart = new Cart();
        cart.setId(2L);

        cartMapper.deleteCart(cart.getId());
    }

    @Test
    public void findCartItem(){
        Cart cart = new Cart();
        cart.setUserId("1");
        cart.setProductId(1);

        cartMapper.findCartItem(cart.getUserId(), cart.getProductId());
    }

}
