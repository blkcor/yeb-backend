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
        //????????????token ???????????????csrf
        http.csrf()
                .disable()
                //??????token  ?????????session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //???????????????????????????
                .authorizeRequests()
                //??????????????? ?????????????????????????????????
                .anyRequest()
                .authenticated()
                //?????????????????? => ??????????????????url?????????
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O obj) {
                        obj.setAccessDecisionManager(customUrlDecisionManagerFilter);
                        obj.setSecurityMetadataSource(customFilter);
                        return obj;
                    }
                })
                .and()
                //????????????
                .headers()
                .cacheControl();

        http
                //????????????????????????token
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //???????????????????????????????????????????????????
                .exceptionHandling()
                //?????????
                .accessDeniedHandler(restAccessDeniedHandler)
                //????????? ???????????????
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if (null != admin) {
                //admin?????????UserDetails????????????
                admin.setRoleList(adminService.getRoleList(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("??????????????????????????????!");
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
