package com.yicao.community.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
public class TestMailClient {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSendMail() {
        mailClient.sendMail("yicaozyq@163.com", "Test", "Hello World!");
    }

    @Test
    public void testSendMailByEngine() {
        Context context = new Context();
        context.setVariable("username", "sunday");

        String content = templateEngine.process("/mail/demo", context);
        mailClient.sendMail("yicaozyq@163.com", "Welcome", content);
    }
}
