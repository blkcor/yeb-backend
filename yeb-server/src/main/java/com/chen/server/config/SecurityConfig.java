package com.chen.server.config;


import com.chen.server.diy.RestAccessDeniedHandler;
import com.chen.server.diy.RestAuthorizationEntryPoint;
import com.chen.server.filter.CustomFilter;
import com.chen.server.filter.CustomUrlDecisionManagerFilter;
import com.chen.server.filter.JwtAuthenticationFilter;
import com.chen.server.pojo.Admin;
import com.chen.server.service.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author: blkcor
 * @DATE: 2022/3/25  20:19
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private AdminService adminService;
    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Resource
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Resource
    private CustomUrlDecisionManagerFilter customUrlDecisionManagerFilter;
    @Resource
    private CustomFilter customFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/login",
                        "/error",
                        "/logout",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/doc.html",
                        "/webjars/**",
                        "/css/**",
                        "/js/**",
                        "/index.html",
                        "favicon.ico",
                        "/captcha");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //我们使用token 所以不需要csrf
        http.csrf()
                .disable()
                //基于token  不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //对哪些请求进行授权
                .authorizeRequests()
                //除了上面的 其他所有的请求都被拦截
                .anyRequest()
                .authenticated()
                //动态权限配置 => 添加我们写的url过滤器
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O obj) {
                        obj.setAccessDecisionManager(customUrlDecisionManagerFilter);
                        obj.setSecurityMetadataSource(customFilter);
                        return obj;
                    }
                })
                .and()
                //禁用缓存
                .headers()
                .cacheControl();

        http
                //过滤器拦截来验证token
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //添加自定义未登录和未授权的结果返回
                .exceptionHandling()
                //未授权
                .accessDeniedHandler(restAccessDeniedHandler)
                //未登录 （未认证）
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if (null != admin) {
                //admin实现了UserDetails这个接口
                admin.setRoleList(adminService.getRoleList(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("用户名或者密码不正确!");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
