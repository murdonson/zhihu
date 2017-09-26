package com.xiangyang.zhihu.dao;

import com.xiangyang.zhihu.model.Question;

import java.util.List;
import java.util.Map;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);

    List<Question> selectQuestions();

    List<Question> selectQuestionsByUserId(int userId);

    List<Question> selectLatestQuestions(Map map);
    List<Question> selectIndexLatestQuestions(Map map);

    int getTotal();
}