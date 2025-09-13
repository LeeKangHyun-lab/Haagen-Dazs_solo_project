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

    @Select("SELECT id, name, size, original_price, price, description, image_url, " +
            "category, created_at " +
            "FROM products " +
            "LIMIT #{offset}, #{listCount}")
    public List<Product> SelectAllProducts(@Param("offset") int offset,
                                           @Param("listCount") int listCount);


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
    @Results(id = "selectCake")
    public List<Product> SelectCake();

    @Select("<script>" +
            "SELECT id, name, size, price, original_price, " +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "<where>" +
            "category = '케이크' " +
            "</where>" +
            "LIMIT #{offset}, #{listCount}" +
            "</script>")
    @Results(id = "selectCakeAll")
    public List<Product> SelectCakeAll(@Param("offset") int offset,
                                       @Param("listCount") int listCount);

    @Select("SELECT id, name, size, price, original_price," +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "WHERE " +
            "category = '파인트' OR category = '미니컵' " +
            "LIMIT #{offset}, #{listCount}" )
    @Results(id = "selectSingleAll")
    public List<Product> SelectSingleAll(@Param("offset") int offset,
                                         @Param("listCount") int listCount);

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
            "<if test='listCount > 0'> LIMIT #{offset}, #{listCount}</if>" +
            "</script>")
    @Results(id = "selectByCategory")
    public List<Product> SelectByCategory(@Param("category") String category,
                                          @Param("offset") int offset,
                                          @Param("listCount") int listCount);

    @Select("<script>" +
            "SELECT COUNT(*) FROM products " +
            "<where>" +
            "category = #{category} " +
            "</where>" +
            "</script>")
    @Results(id = "getProductCount")
    public int getProductCount(@Param("category") String category);

    @Select("SELECT category " +
            "FROM products ")
    @Results(id = "getAllCategories")
    public List<Product> getAllCategories();

    @Select("<script>" +
            "SELECT COUNT(*) FROM products " +
            "</script>")
    @Results(id = "getAllProductCount")
    public int getAllProductCount();

    @Select("<script>" +
            "SELECT COUNT(*) FROM products " +
            "WHERE category = '파인트' or category = '미니컵'" +
            "</script>")
    @Results(id = "getAllSingleCount")
    public int getAllSingleCount();

    @Select("<script>" +
            "SELECT COUNT(*) FROM products " +
            "WHERE category = '케이크'" +
            "</script>")
    @Results(id = "getAllGiftCount")
    public int getAllGiftCount();

    @Select("SELECT id, name, price, original_price, " +
            "description, image_url, category, " +
            "created_at " +
            "FROM products " +
            "WHERE name LIKE concat('%', #{keyword}, '%')")
    @Results(id = "searchProducts")
    public List<Product> SearchProducts(@Param("keyword") String keyword);



}
