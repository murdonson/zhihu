package com.xiangyang.zhihu.service;

import antlr.StringUtils;
import com.xiangyang.zhihu.dao.LoginTicketMapper;
import com.xiangyang.zhihu.dao.UserMapper;
import com.xiangyang.zhihu.model.LoginTicket;
import com.xiangyang.zhihu.model.User;
import com.xiangyang.zhihu.util.WenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    LoginTicketMapper loginTicketMapper;
    public User selectByPrimaryKey(int userId)
    {
        return userMapper.selectByPrimaryKey(userId);
    }

    public Map register(String username,String email,String password)
    {
        Map map=new HashMap();
        if(org.apache.commons.lang.StringUtils.isBlank(username))
        {
            map.put("msg3","用户名不能为空");
            return map;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(password)||password.length()<6)
        {
            map.put("msg2","请按要求填写密码");
            return map;
        }
        if(userMapper.checkUsername(username)>0)
        {
            map.put("msg1","用户名已经存在");
            return map;
        }
        User user=new User();
        user.setEmail(email);
        user.setHeadUrl(String.format("/images/img/boy%d.png",new Random().nextInt(4)));
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(WenUtil.MD5(password+user.getSalt()));
        int a=userMapper.addUser(user);
        if(a>0)
        {
            User user1=userMapper.selectByname(username);
            //添加一个loginticket
            String ticket=addTicket(user1.getId());
            map.put("ticket",ticket);
        }

        return map;

    }

        // 登录业务逻辑 先从数据库取出用户 再进行密码核对 然后建立loginticket到数据库中
    public Map login(String username,String password)
    {
        Map map=new HashMap();
        if(org.apache.commons.lang.StringUtils.isBlank(username)|| org.apache.commons.lang.StringUtils.isBlank(password))
        {
            map.put("msg","用户名，密码不能为空");
            return map;
        }
        User user=userMapper.selectByname(username);
        if(!WenUtil.MD5(password+user.getSalt()).equals(user.getPassword()))
        {
            map.put("msg","密码错误");
        }
        String ticket=addTicket(user.getId());
        map.put("ticket",ticket);
        return map;

    }


    public String addTicket(int userId)
    {
        Date date=new Date();
        date.setTime(date.getTime()+3600*1000);
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-"," "));
        loginTicketMapper.insertSelective(loginTicket);
        return loginTicket.getTicket();
    }


    public void logout(String ticket)
    {
        Map map=new HashMap();
        map.put("status",1);//无效
        map.put("ticket",ticket);
        loginTicketMapper.updataStatus(map);
    }


}
