package kr.khlee.icecreamhaggendazs.schedulers;


import java.util.List;
import kr.khlee.icecreamhaggendazs.helpers.FileHelper;
import kr.khlee.icecreamhaggendazs.models.Member;
import kr.khlee.icecreamhaggendazs.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class AccountScheduler {
    @Value("${upload.dir")
    private String uploadDir;

    @Value("${thumbnail.width}")
    private int thumbnailWidth;

    @Value("${thumbnail.height}")
    private int thumbnailHeight;

    private final FileHelper fileHelper;

    private final MemberService memberService;


//  @Scheduled(cron = "0 0 4 * * ?")
    @Scheduled(cron = "15 * * * * ?")
//  @Scheduled(cron="0 0/30 * * * ?")
    public void processOutMembers() throws Exception{
        log.debug("탈퇴 회원 정리 시작");

        List<Member> outMembers = null;

        try{
            log.debug("탈퇴 회원 조회 및 삭제");
            outMembers = memberService.processOutMembers();
        } catch(Exception e){
            log.error("탈퇴 회원 조회 및 삭제 실패", e);
            return;
        }

        for (int i = 0; i < outMembers.size(); i++) {
            Member m = outMembers.get(i);
            fileHelper.deleteFile(m.getPhoto());
        }
    }

}
