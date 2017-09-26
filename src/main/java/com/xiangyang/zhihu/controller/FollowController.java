package com.xiangyang.zhihu.controller;


import com.xiangyang.zhihu.model.EntityType;
import com.xiangyang.zhihu.model.HostHolder;
import com.xiangyang.zhihu.model.User;
import com.xiangyang.zhihu.service.FollowerService;
import com.xiangyang.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {
    @Autowired
    FollowerService followerService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;

    @RequestMapping("/followuser")
    public String follow(@RequestParam("userId") int userId)
    {
        followerService.follow(hostHolder.getUser().getId(),userId, EntityType.ENTITY_USER);
        return "redirect:/user/"+String.valueOf(userId);
    }

    @RequestMapping("/un")
    public String un(@RequestParam("userId") int userId)
    {
        followerService.unfollow(hostHolder.getUser().getId(),userId, EntityType.ENTITY_USER);
        return "redirect:/user/"+String.valueOf(userId);
    }


    @RequestMapping("/followerlist/{userId}")
    public String getFollowers(Model model,
                               @PathVariable("userId") int userId)
    {
        List<Integer> list=followerService.getFollowers(userId,EntityType.ENTITY_USER);
        List<Map> maps=new ArrayList<>();
        for(int integer:list)
        {
            Map map=new HashMap();
            User user=userService.selectByPrimaryKey(integer);
            map.put("user",user);
            maps.add(map);
        }
        model.addAttribute("maps",maps);
        return "followerlist";
    }



    @RequestMapping("/followeelist/{userId}")
    public String getFollowees(Model model,
                               @PathVariable("userId") int userId)
    {
        List<Integer> list=followerService.getFollowees(userId,EntityType.ENTITY_USER);
        List<Map> maps=new ArrayList<>();
        for(int integer:list)
        {
            Map map=new HashMap();
            User user=userService.selectByPrimaryKey(integer);
            map.put("user",user);
            maps.add(map);
        }
        model.addAttribute("maps",maps);
        return "followeelist";
    }



}
