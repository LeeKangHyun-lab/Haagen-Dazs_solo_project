package kr.khlee.icecreamhaggendazs.services.impl;

import kr.khlee.icecreamhaggendazs.exceptions.AlreadyExistsException;
import kr.khlee.icecreamhaggendazs.exceptions.ServiceNoResultException;
import kr.khlee.icecreamhaggendazs.mappers.members.MemberMapper;
import kr.khlee.icecreamhaggendazs.models.Member;
import kr.khlee.icecreamhaggendazs.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public void isUniqueUserId(Member input) throws Exception{
        if(memberMapper.selectCount(input) > 0){
            throw new AlreadyExistsException("사용할 수 없는 아이디 입니다.");
        }
    }

    @Override
    public void isUniqueEmail(Member input) throws Exception{
        if(memberMapper.selectCount(input) > 0){
            throw new AlreadyExistsException("사용할 수 없는 이메일입니다.");
        }
    }

    @Override
    public Member join(Member input) throws Exception{
        Member temp1 = new Member();
        temp1.setUserId(input.getUserId());
        this.isUniqueUserId(temp1);

        Member temp2 = new Member();
        temp2.setEmail(input.getEmail());
        this.isUniqueEmail(temp2);

        if(memberMapper.insert(input) == 0){
            throw new ServiceNoResultException("저장된 Member 데이터가 없습니다.");

        }

        return memberMapper.selectItem(input);
    }

    @Override
    public Member login(Member input) throws Exception{
        Member output = memberMapper.login(input);

        if(output == null){
            throw new ServiceNoResultException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
        memberMapper.updateLoginDate(output);

        return output;
    }

    @Override
    public Member findId(Member input) throws Exception{
        Member output = memberMapper.findId(input);

        if(output == null){
            throw new Exception("조회된 아이디가 없습니다.");
        }

        return output;
    }

    public void resetPw(Member input) throws Exception{
        if(memberMapper.resetPw(input) == 0){
            throw new Exception("아이디와 이메일을 확인하세요.");
        }
    }

    @Override
    public Member update(Member input) throws Exception{
        if(memberMapper.update(input) == 0){
            throw new Exception("현재 비밀번호를 확인하세요.");
        }

        return memberMapper.selectItem(input);
    }

    @Override
    public void out(Member input) throws Exception{
        if(memberMapper.out(input) == 0){
            throw new ServiceNoResultException("회원 탈퇴에 실패했습니다. 비밀번호가 잘못되었거나 가입되어 있지 않은 회원입니다.");
        }
    }

    @Override
    public List<Member> processOutMembers() throws Exception{
        List<Member> output = null;

        output = memberMapper.selectOutMembersPhoto();

        memberMapper.deleteOutMembers();

        return output;
    }
}
