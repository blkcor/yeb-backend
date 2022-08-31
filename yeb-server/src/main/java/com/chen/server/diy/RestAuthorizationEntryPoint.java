package com.chen.server.diy;

import com.chen.server.common.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author: blkcor
 * @DATE: 2022/3/25  23:11
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 * @Description: 自定义返回结果  用于未登录或者token失效的时候返回的结果
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //设置响应的编码
        response.setCharacterEncoding("UTF-8");
        //设置返回的格式
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("未登录或者token失效,请重新登录");
        bean.setCode("401");
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
