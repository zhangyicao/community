package com.yicao.community.controller;

import com.yicao.community.Service.DiscussPostService;
import com.yicao.community.Service.UserService;
import com.yicao.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, 0, 10);
        List<Map<String, Object>> discussPostAndUser = new ArrayList<>();
        for (DiscussPost discussPost : discussPostList) {
            Map<String, Object> map = new HashMap<>();
            map.put("post", discussPost);
            map.put("user", userService.findUserById(discussPost.getUserId()));
            discussPostAndUser.add(map);
        }
        model.addAttribute("discussPostAndUser", discussPostAndUser);
        return "/index";
    }
}
