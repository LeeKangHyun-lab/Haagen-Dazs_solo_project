package kr.khlee.icecreamhaggendazs.services.impl;

import kr.khlee.icecreamhaggendazs.mappers.home.ProductMapper;
import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
