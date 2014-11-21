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

	@SuppressWarnings("unchecked")
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
}
