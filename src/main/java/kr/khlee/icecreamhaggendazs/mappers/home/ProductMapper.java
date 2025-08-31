package kr.khlee.icecreamhaggendazs.mappers.home;

import kr.khlee.icecreamhaggendazs.models.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT id, name, original_price, price ,image_url FROM " +
            "products " +
            "LIMIT 0, 4")
    @Results(id = "resultMap")
    public List<Product> SelectRecommandation();

    @Select("<script>" +
            "SELECT id, name, size, original_price, price, description, image_url, " +
            "category, created_at " +
            "FROM products" +
//            "<if test='listCount > 0'> LIMIT #{offset}, #{listCount}</if>" +
            "</script>")
    @Results(id = "SelectAll")
    public List<Product> SelectAllProducts();

    @Select("<script>" +
            "SELECT id, name, size, " +
            "price, original_price, description, " +
            "image_url, category, " +
            "created_at, detail_image1 " +
            "FROM products " +
            "<where>" +
            "id = #{id} " +
            "</where>" +
            "</script>")
    @Results(id = "selectById")
    public Product SelectById(int id);

    @Select("<script>" +
            "SELECT id, name, size, price, original_price, " +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "<where>" +
            "category = '케이크' " +
            "</where>" +
            "LIMIT 0, 4 " +
            "</script>")
    @Results(id = "selectCakeAll")
    public List<Product> SelectCakeAll();

    @Select("<script>" +
            "SELECT id, name, size, price, original_price, " +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "<where>" +
            "category = '케이크' " +
            "</where>" +
            "</script>")
    @Results(id = "selectCake")
    public List<Product> SelectCake();

    @Select("SELECT id, name, size, price, original_price," +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "WHERE " +
            "category = '파인트' OR category = '미니컵' " )
    @Results(id = "selectSingleAll")
    public List<Product> SelectSingleAll();

    @Select("SELECT id, name, size, price, original_price," +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "WHERE " +
            "category = '파인트' OR category = '미니컵' " +
            "LIMIT 0, 4")
    @Results(id = "selectSingle")
    public List<Product> SelectSingle();

    @Select("<script>" +
            "SELECT id, name, size, price, original_price," +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "<where> " +
            "category = #{category} " +
            "</where>" +
//            "<if test='listCount > 0'> LIMIT #{offset}, #{listCount}</if>" +
            "</script>")
    @Results(id = "selectByCategory")
    public List<Product> SelectByCategory(@Param("category") String category);

    @Select("<script>" +
            "SELECT COUNT(*) FROM products " +
            "<where>" +
            "category = #{category} " +
            "</where>" +
            "</script>")
    @Results(id = "getProductCount")
    public int getProductCount(@Param("category") String category);

    @Select("<script>" +
            "SELECT COUNT(*) FROM products " +
            "</script>")
    @Results(id = "getAllProductCount")
    public int getAllProductCount();


}
