package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.QuestionDAO;
import model.Question;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDAO questionDAO;
	
	public List<Question> getLatestQuestion(int userId,int offset,int limit){
		return questionDAO.selectLatestQuestions(userId, offset, limit);
	}
	
}
