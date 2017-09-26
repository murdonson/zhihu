package com.xiangyang.zhihu.service;


import com.alibaba.fastjson.JSONObject;
import com.xiangyang.zhihu.dao.MessageMapper;
import com.xiangyang.zhihu.model.HostHolder;
import com.xiangyang.zhihu.model.Message;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageMapper messageMapper;


    public void addMessage(Message message)
    {
        messageMapper.insertSelective(message);
    }

    public List<Message> getConverstionList(int id)
    {
        return messageMapper.getConverstionList(id);
    }


    public List<Message> getMessageListByconversationId(String conversationId)
    {
        return messageMapper.getMessageListByconversationId(conversationId);
    }






}
