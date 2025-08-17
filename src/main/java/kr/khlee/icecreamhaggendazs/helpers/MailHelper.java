package kr.khlee.icecreamhaggendazs.helpers;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class MailHelper {
    private JavaMailSender javaMailSender = null;

    private String senderName = null;

    private String senderEmail = null;



    @Autowired
    public MailHelper(JavaMailSender javaMailSender,
                      @Value("${mailhelper.sender.name}") String senderName,
                      @Value("${mailhelper.sender.email}") String senderEmail) {
        this.javaMailSender = javaMailSender;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
    }

    public void sendMail(String receiverName, String receiverEmail, String subject, String content) throws Exception {
        log.debug("-------------------------------------");
        log.debug(String.format("ReceiverName: %s", receiverName));
        log.debug(String.format("ReceiverEmail: %s", receiverEmail));
        log.debug(String.format("Subject: %s", subject));
//        log.debug(String.format("Content: %s", content));
        log.debug("-------------------------------------");

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try{
            helper.setSubject(subject);
            helper.setText(content,true);
            helper.setTo(new InternetAddress(receiverEmail, receiverName, StandardCharsets.UTF_8.name()));
            helper.setFrom(new InternetAddress(senderEmail, senderName, StandardCharsets.UTF_8.name()));

            javaMailSender.send(message);
        }catch (MessagingException e) {
            log.error("메일 발송 정보 설정 실패",e);
        } catch (UnsupportedEncodingException e) {
            log.error("지원하지 않는 인코딩",e);
        } catch (Exception e){
            log.error("알 수 없는 오류",e);
            throw e;
        }
    }

    public void sendMail(String receiverEmail, String subject, String content) throws Exception {
        this.sendMail(null, receiverEmail, subject, content);
    }
}
