package kr.khlee.icecreamhaggendazs.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Product {
    int id;
    String name;
    String size;
    int price;
    int originalPrice;
    String description;
    String imageUrl;
    String category;
    String createdAt;
    String detailImageUrl;

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}
