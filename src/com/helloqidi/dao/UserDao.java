package com.helloqidi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.helloqidi.model.User;
import com.helloqidi.util.HibernateUtil;


public class UserDao {

	/**
	 * ��¼��֤
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User login(User user) throws Exception{
		User resultUser=null;
		//��ȡһ��session(�����Ϊһ������)
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		//��ʼ����
		session.beginTransaction();
		Query query=session.createQuery("from User u where u.userName=? and u.password=?");
		query.setString(0, user.getUserName());
		query.setString(1, user.getPassword());
		List<User> userList=(List<User>)query.list();
		//�������
		session.getTransaction().commit();
		if(userList.size()>0){
			resultUser=userList.get(0);
		}
		return resultUser;
		
//		String sql="select * from t_user where userName=? and password=?";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, user.getUserName());
//		pstmt.setString(2, user.getPassword());
//		ResultSet rs=pstmt.executeQuery();
//		if(rs.next()){
//			resultUser=new User();
//			resultUser.setUserName(rs.getString("userName"));
//			resultUser.setPassword(rs.getString("password"));
//		}
		
	}
}
