package kr.khlee.icecreamhaggendazs.services;

import kr.khlee.icecreamhaggendazs.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> SelectRecommandation();

    public List<Product> SelectAllProducts();

    public Product SelectById(int id);

    public List<Product> SelectGiftAll();

    public List<Product> SelectGift();

    public List<Product> SelectSingleAll();

    public List<Product> SelectSingle();

    public List<Product> SelectByCategory(String category);

    public int getProductCount(String category) throws Exception;

    public List<Product> SearchProducts(String keyword);
}
