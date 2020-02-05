package com.epam.fitness.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.List;
import java.util.Locale;

@EnableWebMvc
@ComponentScan("com.epam.fitness")
public class SpringWebMvcConfig implements WebMvcConfigurer {

    private static final String TEMPLATE_LOADER_PATH = "/WEB-INF/views/";
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    private static final String VIEW_RESOLVER_PREFIX = "";
    private static final String VIEW_RESOLVER_SUFFIX = ".ftl";
    private static final String VIEW_RESOLVER_CONTENT_TYPE = "text/html; charset=utf-8";

    private static final String MESSAGE_SOURCE_BASENAME = "classpath:pages_content";
    private static final String MESSAGE_SOURCE_ENCODING = "UTF-8";
    private static final String CHANGE_LOCALE_PARAMETER = "lang";

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonMessageConverter = (MappingJackson2HttpMessageConverter) converter;
                ObjectMapper objectMapper = jsonMessageConverter.getObjectMapper();
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                break;
            }
        }
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
        viewResolver.setContentType(VIEW_RESOLVER_CONTENT_TYPE);
        return viewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath(TEMPLATE_LOADER_PATH);
        return freeMarkerConfigurer;
    }

    @Bean
    public MessageSource messageSource()  {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE_BASENAME);
        messageSource.setDefaultEncoding(MESSAGE_SOURCE_ENCODING);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver()  {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(DEFAULT_LOCALE);
        return localeResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName(CHANGE_LOCALE_PARAMETER);
        registry
                .addInterceptor(localeInterceptor)
                .addPathPatterns("/**");
    }
}
