package dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
	
	@Select({"select count(id) from", TABLE_NAME})
	int getQuestionCount();
	
	@Delete({"delete from ",TABLE_NAME,"where id = #{questionId}"})
	int deleteQuestion(int questionId);
	
	@Select({"select",SELECT_FIELDS," from",TABLE_NAME,"where id=#{id}"})
	Question selectQuestion(int id);
	
	@Update({"update ",TABLE_NAME,"set comment_count = #{commentCount} where id=#{id}"})
	int updateCommentCout(@Param("id") int id,@Param("commentCount") int commentCount);
}
