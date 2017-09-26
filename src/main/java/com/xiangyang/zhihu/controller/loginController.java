package com.xiangyang.zhihu.controller;


import com.xiangyang.zhihu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class loginController {

    @Autowired
    UserService userService;
    @RequestMapping("/relogin")  // 进入登录,注册页面
    public String relogin()
    {
        return "login";
    }


    @RequestMapping("/reg")  // 进行注册
    public String reg(Model model,
                      @RequestParam(value = "username") String username,
                      @RequestParam(value = "email",required = false) String email,
                      @RequestParam("password") String password,
                      HttpServletResponse response)
    {
       Map map= userService.register(username,email,password);
       if(map.containsKey("msg1"))
       {
           model.addAttribute("msg",map.get("msg1"));
           return "login";
       }
       if(map.containsKey("msg2"))
       {
           model.addAttribute("msg",map.get("msg2"));
           return "login";
       }
       if(map.containsKey("msg3"))
       {
           model.addAttribute("msg",map.get("msg3"));
           return "login";
       }
       if(map.containsKey("ticket"))
       {
           Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
           cookie.setMaxAge(3600*24);
           cookie.setPath("/");
           response.addCookie(cookie);
       }
       return "redirect:/";
    }



    @RequestMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "username") String username,
                        @RequestParam(value = "email",required = false) String email,
                        @RequestParam("password") String password,
                        HttpServletResponse response)
    {
        Map map=userService.login(username, password);
        if(map.containsKey("ticket"))
        {
            Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/");
            cookie.setMaxAge(3600*24);
            response.addCookie(cookie);
            return "redirect:/";
        }
        model.addAttribute("msg",map.get("msg"));
        return "login";
    }



    @RequestMapping("/logout")  //退出登录
    public String logout(@CookieValue("ticket") String ticket)
    {
        userService.logout(ticket);
        return "redirect:/";
    }
}
