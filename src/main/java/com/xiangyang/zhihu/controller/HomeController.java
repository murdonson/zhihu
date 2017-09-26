package com.xiangyang.zhihu.controller;


import com.xiangyang.zhihu.model.*;
import com.xiangyang.zhihu.service.*;

import com.xiangyang.zhihu.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    FollowerService followerService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    long followerCount;
    long followerCount1;
    long followeeCount;
    long followeeCount1;
    int status;


    List<Map> maps;
    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page",defaultValue = "1") int page)
    {
        maps=new ArrayList<>();
        PageBean pageBean=new PageBean();
        pageBean.setSize(3);
        pageBean.setPage(page);
        Map map1=new HashMap();
        map1.put("start",pageBean.getStart());
        map1.put("size",pageBean.getSize());
        List<Question> questionList=questionService.selectIndexLatestQuestions(map1);
        int totalNum=questionService.getTotal();
        String pageCode=PageUtil.getPagination("/",totalNum,pageBean.getSize(),page);

        for(Question question:questionList)
        {
            Map map=new HashMap();
            map.put("question",question);
            User user=userService.selectByPrimaryKey(question.getUserId());
            map.put("user",user);
            long likecount=likeService.getlikeCount(question.getId(), EntityType.ENTITY_QUESTION);
            if(hostHolder.getUser()!=null)
            {
               status=likeService.getStatus(hostHolder.getUser().getId(),question.getId(), EntityType.ENTITY_QUESTION);
            }
            map.put("likecount",likecount);
            map.put("status",status);
            maps.add(map);

        }
        model.addAttribute("maps",maps);
        model.addAttribute("pageCode",pageCode);
        return "index";
    }


    @RequestMapping("/haoge")
    public String haoge()
    {
        return "haoge";
    }


    @RequestMapping("/user/{id}")
    public String personalindex(Model model,
                                @PathVariable("id") int userId,
                                @RequestParam(value = "page",defaultValue = "1")int page)
    {
            /*
            * page 表示当前分页的页数
            * */
        User user1=userService.selectByPrimaryKey(userId);
        model.addAttribute("user1",user1);
        maps=new ArrayList<>();

        PageBean pageBean=new PageBean();
        pageBean.setPage(page);
        pageBean.setSize(3);
        Map map1=new HashMap();
        map1.put("start",pageBean.getStart());
        map1.put("size",pageBean.getSize());
        map1.put("userId",user1.getId());

        List<Question> questionList=questionService.selectLatestQuestions(map1);
        int totalNum=questionService.getTotal();
        String pageCode=PageUtil.getPagination("/user/"+String.valueOf(userId),totalNum,pageBean.getSize(),page);
        for(Question question:questionList)
        {
            Map map=new HashMap();
            //更新评论个数
            question.setCommentCount(commentService.selectCommentsByQuestionId(question.getId()).size());
            map.put("question",question);
            User user=userService.selectByPrimaryKey(question.getUserId());
            map.put("user",user);
            maps.add(map);
        }


       int commentCount= commentService.selectCommentNumByUserId(userId);
        if(followerService.isFollowed(hostHolder.getUser().getId(),userId,EntityType.ENTITY_USER))
        {
            model.addAttribute("followed",true);
        }
        else {
            model.addAttribute("followed",false);
        }
        followeeCount=followerService.getFolloweeCount(userId, EntityType.ENTITY_USER);
        followerCount=followerService.getFollowerCount(EntityType.ENTITY_USER,userId);

        followeeCount1=followerService.getFolloweeCount(hostHolder.getUser().getId(),EntityType.ENTITY_USER);
        followerCount1=followerService.getFollowerCount(EntityType.ENTITY_USER,hostHolder.getUser().getId());

        model.addAttribute("maps",maps);
        model.addAttribute("commentCount",commentCount);
        model.addAttribute("pageCode",pageCode);
        model.addAttribute("followeeCount",followeeCount);
        model.addAttribute("followeeCount1",followeeCount1);
        model.addAttribute("followerCount",followerCount);
        model.addAttribute("followerCount1",followerCount1);
        return "profile";
    }



}
