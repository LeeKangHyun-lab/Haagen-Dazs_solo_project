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
    public List<Product> SelectAllProducts(int offset, int listCount) {
        return productMapper.SelectAllProducts(offset, listCount);
    }


    @Override
    public Product SelectById(int id) {
        return productMapper.SelectById(id);
    }

    @Override
    public List<Product> SelectGiftAll(int offset, int listCount) {
        return productMapper.SelectCakeAll(offset, listCount);
    }

    @Override
    public List<Product> SelectGift(){
        return productMapper.SelectCake();
    }

    @Override
    public List<Product> SelectSingleAll(int offset, int listCount) {
        return productMapper.SelectSingleAll(offset, listCount);
    }

    @Override
    public List<Product> SelectSingle() {
        return productMapper.SelectSingle();
    }

    @Override
    public List<Product> SelectByCategory(String category, int offset, int listCount) {
        return productMapper.SelectByCategory(category, offset, listCount);
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
    public List<Product> getAllCategories() {
        return productMapper.getAllCategories();
    }

    @Override
    public List<Product> SearchProducts(String keyword) {
        return productMapper.SearchProducts(keyword);
    }

    @Override
    public int getAllSingleCount() {
        return productMapper.getAllSingleCount();
    }

    @Override
    public int getAllGiftCount() {
        return productMapper.getAllGiftCount();
    }


}
