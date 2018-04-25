package com.xiangyang.zhihu.controller;


import com.google.gson.Gson;
import com.xiangyang.zhihu.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller

public class ajaxController {


    //  get 方法 post 方法都能这样用
    @RequestMapping("/ajaxGet")
    @ResponseBody
    public String ajaxGet(@RequestParam("dj") String dj)
    {
       return dj;
    }


    //  写入body
    @RequestMapping("/ajaxGet1")
    public void ajaxGet1(HttpServletResponse response,@RequestParam("dj") String dj)
    {
        PrintWriter writer=null;

        try {
            // 相当于 responseBody注解的意义
             writer=response.getWriter();
             writer.write(dj);
             writer.flush();
             writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/ajaxGet2")
    @ResponseBody
    public Object ajaxGet2(HttpServletResponse response,@RequestBody User user)
    {

        //  返回后台是json对象
//        return user;

        Gson gson=new Gson();

        // 转化为Json字符串 返回后台是json字符串
       return  gson.toJson(user);

    }





    @RequestMapping("/ajax")
    public String ajax()
    {
        return "ajax";
    }


}
