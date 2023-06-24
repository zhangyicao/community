package com.yicao.community.controller;

import com.google.code.kaptcha.Producer;
import com.yicao.community.Service.UserService;
import com.yicao.community.constant.ActivationStatus;
import com.yicao.community.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${community.login.expiredTimeSecondMax}")
    private int expiredTimeSecondMax;

    @Value("${community.login.expiredTimeSecondMin}")
    private int expiredTimeSecondMin;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping(path = "/register", method = {RequestMethod.GET})
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = {RequestMethod.GET})
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "/register", method = {RequestMethod.POST})
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功，我们已经向您的邮箱发送了一封激活邮件，请尽快激活！");
            model.addAttribute("target", "/login");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    @RequestMapping(path = "/activation/{userId}/{code}", method = {RequestMethod.GET})
    public String activate(Model model, @PathVariable("userId") int userid, @PathVariable("code") String code) {
        ActivationStatus status = userService.activation(userid, code);
        if (status == ActivationStatus.SUCCESS) {
            model.addAttribute("msg", "激活成功，您的账号已经可以正常使用了！");
            model.addAttribute("target", "/login");
        } else if (status == ActivationStatus.REPEAT) {
            model.addAttribute("msg", "无效操作，该账号已经激活过了！");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败，您提供的激活码不正确！");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }

    @RequestMapping(path = "/kaptcha", method = {RequestMethod.GET})
    public void getKaptcha(HttpServletResponse resp, HttpSession session) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("kaptcha", text);

        // 将图片输出给浏览器
        resp.setContentType("image/png");
        try {
            OutputStream os = resp.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (Exception e) {
            logger.error("响应验证码失败：" + e.getMessage());
        }
    }

    @RequestMapping(path = "/login", method = {RequestMethod.POST})
    public String login(String username, String password, String code,@RequestParam(value = "remember_me", required = false) boolean rememberMe,
                        Model model, HttpSession session, HttpServletResponse resp) {
        // 检查验证码
        String kaptcha = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("codeMsg", "验证码不正确！");
            return "/site/login";
        }

        // 检查账号密码
        int expiredSeconds = rememberMe ? expiredTimeSecondMax : expiredTimeSecondMin;
        Map<String, Object> map = userService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            // 将ticket存入cookie
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            resp.addCookie(cookie);
            return "redirect:/index";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }

    }

    @RequestMapping(path = "/logout", method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/login";
    }

}
