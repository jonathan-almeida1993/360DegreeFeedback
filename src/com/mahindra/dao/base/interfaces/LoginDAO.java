package com.mahindra.dao.base.interfaces;

import com.mahindra.dao.base.GenericDAO; 
import com.mahindra.database.pojo.UserPojo;

public interface LoginDAO extends GenericDAO {
	
	public UserPojo validateUserLogin(UserPojo loginDetails);
	
	public UserPojo changePasswordRequest(String emailId);
	
	public void resetPassword(String emailId,int pwdChangedFlag,String encryptedPwd);
}
