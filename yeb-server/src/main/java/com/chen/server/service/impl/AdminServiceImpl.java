package com.chen.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chen.server.common.RespBean;
import com.chen.server.dto.req.AdminLogin;
import com.chen.server.mapper.RoleMapper;
import com.chen.server.pojo.Admin;
import com.chen.server.mapper.AdminMapper;
import com.chen.server.pojo.Role;
import com.chen.server.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.server.utils.AdminUtils;
import com.chen.server.utils.JwtTokenUtil;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author blkcor
 * @since 2022-05-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 登录之后返回token
     *
     * @param adminLogin
     * @param request
     * @return
     */
    @Override
    @Transactional
    public RespBean login(AdminLogin adminLogin, HttpServletRequest request) {
        //验证码校验
        String captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(adminLogin.getCode())) {
            return RespBean.error("验证码不正确");
        }
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLogin.getUsername());
        //用户名密码不匹配
        if (null == userDetails || !passwordEncoder.matches(adminLogin.getPassword(), userDetails.getPassword())) {
            return RespBean.error("用户名或者密码错误!");
        }
        //用户被禁用
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用,请联系管理员!");
        }
        //将登录的对象放入SpringSecurity的上下文中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //登陆成功 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登陆成功", tokenMap);
    }

    @Override
    public Admin getAdminByUserName(String username) {
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
        return admin;
    }

    @Override
    public List<Role> getRoleList(Integer adminId) {
        return roleMapper.getRoleList(adminId);
    }

    @Override
    public List<Admin> getAllAdmins(String keyWord) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keyWord);
    }
}
