package dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import model.Img;

public interface ImgDAO {
	
	
	String TABLE_NAME=" img ";
	String INSERT_FIELDS = " entity_id,entity_type,url,offset,status ";
	String SELECT_FIELDS = " id,"+INSERT_FIELDS;
	
	@Insert({"Insert into ",TABLE_NAME,"(",INSERT_FIELDS,") "
			+ "values(#{entityId},#{entityType},#{url},#{offset},#{status})"})
	public int addImg(Img img);
	
	@Select({"select ",SELECT_FIELDS," from",TABLE_NAME,
	"where entity_id=#{entityId} and entity_type=#{entityType}"})
	public List<Img> selectImgsByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);
}
