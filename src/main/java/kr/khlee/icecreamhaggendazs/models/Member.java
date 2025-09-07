package kr.khlee.icecreamhaggendazs.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {
    private int id;
    private String userId;
    private String userPw;
    private String newUserPw;
    private String userName;
    private String email;
    private String phone;
    private String birthday;
    private String gender;
    private String postcode;
    private String addr1;
    private String addr2;
    private String photo;
    private String isOut;
    private String isAdmin;
    private String loginDate;
    private String regDate;
    private String editDate;
}
