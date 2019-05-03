package com.example.dto;

import java.io.Serializable;

public class Token implements Serializable {

    private static final long serialVersionUID = 484076838641809642L;

    private String token;
    /**
     *@Description:设置的一个登录的时间戳
     *@Data 2019/3/24
     *Author censhaojie
     */

    private Long loginTime;

    public Token(String token,Long loginTime){
        super();
        this.token=token;
        this.loginTime=loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }
}
