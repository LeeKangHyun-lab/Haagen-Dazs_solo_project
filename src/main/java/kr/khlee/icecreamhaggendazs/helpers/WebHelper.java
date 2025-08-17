package kr.khlee.icecreamhaggendazs.helpers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Component
public class WebHelper {

    private HttpServletRequest request;
    private HttpServletResponse response;

    private String cookieDomain;
    private String cookiePath;

    @Autowired
    public WebHelper(HttpServletRequest request, HttpServletResponse response,
                     @Value("${spring.cookie.domain}") String cookieDomain,
                     @Value("${spring.cookie.path}") String cookiePath) {
        this.request = request;
        this.response = response;
    }

    /**
     * 클라이언트의 IP 주소를 가져오는 메서드
     * 여러 가지 방법으로 시도하고 가장 적합한 IP 주소를 반환합니다.
     *
     * @return IP 주소
     */
    public String getClientIp() {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public void writeCookie(String name, String value, int maxAge){
        if(value != null && !value.equals("")){
            try{
                value= URLEncoder.encode(value,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Cookie cookie = new Cookie(name, value);

        cookie.setDomain(this.cookieDomain);

        cookie.setPath(this.cookiePath);

        cookie.setHttpOnly(true);

        if(maxAge != 0){
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);

    }


    public void deleteCookie(String name){
        this.writeCookie(name,null,-1);
    }
}