package kr.khlee.icecreamhaggendazs.services.impl;

import kr.khlee.icecreamhaggendazs.mappers.home.ProductMapper;
import kr.khlee.icecreamhaggendazs.models.Product;
import kr.khlee.icecreamhaggendazs.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public List<Product> SelectRecommandation() {
        List<Product> products = productMapper.SelectRecommandation();
        return products != null ? products : Collections.emptyList();
    }

    @Override
    public List<Product> SelectAllProducts(int offset, int listCount) {
        if (offset < 0 || listCount <= 0) {
            throw new IllegalArgumentException("offset 또는 listCount 값이 잘못되었습니다.");
        }
        return productMapper.SelectAllProducts(offset, listCount);
    }

    @Override
    public Product SelectById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("잘못된 상품 ID 입니다.");
        }
        Product product = productMapper.SelectById(id);
        if (product == null) {
            throw new IllegalArgumentException("해당 ID의 상품을 찾을 수 없습니다: " + id);
        }
        return product;
    }

    @Override
    public List<Product> SelectGiftAll(int offset, int listCount) {
        if (offset < 0 || listCount <= 0) {
            throw new IllegalArgumentException("offset 또는 listCount 값이 잘못되었습니다.");
        }
        List<Product> products = productMapper.SelectCakeAll(offset, listCount);
        return products != null ? products : Collections.emptyList();
    }

    @Override
    public List<Product> SelectGift() {
        List<Product> products = productMapper.SelectCake();
        return products != null ? products : Collections.emptyList();
    }

    @Override
    public List<Product> SelectSingleAll(int offset, int listCount) {
        if (offset < 0 || listCount <= 0) {
            throw new IllegalArgumentException("offset 또는 listCount 값이 잘못되었습니다.");
        }
        List<Product> products = productMapper.SelectSingleAll(offset, listCount);
        return products != null ? products : Collections.emptyList();
    }

    @Override
    public List<Product> SelectSingle() {
        List<Product> products = productMapper.SelectSingle();
        return products != null ? products : Collections.emptyList();
    }

    @Override
    public List<Product> SelectByCategory(String category, int offset, int listCount) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리를 입력해주세요.");
        }
        if (offset < 0 || listCount <= 0) {
            throw new IllegalArgumentException("offset 또는 listCount 값이 잘못되었습니다.");
        }
        List<Product> products = productMapper.SelectByCategory(category, offset, listCount);
        return products != null ? products : Collections.emptyList();
    }

    @Override
    public int getProductCount(String category) {
        if (category != null && !category.isEmpty()) {
            return productMapper.getProductCount(category);
        } else {
            return productMapper.getAllProductCount();
        }
    }

    @Override
    public List<Product> getAllCategories() {
        List<Product> categories = productMapper.getAllCategories();
        return categories != null ? categories : Collections.emptyList();
    }

    @Override
    public List<Product> SearchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList(); // 검색어 없으면 빈 리스트
        }
        List<Product> products = productMapper.SearchProducts(keyword);
        return products != null ? products : Collections.emptyList();
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
