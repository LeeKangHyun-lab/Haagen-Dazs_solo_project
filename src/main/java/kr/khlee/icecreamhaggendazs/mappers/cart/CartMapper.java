package kr.khlee.icecreamhaggendazs.mappers.cart;

import kr.khlee.icecreamhaggendazs.models.Cart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    // 장바구니 담기
    @Insert("INSERT INTO cart (user_id, product_id, quantity) " +
            "VALUES (#{userId}, #{productId}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "id") // 자동 생성 PK 매핑
    int insertCart(Cart cart);

    // 사용자별 장바구니 조회 (상품 테이블 조인)
    @Select("SELECT c.id, c.user_id, c.product_id, c.quantity, " +
            "p.id as p_id, p.name as p_name, p.price as p_price, p.image_url as p_imageUrl " +
            "FROM cart c " +
            "JOIN products p ON c.product_id = p.id " +
            "WHERE c.user_id = #{userId}")
    @Results({
            @Result(property="id", column="id"),
            @Result(property="userId", column="user_id"),
            @Result(property="productId", column="product_id"),
            @Result(property="quantity", column="quantity"),
            @Result(property="product.id", column="p_id"),
            @Result(property="product.name", column="p_name"),
            @Result(property="product.price", column="p_price"),
            @Result(property="product.imageUrl", column="p_imageUrl")
    })
    List<Cart> selectCartByUserId(String userId);

    // 수량 변경
    @Update("UPDATE cart SET quantity = #{quantity} WHERE id = #{id}")
    int updateCartQuantity(@Param("id") Long id, @Param("quantity") int quantity);

    // 장바구니 삭제
    @Delete("DELETE FROM cart WHERE id = #{id}")
    int deleteCart(Long id);

    // 특정 상품이 이미 장바구니에 있는지 확인
    @Select("SELECT * FROM cart WHERE user_id = #{userId} AND product_id = #{productId}")
    Cart findCartItem(@Param("userId") String userId, @Param("productId") int productId);
}
