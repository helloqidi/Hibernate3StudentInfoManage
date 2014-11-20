package com.helloqidi.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.helloqidi.model.PageBean;
import com.helloqidi.model.Student;
import com.helloqidi.util.DateUtil;
import com.helloqidi.util.HibernateUtil;
import com.helloqidi.util.StringUtil;

public class StudentDao {

	public List<Student> studentList(PageBean pageBean,Student student,String bbirthday,String ebirthday)throws Exception{
		List<Student> studentList=null;
		StringBuffer sb=new StringBuffer("from Student s");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			sb.append(" and s.sex ='"+student.getSex()+"'");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('"+bbirthday+"')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('"+ebirthday+"')");
		}
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery(sb.toString().replaceFirst("and", "where"));
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getRows());
		}
		
		studentList=(List<Student>)query.list();
		return studentList;
	}
	
	public int studentCount(Student student,String bbirthday,String ebirthday)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_student s,t_grade g where s.gradeId=g.id");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			sb.append(" and s.sex ='"+student.getSex()+"'");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			//TO_DAYS是mysql的函数
			sb.append(" and TO_DAYS(s.birthday)>=TO_DAYS('"+bbirthday+"')");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and TO_DAYS(s.birthday)<=TO_DAYS('"+ebirthday+"')");
		}
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createSQLQuery(sb.toString());
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	public int studentDelete(String delIds)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createSQLQuery("delete from t_student where stuId in("+delIds+")");
		int count=query.executeUpdate();
		session.getTransaction().commit();
		return count;
	}
	
	
//	public int studentAdd(Connection con,Student student)throws Exception{
//		String sql="insert into t_student values(null,?,?,?,?,?,?,?)";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, student.getStuNo());
//		pstmt.setString(2, student.getStuName());
//		pstmt.setString(3, student.getSex());
//		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
//		pstmt.setInt(5, student.getGradeId());
//		pstmt.setString(6, student.getEmail());
//		pstmt.setString(7, student.getStuDesc());
//		return pstmt.executeUpdate();
//	}
//	
//	public int studentModify(Connection con,Student student)throws Exception{
//		String sql="update t_student set stuNo=?,stuName=?,sex=?,birthday=?,gradeId=?,email=?,stuDesc=? where stuId=?";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, student.getStuNo());
//		pstmt.setString(2, student.getStuName());
//		pstmt.setString(3, student.getSex());
//		//日期类型转换为字符串
//		pstmt.setString(4, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
//		pstmt.setInt(5, student.getGradeId());
//		pstmt.setString(6, student.getEmail());
//		pstmt.setString(7, student.getStuDesc());
//		pstmt.setInt(8, student.getStuId());
//		return pstmt.executeUpdate();
//	}
	
	public int studentSave(Student student)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(student);
		session.getTransaction().commit();
		return 1;
	}
	
	public boolean getStudentByGradeId(String gradeId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createSQLQuery("select count(*) from t_student where gradeId=?");
		query.setParameter(0, gradeId);	
		if(((BigInteger)query.uniqueResult()).intValue()>0){
			return true;
		}else{
			return false;
		}
	}
	
//	public boolean getStudentByGradeId(Connection con,String gradeId)throws Exception{
//		String sql="select * from t_student where gradeId=?";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, gradeId);
//		ResultSet rs=pstmt.executeQuery();
//		if(rs.next()){
//			return true;
//		}else{
//			return false;
//		}
//	}
}