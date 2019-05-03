package com.example.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**@ClassName
 *@Description: 字符串转化工具类
 *@Data 2019/4/8
 *Author censhaojie
 */
public class StrUtil {
    /**@ClassName str2hump
     *@Description:       字符串转为驼峰
     *@Data 2019/4/8
     *Author censhaojie
     */
    public static String str2hump(String str){
        StringBuffer buffer = new StringBuffer();
        if(str != null && str.length() > 0){
            if(str.contains("_")){
                String [] chars = str.split("_");
                int size = chars.length;
                if(size > 0){
                    List<String> list = Lists.newArrayList();
                    for(String s:chars){
                        if(s != null &&s.trim().length() > 0){
                            list.add(s);
                        }
                    }
                    size = list.size();
                    if(size > 0){
                        buffer.append(list.get(0));
                        for(int i = 1;i<size;i++){
                            String s = list.get(i);
                            buffer.append(s.substring(0,1).toUpperCase());
                            if(s.length() > 1){
                                buffer.append(s.substring(1));
                            }
                        }
                    }
                }
            }else{
                buffer.append(str);
            }
        }
        return buffer.toString();
    }
}
