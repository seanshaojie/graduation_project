package com.example.service.impl;

import com.example.dto.LoginUser;
import com.example.dto.Token;
import com.example.service.SysLogService;
import com.example.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.apache.commons.collections4.MapUtils;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Primary;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;



import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate<String,LoginUser> redisTemplate;

    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    private static Key KEY = null;

    @Autowired
    private SysLogService logService;

    /**
     *@Description:   私钥
     *@Data 2019/3/24
     *Author censhaojie
     */
    @Value("${token.jwtSecret}")
    private String jwtSecret;

    /**
     *@Description:   token过期秒数
     *@Data 2019/3/24
     *Author censhaojie
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;




    @Override
    public Token saveToken(LoginUser loginUser) {
        loginUser.setToken(UUID.randomUUID().toString());
        cacheLoginUser(loginUser);
        String jwtToken = createJWTToken(loginUser);
        logService.save(loginUser.getId(), "登陆", true, null);
        return new Token(jwtToken,loginUser.getLoginTime());
    }

    private void cacheLoginUser(LoginUser loginUser){
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime()+expireSeconds*1000);
        redisTemplate.boundValueOps(getToken(loginUser.getToken())).set(loginUser,expireSeconds,TimeUnit.SECONDS);
    }
    /**@ClassName createJWTToken
     *@Description:     创建JWT 三个段落
     *@Data 2019/3/25
     *Author censhaojie
     */
    private String createJWTToken(LoginUser loginUser){
        Map<String, Object> claims = new HashMap<>();//数据声明
        claims.put(LOGIN_USER_KEY,loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户
        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256,getKeyInstance()).compact();
        return jwtToken;
    }
    private String getToken(String uuid){
        return "token:"+uuid;
    }
    private Key getKeyInstance(){
        if (KEY == null) {
            synchronized (TokenServiceImpl.class) {
                if (KEY == null) {
                    byte[] apiKeySecretBytes=DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY=new SecretKeySpec(apiKeySecretBytes,SignatureAlgorithm.HS256.getJcaName());//采用hs256算法得到的秘钥
                }
            }
        }
        return KEY;
    }

    @Override
    public LoginUser getLoginUser(String jwtSecret){
        String uuid=getUUIDFromJWT(jwtSecret);
        if (uuid != null) {
            return  redisTemplate.boundValueOps(getToken(uuid)).get();

        }
        return null;
    }
    private String getUUIDFromJWT(String jwtSecret) {
        if("null".equals(jwtSecret)|| StringUtils.isBlank(jwtSecret)){
            return null;
        }
        try{
            Map<String,Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtSecret).getBody();
            return MapUtils.getString(jwtClaims,LOGIN_USER_KEY);
        }catch(ExpiredJwtException e){

        }
        return null;
    }


    public boolean deleteToken(String jwtToken)  {
        String uuid=getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            String key=getToken(uuid);
            LoginUser loginUser =redisTemplate.opsForValue().get(key);
            if (loginUser != null) {
                redisTemplate.delete(key);
                logService.save(loginUser.getId(), "退出", true, null);
                return true;
            }
        }
        return false;
    }
}
