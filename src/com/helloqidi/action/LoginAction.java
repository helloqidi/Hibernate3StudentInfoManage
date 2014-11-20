package com.helloqidi.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.helloqidi.dao.UserDao;
import com.helloqidi.model.User;
import com.helloqidi.util.DbUtil;
import com.helloqidi.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements ServletRequestAware {



	private User user;
	private String error;
	private String imageCode;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	UserDao userDao=new UserDao();
	
	HttpServletRequest request;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}
	
	@Override
	public String execute() throws Exception {
		// ��ȡSession
		HttpSession session=request.getSession();
		
		// TODO Auto-generated method stub
		
		//�п�
		if(StringUtil.isEmpty(user.getUserName()) || StringUtil.isEmpty(user.getPassword())){
			//			request.setAttribute("error", "�û���������Ϊ��");
			error = "�û���������Ϊ��";
			//request.getRequestDispatcher("index.jsp").forward(request, response);
			//return;
			return ERROR;
		}
		if(StringUtil.isEmpty(imageCode)){
			error = "��֤��Ϊ��";
			return ERROR;
		}
		
		if(!imageCode.equals(session.getAttribute("imageCode"))){
			error = "��֤�����";
			return ERROR;
		}
		
		Connection con=null;
		try {
			User currentUser=userDao.login(user);
			if(currentUser==null){
				error = "�û������������";
				return ERROR;
			}else{
				session.setAttribute("currentUser", currentUser);
				// �ͻ�����ת
				return SUCCESS;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
