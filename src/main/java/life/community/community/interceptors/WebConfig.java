package life.community.community.interceptors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SectionInterceptor sectionInterceptor;

    @Autowired
    private UnReadCountInterceptor unReadCountInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sectionInterceptor)
                .addPathPatterns("/publish", "/profile/**", "/comment", "/notification/**");

        registry.addInterceptor(unReadCountInterceptor)
                .addPathPatterns("/**");
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(104857600);
        return commonsMultipartResolver;
    }
}
