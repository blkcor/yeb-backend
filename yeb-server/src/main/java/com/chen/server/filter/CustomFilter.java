package com.chen.server.filter;

import com.chen.server.mapper.MenuMapper;
import com.chen.server.pojo.Menu;
import com.chen.server.pojo.Role;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 根据请求的url分析请求的role
 *
 * @author: blkcor
 * @DATE: 2022/3/26  19:48
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    @Resource
    private MenuMapper menuMapper;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //拿到请求的URL
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> menus = menuMapper.getMenusByRole();
        for (Menu menu : menus) {
            //使用工具类判断两个url是否匹配
            //这里注意是模式串  后面是实际请求的路径
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                //匹配情况
                //将角色列表映射为角色名称的一个数组
                String[] names = menu.getRoleList().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(names);
            }
        }
        //没匹配的URL默认登录即可访问 ==> 除了规定的url 其他的url只要登录就可以访问  并赋予 ROLE_LOGIN 的权限
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
