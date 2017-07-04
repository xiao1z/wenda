package dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import model.Question;

public interface QuestionDAO {
	String TABLE_NAME=" question ";
	String INSERT_FIELDS = " title,content,created_date,user_id,comment_count ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	List<Question> selectLatestQuestions(@Param("userId") int userId,
			@Param("offset") int offset,@Param("limit") int limit);
	
	
	
	@Insert({"Insert into ",TABLE_NAME,"(",INSERT_FIELDS,") "
			+ "values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
	int addQuestion(Question question);
	

}
