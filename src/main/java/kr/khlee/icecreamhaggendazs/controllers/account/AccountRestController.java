package kr.khlee.icecreamhaggendazs.controllers.account;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.khlee.icecreamhaggendazs.exceptions.StringFormatException;
import kr.khlee.icecreamhaggendazs.helpers.*;
import kr.khlee.icecreamhaggendazs.models.Member;
import kr.khlee.icecreamhaggendazs.models.UploadItem;
import kr.khlee.icecreamhaggendazs.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccountRestController {

    private final MemberService memberService;

    private final RegexHelper regexHelper;

    private final FileHelper fileHelper;

    private final UtilHelper utilHelper;

    private final MailHelper mailHelper;

    @GetMapping("/api/account/id_unique_check")
    public Map<String, Object> idUniqueCheck(@RequestParam("user_id") String userId,
                                             @SessionAttribute(value = "memberInfo", required = false) Member memberInfo)
            throws Exception {
        regexHelper.isValue(userId, "아이디를 입력하세요.");
        regexHelper.isEngNum(userId, "아이디는 영문자와 숫자만 입력할 수 있습니다.");

        Member input = new Member();
        input.setUserId(userId);

        if (memberInfo != null) {
            input.setId(memberInfo.getId());
        }

        memberService.isUniqueUserId(input);

        return null;
    }

    @GetMapping("/api/account/email_unique_check")
    public Map<String, Object> EmailUniqueCheck(@RequestParam("email") String email,
                                                @SessionAttribute(value = "memberInfo", required = false) Member memberInfo)
            throws Exception {

        regexHelper.isValue(email, "이메일을 입력하세요.");
        regexHelper.isEmail(email, "이메일 형식이 맞지 않습니다.");

        Member input = new Member();
        input.setEmail(email);

        if (memberInfo != null) {
            input.setId(memberInfo.getId());
        }

        memberService.isUniqueEmail(input);

        return null;
    }

    @PostMapping("/api/account/join")
    @SessionCheckHelper(enable = false)
    public Map<String, Object> join(
            @RequestParam("user_id") String userId,
            @RequestParam("user_pw") String userPw,
            @RequestParam("user_pw_re") String userPwRe,
            @RequestParam("user_name") String userName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("birthday") String birthday,
            @RequestParam("gender") String gender,
            @RequestParam("postcode") String postcode,
            @RequestParam("addr1") String addr1,
            @RequestParam("addr2") String addr2,
            @RequestParam(value = "photo", required = false) MultipartFile photo) throws Exception {
        regexHelper.isValue(userId, "아이디를 입력하세요.");
        regexHelper.isEngNum(userId, "아이디는 영문자와 숫자만 입력할 수 있습니다.");
        regexHelper.isValue(userPw, "비밀번호를 입력하세요.");

        if (!userPw.equals(userPwRe)) {
            throw new StringFormatException("비밀번호가 확인이 잘못되었습니다.");
        }

        regexHelper.isValue(userName, "이름을 입력하세요.");
        regexHelper.isKor(userName, "이름은 한글만 입력 가능합니다.");
        regexHelper.isValue(email, "이메일을 입력하세요.");
        regexHelper.isEmail(email, "이메일 형식이 잘못되었습니다.");
        regexHelper.isValue(phone, "전화번호를 입력하세요.");
        regexHelper.isPhone(phone, "전화번호 형식이 잘못되었습니다.");
        regexHelper.isValue(birthday, "생년월일을 입력하세요.");
        regexHelper.isValue(gender, "성별을 입력하세요.");

        if (!gender.equals("M") && !gender.equals("F")) {
            throw new StringFormatException("성별은 M(남성) 또는 F(여성)만 입력할 수 있습니다.");
        }
// 1)유효성 검사
        regexHelper.isValue(postcode, "우편번호를 입력하세요.");
        regexHelper.isValue(addr1, "주소를 입력하세요.");
        regexHelper.isValue(addr2, "상세주소를 입력하세요.");

/** 2) 업로드 받기 */
        UploadItem uploadItem = fileHelper.saveMultipartFile(photo);

/** 3) 정보를 Service에 전달하기 위한 객체 구성 */
        Member member = new Member();
        member.setUserId(userId);
        member.setUserPw(userPw);
        member.setUserName(userName);
        member.setEmail(email);
        member.setPhone(phone);
        member.setBirthday(birthday);
        member.setGender(gender);
        member.setPostcode(postcode);
        member.setAddr1(addr1);
        member.setAddr2(addr2);

// 업로드 된 이미지의 경로만 DB에 저장하면 됨
        if (uploadItem != null) {
            member.setPhoto(uploadItem.getFilePath());
        }

/** 4) DB에 저장 */
        memberService.join(member);

/** 5) 결과 반환 */
        return null;
    }


    @PostMapping("/api/account/login")
    @SessionCheckHelper(enable = false)
    public Map<String, Object> login(
            //HttpServletRequest 는 본 로직에서 세션을 getSession()으로 가져와
            // memberinfo에 output값을 저장하는 역할을 한다.
            HttpServletRequest request,
            @RequestParam("user_id") String userId,
            @RequestParam("user_pw") String userPw)
            throws Exception {

        regexHelper.isValue(userId, "아이디를 입력하세요.");
        regexHelper.isValue(userPw, "비밀번호를 입력하세요.");

        Member input = new Member();
        input.setUserId(userId);
        input.setUserPw(userPw);

        Member output = memberService.login(input);

        //memberinfo는 개발자가 원하는 이름으로 지정하는 문자옇
        request.getSession().setAttribute("memberInfo", output);

        return null;
    }

    @GetMapping("/api/account/logout")
    @SessionCheckHelper(enable = true)
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return null;
    }

    @PostMapping("/api/account/find_id")
    @SessionCheckHelper(enable = false)
    public Map<String, Object> findId(
            @RequestParam("user_name") String userName,
            @RequestParam("email") String email) throws Exception {
        regexHelper.isValue(userName, "이름을 입력하세요.");
        regexHelper.isValue(email, "이메일을 입력하세요.");
        regexHelper.isEmail(email, "이메일 형식이 잘못되었습니다.");

        Member input = new Member();
        input.setUserName(userName);
        input.setEmail(email);

        Member output = memberService.findId(input);

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("user_id", output.getUserId());

        return data;
    }

    @PutMapping("/api/account/reset_pw")
    @SessionCheckHelper(enable = false)
    public Map<String, Object> resetPw(
            @RequestParam("user_id") String userId,
            @RequestParam("email") String email) throws Exception {
        regexHelper.isValue(userId, "아이디를 입력하세요.");
        regexHelper.isValue(email, "이메일을 입력하세요.");
        regexHelper.isEmail(email, "이메일 혈식이 잘못되었습니다.");

        String newPassword = utilHelper.getRandomString(8);

        Member input = new Member();
        input.setUserId(userId);
        input.setEmail(email);
        input.setUserPw(newPassword);

        memberService.resetPw(input);

        ClassPathResource resource = new ClassPathResource("mail_templates/reset_pw.html");
        String mailTemplatePath = resource.getFile().getAbsolutePath();

        String template = fileHelper.readString(mailTemplatePath);
        template = template.replace("{{userId}}", userId);
        template = template.replace("{{password}}", newPassword);

        String subject = userId + "님의 비밀번호가 재설정되었습니다.";
        mailHelper.sendMail(email, subject, template);

        return null;

    }


    /**
     * 회원 정보 수정
     *
     * @param request          // 서블릿 정보
     * @param memberInfo       // 세션에서 정보 원본
     * @param userPw           // 현재 비밀번호 (검증 원본)
     * @param newUserPw        // 신규 비밀번호
     * @param newUserPwConfirm // 신규 비밀번호 확인
     * @param userName         // 이름
     * @param email            // 이메일
     * @param phone            // 전화번호
     * @param birthday         // 생년월일
     * @param gender           // 성별
     * @param postcode         // 우편번호
     * @param addr1            // 주소
     * @param addr2            // 상세주소
     * @param deletePhoto      // 신규 프로필 사진 삭제 여부(Y/N)
     * @param photo            // 프로필 사진 (등록 사용자)
     * @return // 성공 또는 실패 결과에 대한 JSON 응답
     * @throws Exception // 입력값 유효성 검사 실패 또는 회원 정보 수정 처리 중 예외 발생시
     */
    @PutMapping("/api/account/edit")
    @SessionCheckHelper(enable = true)
    public Map<String, Object> edit(
            HttpServletRequest request,
            @SessionAttribute("memberInfo") Member memberInfo,
            @RequestParam("user_pw") String userPw,
            @RequestParam("new_user_pw") String newUserPw,
            @RequestParam("new_user_pw_confirm") String newUserPwConfirm,
            @RequestParam("user_name") String userName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("birthday") String birthday,
            @RequestParam("gender") String gender,
            @RequestParam("postcode") String postcode,
            @RequestParam("addr1") String addr1,
            @RequestParam("addr2") String addr2,
            @RequestParam(value = "delete_photo", defaultValue = "N") String deletePhoto,
            @RequestParam(value = "photo", required = false) MultipartFile photo)
            throws Exception {

        regexHelper.isValue(userPw, "현재 비밀번호를 입력하세요.");

        if ((newUserPw != null && !newUserPw.isEmpty()) && !newUserPw.equals(newUserPwConfirm)) {
            regexHelper.isValue(newUserPw, "비밀번호 확인이 잘못되었습니다.");
        }

        regexHelper.isValue(userName, "이름을 입력해주세요.");
        regexHelper.isEmail(email, "이메일을 입력해주세요.");
        regexHelper.isEmail(email, "이메일 형식이 올바르지 않습니다.");
        regexHelper.isPhone(phone, "전화번호를 입력해주세요.");
        regexHelper.isPhone(phone, "전화번호 형식이 올바르지 않습니다.");
        regexHelper.isValue(birthday, "생년월일을 입력해주세요.");
        regexHelper.isValue(gender, "성별을 입력해주세요.");

        if (!gender.equals("M") && !gender.equals("F")) {
            throw new StringFormatException("성별은 M(남성) 또는 F(여성)만 입력할 수 있습니다.");
        }

        regexHelper.isValue(postcode, "우편번호를 입력하세요.");
        regexHelper.isValue(addr1, "주소를 입력하세요.");
        regexHelper.isValue(addr2, "상세주소를 입력하세요.");

        Member input = new Member();
        input.setEmail(email);
        input.setId(memberInfo.getId());
        memberService.isUniqueEmail(input);

        UploadItem uploadItem = null;

        try {
            uploadItem = fileHelper.saveMultipartFile(photo);
        } catch (Exception e) {

        }

        Member member = new Member();
        member.setId(memberInfo.getId()); // PK 설정
        member.setUserPw(userPw); // 기존 비밀번호
        member.setNewUserPw(newUserPw); // 신규 비밀번호
        member.setUserName(userName);
        member.setEmail(email);
        member.setPhone(phone);
        member.setBirthday(birthday);
        member.setGender(gender);
        member.setPostcode(postcode);
        member.setAddr1(addr1);
        member.setAddr2(addr2);

        String currentPhoto = memberInfo.getPhoto();

        if (currentPhoto != null && !currentPhoto.equals("")) {
            if (deletePhoto.equals("Y")) {
                try {
                    fileHelper.deleteFile(currentPhoto);
                } catch (Exception e) {

                }

                if (uploadItem != null) {
                    member.setPhoto(uploadItem.getFilePath());
                } else {
                    member.setPhoto(null);
                }
            } else {
                member.setPhoto(currentPhoto);
            }
        } else {
            if (uploadItem != null) {
                member.setPhoto(uploadItem.getFilePath());
            }
        }


        Member output = memberService.update(member);

        request.getSession().setAttribute("memberInfo", output);

        return null;
    }

    @DeleteMapping("/api/account/out")
    @SessionCheckHelper(enable = true)
    public Map<String, Object> out(
            HttpServletRequest request,
            @SessionAttribute("memberInfo") Member memberInfo,
            @RequestParam("password") String password) throws Exception {
        memberInfo.setUserPw(password);

        memberService.out(memberInfo);

        HttpSession session = request.getSession();
        session.invalidate();

        return null;

    }

}
