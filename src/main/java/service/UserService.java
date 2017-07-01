package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDAO;
import model.User;

@Service
public class UserService {
	
	@Autowired
	UserDAO userDAO;
	
	public User getUser(int id){
		return userDAO.selectUserById(id);
	}
}
