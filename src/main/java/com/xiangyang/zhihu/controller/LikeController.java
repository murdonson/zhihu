package com.xiangyang.zhihu.controller;


import com.xiangyang.zhihu.model.EntityType;
import com.xiangyang.zhihu.model.HostHolder;
import com.xiangyang.zhihu.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LikeController {

    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping("/like/{qId}")
    public String like(Model model,
                       @PathVariable("qId") int qId)
    {
        likeService.like(hostHolder.getUser().getId(),qId, EntityType.ENTITY_QUESTION);
        return "redirect:/";
    }
    @RequestMapping("/dislike/{qId}")
    public String dislike(Model model,
                          @PathVariable("qId") int qId)
    {
        likeService.dislike(hostHolder.getUser().getId(),qId, EntityType.ENTITY_QUESTION);
        return "redirect:/";

    }
}
