package com.example.filter;


import com.example.dto.LoginUser;
import com.example.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**@ClassName
 *@Description: 定义一个拦截器，当所有的请求都会执行doFilterInternal这个拦截器
 *@Data 2019/3/25
 *Author censhaojie
 */
@Component  /*把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>*/
public class TokenFilter extends OncePerRequestFilter {
    private static final String TOKEN_KEY = "token";

    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getToken(request);
        if (StringUtils.isNotBlank(token)) {
            LoginUser loginUser = tokenService.getLoginUser(token);
            if (loginUser != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser,
                        null, loginUser.getAuthorities());//authentication
                SecurityContextHolder.getContext().setAuthentication(authentication);//保存在ThreadLocal中
            }
        }

        filterChain.doFilter(request, response);
    }




   /**@ClassName getToken
    *@Description:   根据参数或者header获取token
    *@Data 2019/3/25
    *Author censhaojie
    */
    public static String getToken(HttpServletRequest request){
        String token = request.getParameter(TOKEN_KEY);
        if(StringUtils.isBlank(token)){
            token=request.getHeader(TOKEN_KEY);
        }
        return token;
    }
}
