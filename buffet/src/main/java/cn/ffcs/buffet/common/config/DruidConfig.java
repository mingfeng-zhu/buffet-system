package cn.ffcs.buffet.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: druid数据源配置
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/20 19:56
 */
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    /**
     *配置druid的监控
     *1.配置一个管理后台的Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        // /druid/*在/druid/下的所有请求都匹配到
        ServletRegistrationBean bean =
                new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        Map<String,String> initParams=new HashMap<>();
        initParams.put("loginUsername","admin");//登录后台时的用户名
        initParams.put("loginPassword","123456");//登录后台时的密码
        initParams.put("allow","");//允许谁登录,默认是谁都可以，写上localhost表示只允许localhost登录
        bean.setInitParameters(initParams);

        return bean;
    }

    /**
     * 2.配置一个web监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams=new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");//*.js,*.css,/druid/*这些请求不需要拦截
        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*"));//拦截请求,拦截所有请求
        return bean;
    }
}
