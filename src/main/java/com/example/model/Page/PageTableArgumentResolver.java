package com.example.model.Page;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**   分页、查询参数解析
 *@Description:  一个切面 HandlerMethodArgumentResolver处理函数参数的分解器
 *@Data 2019/3/29
 *Author censhaojie
 */

public class PageTableArgumentResolver implements HandlerMethodArgumentResolver {



    /**@ClassName supportsParameter
     *@Description:   测试此 service 是否能使用指定的参数。如果此服务不能使用该参数，则返回 false。如果此服务能使用该参数、快速测试不可行或状态未知，则返回 true
     *@Data 2019/3/29
     *Author censhaojie
     * parameter    MethodParameter方法参数对象 通过它可以获取该方法参数上的一些信息 如方法参数中的注解信息等
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clas = parameter.getParameterType(); //Class<>信息类。在运行时跟踪类，掌握类的全部信息
        return clas.isAssignableFrom(PageTableRequest.class); //判断PageTableRequest.class是不是clas子类或者子接口
    }


    /**@ClassName resolveArgument
     *@Description:       该方法就是对参数的解析
     *@Data 2019/3/29
     *Author censhaojie
     * WebRequest是Spring Web MVC提供的统一请求访问接口
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request=webRequest.getNativeRequest(HttpServletRequest.class);
        PageTableRequest tableRequest=new PageTableRequest();
        Map<String,String[]> param=request.getParameterMap();
        if(param.containsKey("start")){//判断param中是否有start这个键
            tableRequest.setOffset(Integer.parseInt(request.getParameter("start")));
        }
        if(param.containsKey("length")){
            tableRequest.setLimit(Integer.parseInt(request.getParameter("length")));
        }
        Map<String,Object> map= Maps.newHashMap();//google的guava.jar提供的写法
        tableRequest.setParams(map);

        param.forEach((k,v)->{
            if(v.length==1){
                map.put(k,v[0]);
            }else{
                map.put(k, Arrays.asList(v));
            }
        });
        setOrderBy(tableRequest, map);
        removeParam(tableRequest);
        return tableRequest;


    }

    private void removeParam(PageTableRequest tableRequest) {
        Map<String, Object> map = tableRequest.getParams();

        if (!CollectionUtils.isEmpty(map)) {
            Map<String, Object> param = new HashMap<>();
            map.forEach((k, v) -> {
                if (k.indexOf("[") < 0 && k.indexOf("]") < 0 && !"_".equals(k)) {
                    param.put(k, v);
                }
            });

            tableRequest.setParams(param);
        }
    }

    /**
     * 从datatables分页请求数据中解析排序
     *
     * @param tableRequest
     * @param map
     */
    private void setOrderBy(PageTableRequest tableRequest, Map<String, Object> map) {
        StringBuilder orderBy = new StringBuilder();
        int size = map.size();
        for (int i = 0; i < size; i++) {
            String index = (String) map.get("order[" + i + "][column]");
            if (StringUtils.isEmpty(index)) {
                break;
            }
            String column = (String) map.get("columns[" + index + "][data]");
            if (StringUtils.isBlank(column)) {
                continue;
            }
            String sort = (String) map.get("order[" + i + "][dir]");

            orderBy.append(column).append(" ").append(sort).append(", ");
        }

        if (orderBy.length() > 0) {
            tableRequest.getParams().put("orderBy",
                    " order by " + StringUtils.substringBeforeLast(orderBy.toString(), ","));
        }
    }

}
