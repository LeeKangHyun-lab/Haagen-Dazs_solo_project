package kr.khlee.icecreamhaggendazs.mappers.home;

import kr.khlee.icecreamhaggendazs.models.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT name, original_price, price ,image_url FROM " +
            "products " +
            "LIMIT 0, 4")
    @Results(id = "resultMap")
    public List<Product> SelectRecommandation();

    @Select("SELECT * FROM " +
            "products")
    @Results(id = "SelectAll")
    public List<Product> SelectAllProducts();
}
