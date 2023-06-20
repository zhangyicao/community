package com.yicao.community.Mapper;

import com.yicao.community.entity.User;
import com.yicao.community.mapper.DiscussPostMapper;
import com.yicao.community.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class DiscussPostMapperTest {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectDiscussPosts() {
        System.out.println(discussPostMapper.selectDiscussPosts(0, 0, 10));
    }

    @Test
    public void testSelectDiscussPostRows() {
        System.out.println(discussPostMapper.selectDiscussPostRows(0));
    }
}
