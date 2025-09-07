package kr.khlee.icecreamhaggendazs.services;

import kr.khlee.icecreamhaggendazs.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> SelectRecommandation();

    List<Product> SelectAllProducts(int offset, int listCount);

    public Product SelectById(int id);

    public List<Product> SelectGiftAll(int offset, int listCount);

    public List<Product> SelectGift();

    public List<Product> SelectSingleAll(int offset, int listCount);

    public List<Product> SelectSingle();

    public List<Product> SelectByCategory(String category, int offset, int listCount);

    public int getProductCount(String category) throws Exception;

    public List<Product> getAllCategories();

    public List<Product> SearchProducts(String keyword);

    public int getAllSingleCount();

    public int getAllGiftCount();

}
