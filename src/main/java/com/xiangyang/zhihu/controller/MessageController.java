package com.xiangyang.zhihu.controller;


import com.alibaba.fastjson.JSONObject;
import com.xiangyang.zhihu.dao.MessageMapper;
import com.xiangyang.zhihu.model.HostHolder;
import com.xiangyang.zhihu.model.Message;
import com.xiangyang.zhihu.model.User;
import com.xiangyang.zhihu.service.MessageService;
import com.xiangyang.zhihu.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;

    @RequestMapping("/msg/add")
    @ResponseBody
    public String addMessage(@RequestParam("userId") int userId,
                             @RequestParam("content") String content)
    {
        JSONObject result=new JSONObject();
        if(hostHolder.getUser()==null)
        {
            result.put("code",1);
            return result.toString();
        }
        if(StringUtils.isBlank(content))
        {
            result.put("code",2);
            return result.toString();
        }
        Message message=new Message();
        message.setContent(content);
        message.setCreatedDate(new Date());
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(userId);
        message.setHasRead(0);
        message.setConversationId(message.getConversationId());
        messageService.addMessage(message);
        result.put("code",0);
        return result.toString();
    }

    @RequestMapping("/msg/list")
    public String messagelist(Model model)
    {
        int targetId=0;
       List<Message> list= messageService.getConverstionList(hostHolder.getUser().getId());
       List<Map> maps=new ArrayList<>();
       for(Message message:list)
       {

           if(message.getFromId()!=hostHolder.getUser().getId())
           {
               targetId=message.getFromId();
           }
           else {
               targetId=message.getToId();
           }
           User user=userService.selectByPrimaryKey(targetId);
           int total=message.getId();
           Map map=new HashMap();
           map.put("user",user);
           map.put("message",message);
           map.put("total",total);
           maps.add(map);
       }
       model.addAttribute("maps",maps);
        return "messagelist";
    }



    @RequestMapping("/msg/detail/{id}")
    public String getMessageDetail(Model model,
                                   @PathVariable("id") String conversationId)
    {
        int targetId=0;

       List<Message> list= messageService.getMessageListByconversationId(conversationId);
       List<Map> maps=new ArrayList<>();
       for(Message message:list)
       {


           if(message.getFromId()!=hostHolder.getUser().getId())
           {
               targetId=message.getFromId();
           }
           else {
               targetId=message.getToId();
           }
           User user=userService.selectByPrimaryKey(targetId);
           int total=message.getId();
           Map map=new HashMap();
           map.put("user",user);
           map.put("message",message);
           maps.add(map);
       }
       model.addAttribute("maps",maps);
        return "msgdetail";
    }
}
