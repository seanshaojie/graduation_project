package com.example.config;

import com.example.model.Page.PageTableArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;
/**@ClassName
 *@Description: 由于datatable传入的参数无法用自带的解析器解析，只能自定义一个WebMvcConfigurer来启动resolve
 *@Data 2019/3/29
 *Author censhaojie
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**@ClassName corsConfigurer
     *@Description:      跨域支持
     *@Data 2019/3/29
     *Author censhaojie
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

    /**@ClassName tableArgumentResolver
     *@Description:    注入参数解析器
     *@Data 2019/3/29
     *Author censhaojie
     */
    @Bean
    public PageTableArgumentResolver tableHandlerMethodArgumentResolver(){
        return new PageTableArgumentResolver();
    }

    /**@ClassName addArgumentResolvers
     *@Description:     添加类型转换器和格式化器  
     *@Data 2019/3/29 
     *Author censhaojie
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tableHandlerMethodArgumentResolver());
    }
    
    /**@ClassName 
     *@Description:  上传文件根路径     
     *@Data 2019/3/29 
     *Author censhaojie
     */
    @Value("${files.path}")
    private String filesPath;


    /**@ClassName addResourceHandlers
     *@Description:        添加静态资源
     *@Data 2019/3/29 
     *Author censhaojie
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**") // /statics/**表示在磁盘statics目录下的所有资源会被解析为以下的路径
                .addResourceLocations(ResourceUtils.FILE_URL_PREFIX + filesPath + File.separator);//媒体资源
    }
}
