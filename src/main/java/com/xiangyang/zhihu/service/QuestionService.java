package com.xiangyang.zhihu.service;


import com.xiangyang.zhihu.dao.QuestionMapper;
import com.xiangyang.zhihu.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    Map map=new HashMap();

    public List<Question>  selectQuestions()
    {
       List<Question> questionList= questionMapper.selectQuestions();
       return questionList;
    }

    public int getTotal()
    {
        return questionMapper.getTotal();
    }


    public List<Question> selectIndexLatestQuestions(Map map)
    {
        return questionMapper.selectIndexLatestQuestions(map);
    }

    public List<Question>  selectQuestionsByUserId(int userId)
    {
        List<Question> questionList= questionMapper.selectQuestionsByUserId(userId);
        return questionList;
    }




    public int addQuestion(Question question)
    {
        return questionMapper.insertSelective(question);
    }

    public Question selectQuestionById(int qid)
    {
        return questionMapper.selectByPrimaryKey(qid);
    }


    public void updataquestion(Question question)
    {
        questionMapper.updateByPrimaryKeySelective(question);
    }


    public List<Question> selectLatestQuestions(Map map)
    {
        return questionMapper.selectLatestQuestions(map);
    }
}
