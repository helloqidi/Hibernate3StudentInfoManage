package com.helloqidi.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.helloqidi.model.Grade;
import com.helloqidi.model.PageBean;
import com.helloqidi.util.HibernateUtil;
import com.helloqidi.util.StringUtil;

public class GradeDao {

	public List<Grade> gradeList(PageBean pageBean,Grade grade)throws Exception{
		List<Grade> gradeList=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		StringBuffer sb=new StringBuffer("from Grade g");
		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and g.gradeName like '%"+grade.getGradeName()+"%'");
		}
		Query query=session.createQuery(sb.toString().replaceFirst("and", "where"));
		if(pageBean!=null){
			//��ҳ
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getRows());
		}
		gradeList=(List<Grade>)query.list();
		return gradeList;
	}
	
//	public ResultSet gradeList(Connection con,PageBean pageBean,Grade grade)throws Exception{
//		//ƴ���޸Ĳ�ѯ�ַ���ʱ������ʹ��StringBuffer
//		StringBuffer sb=new StringBuffer("select * from t_grade");
//
//		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())){
//			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
//		}
//		
//		if(pageBean!=null){
//			sb.append(" limit "+pageBean.getStart()+","+pageBean.getRows());
//		}
//		//PreparedStatement pstmt=con.prepareStatement(sb.toString());
//		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
//		return pstmt.executeQuery();
//	}
	
	public int gradeCount(Grade grade)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Query query=session.createSQLQuery(sb.toString().replaceFirst("and", "where"));
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
//	public int gradeCount(Connection con,Grade grade)throws Exception{
//		StringBuffer sb=new StringBuffer("select count(*) as total from t_grade");
//		if(StringUtil.isNotEmpty(grade.getGradeName())){
//			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
//		}
//		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
//		ResultSet rs=pstmt.executeQuery();
//		if(rs.next()){
//			return rs.getInt("total");
//		}else{
//			return 0;
//		}		
//	}
	
	/**
	 * delete from tableName where field in (1,3,5)
	 * @param con
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
//	public int gradeDelete(Connection con,String delIds)throws Exception{
//		String sql="delete from t_grade where id in("+delIds+")";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		//ɾ�˼����ͷ��ؼ���
//		return pstmt.executeUpdate();
//	}
	public int gradeDelete(String delIds)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createSQLQuery("delete from t_grade where id in("+delIds+")");
		int count=query.executeUpdate();
		session.getTransaction().commit();
		return count;
	}

	
	/**
	 * ����
	 */
//	public int gradeAdd(Connection con,Grade grade)throws Exception{
//		String sql="insert into t_grade values(null,?,?)";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, grade.getGradeName());
//		pstmt.setString(2, grade.getGradeDesc());
//		return pstmt.executeUpdate();
//	}
//	
//	public int gradeModify(Connection con,Grade grade)throws Exception{
//		String sql="update t_grade set gradeName=?,gradeDesc=? where id=?";
//		PreparedStatement pstmt=con.prepareStatement(sql);
//		pstmt.setString(1, grade.getGradeName());
//		pstmt.setString(2, grade.getGradeDesc());
//		pstmt.setInt(3, grade.getId());
//		return pstmt.executeUpdate();
//	}
	
	public int gradeSave(Grade grade)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		//merge��������id���޸ģ���id������
		session.merge(grade);
		session.getTransaction().commit();
		return 1;
	}
	
	public Grade getGradeById(int gradeId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Grade grade=(Grade) session.get(Grade.class, gradeId);
		session.getTransaction().commit();
		return grade;
	}
}
