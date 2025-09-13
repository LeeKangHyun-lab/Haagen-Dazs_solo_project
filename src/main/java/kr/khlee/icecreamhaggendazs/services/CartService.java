package kr.khlee.icecreamhaggendazs.services;

import kr.khlee.icecreamhaggendazs.models.Cart;

import java.util.List;

public interface CartService {

    public void addToCart(String userId, int productId, int quantity) throws Exception;

    public List<Cart> getCartItems(String userId) throws Exception;

    public void updateQuantity(Long id, int quantity) throws Exception;

    public void deleteItem(Long id) throws Exception;
}
