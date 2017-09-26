package com.xiangyang.zhihu.dao;

import com.xiangyang.zhihu.model.Comment;
import com.xiangyang.zhihu.model.Question;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectCommentsByQuestionId(int qid);

    int selectCommentNumByUserId(int userId);

    List<Comment> selectCommentsByUserId(int userId);
}