package com.yicao.community.Mapper;

import com.yicao.community.entity.User;
import com.yicao.community.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUser(){
        System.out.println(userMapper.selectById(102));
    }

    @Test
    public void testSelectByName(){
        System.out.println(userMapper.selectByName("zhangfei"));
    }

    @Test
    public void testSelectByEmail() {
        System.out.println(userMapper.selectByEmail("nowcoder25@sina.com"));
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("testesttd");
        user.setPassword("123123");
        user.setEmail("test@qq.com");
        user.setSalt("abc");
        user.setHeaderUrl("http://www.nowcoder.com/101.png");
        user.setCreateTime(new Date());
        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdateStatus(){
        int rows = userMapper.updateStatus(150, 1);
        System.out.println(rows);
    }

    @Test
    public void testUpdateHeader(){
        int rows = userMapper.updateHeader(150, "http://www.nowcoder.com/102.png");
        System.out.println(rows);
    }

    @Test
    public void testUpdatePassword(){
        int rows = userMapper.updatePassword(150, "hello");
        System.out.println(rows);
    }
}
