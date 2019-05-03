package com.example.config;


import com.example.dto.LoginUser;
import com.example.dto.ResponseInfo;
import com.example.dto.Token;
import com.example.filter.TokenFilter;
import com.example.service.TokenService;
import com.example.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**@ClassName
 *@Description:     Spring security 处理器
 *@Data 2019/3/21
 *Author censhaojie
 */
@Configuration
public class SecurityHandlerConfig {

    @Autowired
    private TokenService tokenService;

/**@ClassName loginSuccessHandler
 *@Description:  登录成功的处理
 *@Data 2019/3/21
 *Author censhaojie
 */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                LoginUser loginUser=(LoginUser) authentication.getPrincipal();
                Token token = tokenService.saveToken(loginUser);
                ResponseUtil.responseJson(response, HttpStatus.OK.value(),token);
            }
        };
    }

 /**@ClassName loginFailureHandler
  *@Description:   登录失败的处理
  *@Data 2019/3/21
  *Author censhaojie
  */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                                AuthenticationException exception) throws IOException, ServletException {
                String msg = null;
                if (exception instanceof BadCredentialsException) {
                    msg = "密码错误";
                } else {
                    msg = exception.getMessage();
                }
                ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", msg);
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
            }
        };

    }


/**@ClassName authenticationEntryPoint
 *@Description:      未登录 401
 *@Data 2019/3/25
 *Author censhaojie
 */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", "请先登录");
                ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
            }
        };
    }

/**@ClassName logoutSuccessHandler
 *@Description:       退出登录
 *@Data 2019/3/25
 *Author censhaojie
 */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                ResponseInfo info = new ResponseInfo(HttpStatus.OK.value() + "", "退出成功");
                String token=TokenFilter.getToken(request);
                tokenService.deleteToken(token);//在redis中删除数据
                ResponseUtil.responseJson(response,HttpStatus.OK.value(),info);
            }
        };
    }

}
