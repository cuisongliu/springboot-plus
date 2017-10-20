/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 cuisongliu@qq.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.cuisongliu.springboot.plus.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.IOUtils;
import com.cuisongliu.springboot.plus.converters.StringToDateConverter;
import com.cuisongliu.springboot.plus.filter.XssFilter;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * springMVC 扩展类
 *
 * @author cuisongliu
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.cuisongliu.springboot.controller"})
@Import(SpringConfig.class)
public abstract class SpringMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 1、 extends WebMvcConfigurationSupport
     * 2、重写下面方法;
     * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配；
     * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false)
                .setUseTrailingSlashMatch(true);
    }

    /**
     * 设置xssFilter
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");// 忽略资源
        return filterRegistrationBean;
    }

    @Bean
    public Filter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        fastJsonConfig.setCharset(IOUtils.UTF8);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.TEXT_HTML);
        fastConverter.setSupportedMediaTypes(mediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //设置默认
        configurer.favorPathExtension(true)//The order of checking is always path extension, parameter, Accept header.
                .ignoreUnknownPathExtensions(true)
                .favorParameter(false)
                .ignoreAcceptHeader(true)
                .useJaf(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("2MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("20MB");
        //Sets the directory location where files will be stored.
        //factory.setLocation("路径地址");
        configMultipart(factory);
        return factory.createMultipartConfig();
    }

    /**
     * 设置文件的相关配置
     *
     * @param multipartConfigFactory
     */
    protected abstract void configMultipart(MultipartConfigFactory multipartConfigFactory);

    @Bean
    public ConversionService conversionService() {
        FormattingConversionServiceFactoryBean bean = new FormattingConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringToDateConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }

    @Bean
    public ConfigurableWebBindingInitializer webBindingInitializer(ConversionService conversionService) {
        ConfigurableWebBindingInitializer webBindingInitializer = new ConfigurableWebBindingInitializer();
        webBindingInitializer.setConversionService(conversionService);
        return webBindingInitializer;
    }

    @Bean
    public RequestMappingHandlerAdapter mappingHandlerAdapter(ConfigurableWebBindingInitializer webBindingInitializer) {
        RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
        adapter.setWebBindingInitializer(webBindingInitializer);
        return adapter;
    }


    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                //设置错误页面
                Boolean isJson = setErrorPages(container);
                String prefix = "";
                if (isJson) prefix="json/";
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/"+prefix+"404"));
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/"+prefix+"404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/"+prefix+"500"));
                container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/"+prefix+"401"));
                container.addErrorPages(new ErrorPage(Throwable.class, "/error/"+prefix+"500"));
            }
        };
    }

    /**
     * 设置错误页面的信息<br/>
     *
     * @param container
     * @return  是否为json
     */
    protected abstract Boolean setErrorPages(ConfigurableEmbeddedServletContainer container);
}
