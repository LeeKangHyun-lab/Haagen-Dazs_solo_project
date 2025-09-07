package kr.khlee.icecreamhaggendazs.services;

import kr.khlee.icecreamhaggendazs.models.Member;

import java.util.List;

public interface MemberService {

    public void isUniqueUserId(Member input) throws Exception;

    public void isUniqueEmail(Member input) throws Exception;

    public Member join(Member input) throws Exception;

    public Member login(Member input) throws Exception;

    public Member findId(Member input) throws Exception;

    public void resetPw(Member input) throws Exception;

    public Member update(Member input) throws Exception;

    public void out(Member input) throws Exception;

    public List<Member> processOutMembers() throws Exception;
}
