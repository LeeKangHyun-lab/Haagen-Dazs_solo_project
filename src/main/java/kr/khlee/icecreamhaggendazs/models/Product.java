package kr.khlee.icecreamhaggendazs.models;

import lombok.Data;

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
}
