package kr.khlee.icecreamhaggendazs.controllers.cart;

import jakarta.servlet.http.HttpSession;
import kr.khlee.icecreamhaggendazs.models.Cart;
import kr.khlee.icecreamhaggendazs.models.Member;
import kr.khlee.icecreamhaggendazs.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartRestController {

    private final CartService cartService;

    /** 장바구니 추가 */
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            HttpSession session,
            @RequestParam int productId,
            @RequestParam(defaultValue = "1") int quantity) {

        // 세션에서 로그인 유저 확인
        Object memberObj = session.getAttribute("memberInfo");
        if (memberObj == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }

        String userId = ((Member) memberObj).getUserId();

        try {
            cartService.addToCart(userId, productId, quantity);
            return ResponseEntity.ok("장바구니에 담겼습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("장바구니 담기 중 오류 발생");
        }
    }

    /** 장바구니 목록 조회 */
    @GetMapping
    public ResponseEntity<?> getCart(HttpSession session) {
        Object memberObj = session.getAttribute("memberInfo");
        if (memberObj == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }

        String userId = ((Member) memberObj).getUserId();
        try {
            List<Cart> cartItems = cartService.getCartItems(userId);
            return ResponseEntity.ok(cartItems);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("장바구니 조회 중 오류 발생");
        }
    }

    /** 장바구니 수량 변경 */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuantity(
            HttpSession session,
            @PathVariable Long id,
            @RequestParam int quantity) {

        // 로그인 체크
        if (session.getAttribute("memberInfo") == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }

        try {
            cartService.updateQuantity(id, quantity);
            return ResponseEntity.ok("수량이 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("수량 변경 중 오류 발생");
        }
    }

    /** 장바구니 아이템 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(HttpSession session, @PathVariable Long id) {
        // 로그인 체크
        if (session.getAttribute("memberInfo") == null) {
            return ResponseEntity.badRequest().body("로그인이 필요합니다.");
        }

        try {
            cartService.deleteItem(id);
            return ResponseEntity.ok("삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("삭제 중 오류 발생");
        }
    }
}
