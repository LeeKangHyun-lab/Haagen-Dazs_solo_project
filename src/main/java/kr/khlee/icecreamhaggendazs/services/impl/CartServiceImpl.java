package kr.khlee.icecreamhaggendazs.services.impl;

import kr.khlee.icecreamhaggendazs.mappers.cart.CartMapper;
import kr.khlee.icecreamhaggendazs.models.Cart;
import kr.khlee.icecreamhaggendazs.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;

    @Override
    @Transactional
    public void addToCart(String userId, int productId, int quantity) {

        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId는 비어 있을 수 없습니다.");
        }
        if (productId <= 0) {
            throw new IllegalArgumentException("잘못된 상품 ID입니다.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 1개 이상이어야 합니다.");
        }

        Cart existing = cartMapper.findCartItem(userId, productId);

        if (existing != null) {
            // 이미 있으면 수량 업데이트
            cartMapper.updateCartQuantity(existing.getId(), existing.getQuantity() + quantity);
        } else {
            // 없으면 새로 추가
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cartMapper.insertCart(cart);
        }
    }

    @Override
    public List<Cart> getCartItems(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("userId는 비어 있을 수 없습니다.");
        }
        return cartMapper.selectCartByUserId(userId);
    }

    @Override
    @Transactional
    public void updateQuantity(Long id, int quantity) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("잘못된 장바구니 ID입니다.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }
        cartMapper.updateCartQuantity(id, quantity);
    }

    @Override
    @Transactional
    public void deleteItem(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("잘못된 장바구니 ID입니다.");
        }
        cartMapper.deleteCart(id);
    }
}
