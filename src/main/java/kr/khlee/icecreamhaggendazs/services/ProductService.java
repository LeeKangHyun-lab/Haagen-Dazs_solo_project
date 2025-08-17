package kr.khlee.icecreamhaggendazs.services;

import kr.khlee.icecreamhaggendazs.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> SelectRecommandation();

    public List<Product> SelectAllProducts();
}
