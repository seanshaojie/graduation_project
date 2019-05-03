package com.example.utils;


import com.example.dto.LoginUser;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();//在ThreadLocal中取出当前用户
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {//匿名
                return null;
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                MethodDelegationBinder.BindingResolver.StreamWriting.toSystemOut();
                return (LoginUser) authentication.getPrincipal();
            }
        }

        return null;
    }

}
