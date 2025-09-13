package kr.khlee.icecreamhaggendazs.models;

import lombok.Data;

@Data
public class Cart {
    private Long id;
    private String userId;
    private int productId;
    private int quantity;

    // JOIN 해서 Product 객체 바로 담기
    private Product product;
}
