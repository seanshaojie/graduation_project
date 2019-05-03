package com.example.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**@ClassName
 *@Description:   一个把数据传回前端的工具类
 *@Data 2019/3/21
 *Author censhaojie
 */
public class ResponseUtil {

    public static void responseJson(HttpServletResponse response, int status, Object data) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*"); //可以允许跨域的数据交流
            response.setHeader("Access-Control-Allow-Methods", "*");//所有的请求方式都允许
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);

            response.getWriter().write(JSONObject.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
