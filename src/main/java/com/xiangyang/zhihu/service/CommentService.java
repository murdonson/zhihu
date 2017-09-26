package com.xiangyang.zhihu.service;


import com.xiangyang.zhihu.dao.CommentMapper;
import com.xiangyang.zhihu.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    public List<Comment> selectCommentsByQuestionId(int qid)
    {
        return commentMapper.selectCommentsByQuestionId(qid);
    }

    public void addComment(Comment comment)
    {
        commentMapper.insertSelective(comment);
    }


    public int selectCommentNumByUserId(int userId)
    {
        return commentMapper.selectCommentNumByUserId(userId);
    }


    public List<Comment> selectCommentsByUserId(int userId)
    {
        return commentMapper.selectCommentsByUserId(userId);
    }
}
