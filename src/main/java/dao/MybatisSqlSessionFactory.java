package dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisSqlSessionFactory {
	private SqlSessionFactory factory;
	private static class MybatisSqlSessionFactoryHolder
	{
		private static MybatisSqlSessionFactory myFactory=new MybatisSqlSessionFactory();
	}
	
	private MybatisSqlSessionFactory()
	{
		String resource = "dao/mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			factory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SqlSessionFactory getSqlSessionFactory()
	{
		return MybatisSqlSessionFactoryHolder.myFactory.factory;
	}
}
