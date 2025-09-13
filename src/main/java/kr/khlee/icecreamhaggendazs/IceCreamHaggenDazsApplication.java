package kr.khlee.icecreamhaggendazs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IceCreamHaggenDazsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IceCreamHaggenDazsApplication.class, args);
    }

}
