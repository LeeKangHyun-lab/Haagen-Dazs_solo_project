package kr.khlee.icecreamhaggendazs;

import kr.khlee.icecreamhaggendazs.mappers.members.MemberMapper;
import kr.khlee.icecreamhaggendazs.models.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class MemberMapperTests {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void selectCount() {
        Member input = new Member();
        input.setUserId("hellouser");
        int output = memberMapper.selectCount(input);
        assertTrue(output >= 0, "중복체크 결과는 0 이상이어야 합니다.");
    }

    @Test
    void insert() {
        Member input = new Member();
        input.setUserId("testUser100");
        input.setUserPw("testPw123");
        input.setUserName("테스트유저");
        input.setEmail("testuser100@example.com");
        input.setPhone("010-1234-5678");
        input.setBirthday("1990-01-01");
        input.setGender("M");
        input.setPostcode("12345");
        input.setAddr1("서울시 강남구");
        input.setAddr2("테스트동 101호");
        input.setPhoto(null);

        int result = memberMapper.insert(input);
        assertEquals(1, result, "insert는 1개 행이 영향을 주어야 합니다.");
        assertNotNull(input.getId(), "insert 후에는 PK id가 채워져야 합니다.");
    }

    @Test
    void selectItem() {
        Member input = new Member();
        input.setId(1);

        Member output = memberMapper.selectItem(input);
        assertNotNull(output, "조회 결과가 null이면 안 됩니다.");
        assertEquals(1, output.getId());
    }

    @Test
    void login_success() {
        Member input = new Member();
        input.setUserId("test2");
        input.setUserPw("123");

        Member output = memberMapper.login(input);
        assertNotNull(output, "올바른 아이디/비밀번호로 로그인해야 합니다.");
    }

    @Test
    void login_fail() {
        Member input = new Member();
        input.setUserId("test1");
        input.setUserPw("wrongPw");

        Member output = memberMapper.login(input);
        assertNull(output, "잘못된 비밀번호로는 로그인할 수 없어야 합니다.");
    }

    @Test
    void updateLoginDate() {
        Member input = new Member();
        input.setId(1);
        int result = memberMapper.updateLoginDate(input);
        assertTrue(result >= 0, "로그인 날짜 업데이트 결과는 0 이상이어야 합니다.");
    }

    @Test
    void findId() {
        Member input = new Member();
        input.setUserName("테스트유저");
        input.setEmail("testuser100@example.com");

        Member output = memberMapper.findId(input);
        assertNotNull(output, "이름/이메일이 일치하면 아이디가 나와야 합니다.");
    }

    @Test
    void resetPassword_fail() {
        Member input = new Member();
        input.setUserId("noUser");
        input.setEmail("wrong@example.com");
        input.setUserPw("newPw");

        int result = memberMapper.resetPw(input);
        assertEquals(0, result, "존재하지 않는 유저는 비밀번호 변경 불가해야 합니다.");
    }

    @Test
    void updateMember() {
        Member input = new Member();
        input.setId(1);
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

        int result = memberMapper.update(input);
        assertTrue(result >= 0, "업데이트 결과는 0 이상이어야 합니다.");
    }

    @Test
    void outMember_fail() {
        Member input = new Member();
        input.setId(9999); // 없는 유저
        input.setUserPw("wrong");

        int result = memberMapper.out(input);
        assertEquals(0, result, "없는 유저/비밀번호 불일치 시 탈퇴 불가해야 합니다.");
    }

    @Test
    void selectOutMember() {
        List<Member> output = memberMapper.selectOutMembersPhoto();
        assertNotNull(output);
    }

    @Test
    void deleteOutMember() {
        int result = memberMapper.deleteOutMembers();
        assertTrue(result >= 0, "삭제된 행 수는 0 이상이어야 합니다.");
    }
}
