package kr.khlee.icecreamhaggendazs.mappers.members;

import kr.khlee.icecreamhaggendazs.models.Member;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface MemberMapper {

    @Select("<script>"
            +"SELECT COUNT(*) FROM members"
            +"<where>"
            +"<if test='userId != null'>user_id = #{userId}</if>"
            +"<if test='email != null'>email = #{email}</if>"
            +"<if test='id != 0'>AND id != #{id}</if>"
            +"</where>"
            +"</script>")
    public int selectCount(Member input);
//    selectCount의 로직이 위의 @Select이다. 연결된 느낌.

    @Insert("Insert INTO members ("
            + "user_id, user_pw, user_name, email, phone, birthday, gender, postcode, addr1, addr2,"
            + "photo, is_out, is_admin, login_date, reg_date,edit_date"
            + ") VALUES ("
            + "#{userId}, MD5(#{userPw}), #{userName}, #{email}, #{phone}, #{birthday}, #{gender}, #{postcode},"
            + "#{addr1}, #{addr2}, #{photo}, 'N', 'N', NULL, NOW(), NOW()"
            +")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int insert(Member input);

    @Select("SELECT "
            + "id, user_id, user_pw, user_name, email, phone, birthday, gender, "
            + "postcode, addr1, addr2, photo, is_out, is_admin, login_date, reg_date, edit_date "
            + "FROM members "
            + "WHERE id = #{id}")
    @Results(id = "resultMap")
    public Member selectItem(Member input);

    @Select("SELECT "
            + "id, user_id, user_pw, user_name, email, phone, birthday, gender, "
            + "postcode, addr1, addr2, photo, is_out, is_admin, login_date, reg_date, edit_date "
            + "FROM members "
            + "WHERE user_id = #{userId} AND user_pw = MD5(#{userPw}) AND is_out='N'" )
    @ResultMap("resultMap")
    public Member login(Member input);

    @Update("UPDATE members SET login_date=NOW() WHERE id = #{id} AND is_out='N'")
    public int updateLoginDate(Member input);

    @Select("SELECT user_id FROM members "
            + "WHERE user_name = #{userName} AND email = #{email} AND is_out = 'N'")
    @ResultMap("resultMap")
    public Member findId(Member input);

    @Update("UPDATE members SET user_pw = MD5(#{userPw}) "
            +" WHERE user_id = #{userId} AND email = #{email} AND is_out = 'N'")
    public int resetPw(Member input);

    @Update("<script>"
            + "UPDATE members SET "
            + "user_name = #{userName},"
            + "<if test = 'newUserPw != null and newUserPw != \"\"'> user_pw = MD5(#{newUserPw}),</if>"
            + "email = #{email},"
            + "phone = #{phone},"
            + "birthday = #{birthday},"
            + "gender = #{gender},"
            + "postcode = #{postcode},"
            + "addr1 = #{addr1},"
            + "addr2 = #{addr2},"
            + "photo = #{photo},"
            + "edit_date = NOW() "
            + "WHERE id = #{id} AND user_pw = MD5(#{userPw}) AND is_out = 'N'"
            + "</script>")
    public int update(Member input);

    @Update("UPDATE members "
            + "SET is_out='Y', edit_date=NOW() "
            + "WHERE id = #{id} AND user_pw = MD5(#{userPw}) AND is_out = 'N'")
    public int out(Member input);

    @Select("SElECT photo FROM members "+
            "WHERE is_out = 'Y' AND edit_date < DATE_ADD(NOW(), interval -1 minute) AND "
            + "photo IS NOT NULL")
    @ResultMap("resultMap")
    public List<Member> selectOutMembersPhoto();

    @Delete("DELETE FROM members "
            + "WHERE is_out='Y' AND "
            + "edit_date < Date_ADD(Now(), interval -1 minute)")
    public int deleteOutMembers();
}



