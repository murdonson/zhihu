package com.xiangyang.zhihu.controller;


import com.alibaba.fastjson.JSONObject;
import com.xiangyang.zhihu.model.Comment;
import com.xiangyang.zhihu.model.HostHolder;
import com.xiangyang.zhihu.model.Question;
import com.xiangyang.zhihu.service.CommentService;
import com.xiangyang.zhihu.service.QuestionService;
import com.xiangyang.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;



    @RequestMapping("/question/add")
    @ResponseBody
    public String addQuestion(Model model,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content)
    {
        JSONObject result=new JSONObject();
        if(hostHolder.getUser()==null)
        {
            result.put("code",1);
            return result.toJSONString();
        }

        Question question=new Question();
        question.setCommentCount(0);
        question.setContent(content);
        question.setCreatedDate(new Date());
        question.setUserId(hostHolder.getUser().getId());
        question.setTitle(title);
        if(questionService.addQuestion(question)>0)
        {
            result.put("code",0);
            return result.toJSONString();
        }
        result.put("code",2);
        return result.toJSONString();
    }

    @RequestMapping("/question/{qid}")
    public String detail(Model model,
                         @PathVariable("qid") int qid)
    {
        List<Map> maps=new ArrayList<>();
        Question question=questionService.selectQuestionById(qid);
        List<Comment> commentList=commentService.selectCommentsByQuestionId(qid);

        for(Comment comment:commentList)
        {
            Map map=new HashMap();
            map.put("user",hostHolder.getUser());
            map.put("comment",comment);
            maps.add(map);
        }
        model.addAttribute("maps",maps);
        model.addAttribute("question",question);

        return "detail";
    }


}
