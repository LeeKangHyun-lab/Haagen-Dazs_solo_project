package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.mappers.members.MemberMapper;
import kr.khlee.icecreamhaggendazs.models.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest
public class MemberMapperTests {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void selectCount(){
        Member input = new Member();
        input.setUserId("hellouser");
        int output = memberMapper.selectCount(input);
        log.debug("output:{}",output);
    }

    @Test
    void insert(){
        Member input=new Member();
        input.setUserId("testUser");
        input.setUserPw("testPw123");
        input.setUserName("테스트유저");
        input.setEmail("testuser@example.com");
        input.setPhone("010-1234-5678");
        input.setBirthday("1990-01-01");
        input.setGender("M");
        input.setPostcode("12345");
        input.setAddr1("서울시 강남구");
        input.setAddr2("테스트동 101호");
        input.setPhoto(null);

        int insertedId = memberMapper.insert(input);
        log.debug("insertedId:{}",insertedId);
    }

    @Test
    void selectItem(){
        Member input=new Member();
        input.setId(1);

        Member output = memberMapper.selectItem(input);
        log.debug("output:{}",output);
    }

    @Test
    void login(){
        Member input=new Member();
        input.setUserId("test1");
        input.setUserPw("1234");

        Member output = memberMapper.login(input);
        log.debug("output:{}",output);
    }

    @Test
    void updateLoginDate(){
        Member input=new Member();
        input.setId(5);

        int output = memberMapper.updateLoginDate(input);
        log.debug("output:{}",output);
    }

    @Test
    void findId(){
        Member input=new Member();
        input.setUserName("테스트");
        input.setEmail("test1@naver.com");

        Member output = memberMapper.findId(input);
        log.debug("output:{}",output);
    }

    @Test
    void resetPassword(){
       Member input = new Member();
       input.setUserId("test1");
       input.setEmail("ka09068@gmail.com");
       input.setUserPw("123");

       int output = memberMapper.resetPw(input);
       log.debug("output:{}",output);
    }

    @Test
    void editMember(){
        Member input = new Member();
        input.setId(4);
        input.setUserName("수정된 유저");
        input.setEmail("test1@naver.com");
        input.setPhone("01098765432");
        input.setBirthday("1990-01-01");
        input.setGender("M");
        input.setPostcode("12345");
        input.setAddr1("서울시 강남구");
        input.setAddr2("테스트동 101호");
        input.setPhoto(null);

        input.setUserPw("1234");
        input.setNewUserPw("123456");

        int output = memberMapper.update(input);
        log.debug("output:{}", output);
    }

    @Test
    void outMember(){
        Member input = new Member();
        input.setId(5);
        input.setUserPw("1234");

        int output = memberMapper.out(input);
        log.debug("output:{}", output);
    }

    @Test
    void selectOutMember(){
        List<Member> output = memberMapper.selectOutMembersPhoto();
        log.debug("output:{}",output);
    }

    @Test
    void deleteOutMember(){
        int output = memberMapper.deleteOutMembers();
        log.debug("output:{}",output);
    }
}

