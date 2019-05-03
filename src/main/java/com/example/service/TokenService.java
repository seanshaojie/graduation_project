package com.example.service;

import com.example.dto.LoginUser;
import com.example.dto.Token;

/**@ClassName
 *@Description:
 *@Data 2019/3/24
 *Author censhaojie
 */

public interface TokenService {

    Token saveToken(LoginUser loginUser);

    LoginUser getLoginUser(String jwtSecret);

    public boolean deleteToken(String jwtToken);
}
