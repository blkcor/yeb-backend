package com.chen.server.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author: blkcor
 * @DATE: 2022/3/26  12:04
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@RestController
@Api(value = "获取验证码")
public class KaptchaController {

    @Resource
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "验证码")
    @RequestMapping(value = "/captcha", method = RequestMethod.GET,produces = "image/jpeg")
    public void verification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = defaultKaptcha.createText();
        // store the text in the session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage image = defaultKaptcha.createImage(capText);
        ServletOutputStream os = response.getOutputStream();
        // write the data out
        ImageIO.write(image, "jpg", os);
        try {
            os.flush();
        } finally {
            os.close();
        }
    }
}
