package com.xiangyang.zhihu.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xiangyang.zhihu.model.Comment;
import com.xiangyang.zhihu.model.EntityType;
import com.xiangyang.zhihu.model.HostHolder;
import com.xiangyang.zhihu.model.Question;
import com.xiangyang.zhihu.service.CommentService;
import com.xiangyang.zhihu.service.QuestionService;
import com.xiangyang.zhihu.service.UserService;
import org.apache.catalina.Host;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @RequestMapping("/comment/add")
    public String addComment(Model model,
                             @RequestParam("qId") int qId,
                             @RequestParam("comment") String comment)
    {
        if(hostHolder.getUser()==null)
        {
            model.addAttribute("msg","请先登录(⊙o⊙)…");
            return "login";
        }
        Comment c=new Comment();
        c.setContent(comment);
        c.setCreatedDate(new Date());
        c.setEntityId(qId);
        c.setEntityType(EntityType.ENTITY_QUESTION);
        c.setStatus(0);
        c.setUserId(hostHolder.getUser().getId());
        commentService.addComment(c);
        Question question=questionService.selectQuestionById(c.getEntityId());
        question.setCommentCount(question.getCommentCount()+1);
        questionService.updataquestion(question);

        return "redirect:/question/"+String.valueOf(qId);
    }

    @RequestMapping("/answer/{userId}")
    public String getAnswer(Model model,
                            @PathVariable("userId") int userId)
    {
        List<Comment> list=commentService.selectCommentsByUserId(userId);
        List<Map> maps=new ArrayList<>();
        for(Comment comment:list)
        {
            Map map=new HashMap();
            Question question=questionService.selectQuestionById(comment.getEntityId());
            map.put("comment",comment);
            map.put("question",question);
            map.put("user",userService.selectByPrimaryKey(userId));
            maps.add(map);
        }
        model.addAttribute("maps",maps);
        return "answer";
    }

}
