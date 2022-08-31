package com.chen.server.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author: blkcor
 * @DATE: 2022/3/26  11:52
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Configuration
public class CaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha() {
        //验证码生成器
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        //配置
        Properties properties = new Properties();
        //是否有边框
        properties.setProperty("kaptcha.border", "yes");
        //设置边框颜色
        properties.setProperty("kaptcha.border.color", "255,177,176");
        //边框粗细度 默认为1
        //properties.setProperty("kaptcha.border.thickness","1");
        //验证码
        properties.setProperty("kaptcha.session.key", "code");
        //文本颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        //字体样式
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        //字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "35");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字符间距
        properties.setProperty("kaptcha.textproducer.char.space", "4");
        //验证码图片宽度
        properties.setProperty("kaptcha.image.width", "120");
        //验证码图片高度
        properties.setProperty("kaptcha.image.height", "55");
        //验证码配置类
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
