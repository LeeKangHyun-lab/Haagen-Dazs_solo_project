package kr.khlee.icecreamhaggendazs;


import kr.khlee.icecreamhaggendazs.helpers.UtilHelper;
import kr.khlee.icecreamhaggendazs.models.Member;
import kr.khlee.icecreamhaggendazs.services.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UtilHelper utilHelper;

    @Test
    void testIsUniqueUserId(){
        Member input = new Member();
        input.setUserId("test");

        try{
            memberService.isUniqueUserId(input);
        }catch (Exception e){
            log.debug("사용할 수 없는 아이디입니다.");
            return;
        }

        log.debug("사용 가능한 아이디입니다.");
    }

    @Test
    void testIsUniqueEmail(){
        Member input = new Member();
        input.setEmail("test@example.com");

        try{
            memberService.isUniqueEmail(input);
        }catch(Exception e){
            log.debug("사용할 수 없는 이메일입니다.");
            return;
        }

        log.debug("사용 가능한 이메일입니다.");
    }

    @Test
    void testJoin(){
        Member input=new Member();
        input.setUserId("helloworld2");
        input.setUserPw("1234");
        input.setUserName("헬로월드");
        input.setEmail("helloworld2@example.com");
        input.setPhone("010-1234-5678");
        input.setBirthday("1990-01-01");
        input.setGender("M");
        input.setPostcode("12345");
        input.setAddr1("서울시 강남구");
        input.setAddr2("테스트동 101호");
        input.setPhoto(null);

        Member output = null;

        try{
            output = memberService.join(input);
        }catch(Exception e){
            log.error("회원가입에 실패했습니다.", e);
            return;
        }

        log.debug("가입된 회원 정보: {}", output);
    }
    @Test
    void testLogin(){
        Member input = new Member();
        input.setUserId("test1");
        input.setUserPw("1234");

        Member output = null;

        try{
            output = memberService.login(input);
        }catch(Exception e){
            log.error("로그인에 실패했습니다.", e);
            return;
        }
        log.debug("로그인 성공, 회원 정보: {}", output);
    }

    @Test
    void testResetPw(){
        String newPassword = utilHelper.getRandomString(6);
        log.debug("새로운 비밀번호: {}", newPassword);

        Member input = new Member();
        input.setUserId("test1");
        input.setEmail("ka09068@gmail.com");
        input.setUserPw(newPassword);

        try{
            memberService.resetPw(input);
        }catch(Exception e){
            log.error("비밀번호 재설정에 실패했습니다.", e);
            return;
        }

        log.debug("비밀번호가 성공적으로 재설정되었습니다.");
    }

    @Test
    void testEditMember(){
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

        input.setUserPw("123456");
        input.setNewUserPw("123456");

        Member output = null;
        try{
            output = memberService.update(input);
        } catch (Exception e){
            log.error("회원 정보 수정에 실패했습니다.", e);
            return;
        }
        log.debug("output:{}", output.toString());

    }

    @Test
    void testOutMember() {
        Member input = new Member();
        input.setId(5);
        input.setUserPw("1234");

        try {
            memberService.out(input);
        } catch (Exception e) {
            log.error("회원 탈퇴에 실패했습니다.", e);
            return;
        }

        log.debug("회원 탈퇴가 성공적으로 처리되었습니다.");
    }

    @Test
    void testProcessOutMember() {
        List<Member> output = null;
        try{
            output = memberService.processOutMembers();
        } catch(Exception e){
            log.error("탈퇴 처리에 실패했습니다.", e);
            return;
        }

        if(output !=  null && !output.isEmpty()){
            log.debug("탈퇴 처리된 회원 목록: {}", output);
        } else{
            log.debug("틸퇴 처리된 회원이 없습니다.");
        }
    }
}
