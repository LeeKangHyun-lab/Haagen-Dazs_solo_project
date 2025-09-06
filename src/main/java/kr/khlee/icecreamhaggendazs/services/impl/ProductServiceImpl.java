package kr.khlee.icecreamhaggendazs.services.impl;

import kr.khlee.icecreamhaggendazs.mappers.home.ProductMapper;
import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.ProtectionDomain;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> SelectRecommandation() {
        List<Product> products = null;

        products = productMapper.SelectRecommandation();

        return products;
    }

    @Override
    public List<Product> SelectAllProducts() {
        return productMapper.SelectAllProducts();
    }

    @Override
    public Product SelectById(int id) {
        return productMapper.SelectById(id);
    }

    @Override
    public List<Product> SelectGiftAll() {
        return productMapper.SelectCakeAll();
    }

    @Override
    public List<Product> SelectGift(){
        return productMapper.SelectCake();
    }

    @Override
    public List<Product> SelectSingleAll() {
        return productMapper.SelectSingleAll();
    }

    @Override
    public List<Product> SelectSingle() {
        return productMapper.SelectSingle();
    }

    @Override
    public List<Product> SelectByCategory(String category) {
        return productMapper.SelectByCategory(category);
    }

    @Override
    public int getProductCount(String category) throws Exception {
        int count = 0;
        if (category != null && !category.isEmpty()) {
            count = productMapper.getProductCount(category);
        } else {
            // 카테고리 없으면 전체 개수
            count = productMapper.getAllProductCount();
        }
        return count;
    }

    @Override
    public List<Product> SearchProducts(String keyword) {
        return productMapper.SearchProducts(keyword);
    }


}
