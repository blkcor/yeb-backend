package com.chen.server.filter;

import com.chen.server.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: blkcor
 * @DATE: 2022/3/25  20:34
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 * OncePerRequestFilter 是 ioc容器的一部分
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //拦截请求  进行操作
        String autHeader = httpServletRequest.getHeader(tokenHeader);
        if (null != autHeader && autHeader.startsWith(tokenHead)) {
            //拿到token
            String token = autHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUsernameFromToken(token);

            if (username != null && null == SecurityContextHolder.getContext().getAuthentication()) {
                //用户名存在 但是上下文中没有  我们在登录的时候设置上下文  ==> 用户没有登录
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    //token有效
                    //我们重新登录 => userDetails对象 ==> 重新设置权限
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
