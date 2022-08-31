package com.chen.server.filter;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限控制
 *
 * @author: blkcor
 * @DATE: 2022/3/26  21:59
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Component
public class CustomUrlDecisionManagerFilter implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            //当前URL所需的角色
            String needRole = configAttribute.getAttribute();
            //判断角色是否是登录即可访问的角色 ==> 在CustomFilter中设置
            if ("ROLE_LOGIN".equals(needRole)) {
                //下面属于没有登录的逻辑
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("尚未登陆,请登录");
                } else {
                    //decide生效  可以访问
                    return;
                }
            }
            //下面是已经登录的逻辑
            //判断用户角色是否为Url所需的角色
            //拿到所用的权限(角色) ==> 对应我们在Admin中设置的角色权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    //decide生效  允许访问
                    return;
                }
            }
        }
        //没有访问该Url的权限
        throw new AccessDeniedException("权限不足,请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
