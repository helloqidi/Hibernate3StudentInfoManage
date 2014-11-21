package com.helloqidi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_student")
public class Student {

	private int stuId;
	private String stuNo;
	private String stuName;
	private String sex;
	private Date birthday;
	private int gradeId=-1;
	private String email;
	private String stuDesc;
	
	private String gradeName;
	
	
	
	public Student() {
		super();
	}
	
	
	public Student(String stuNo, String stuName, String sex, Date birthday,
			int gradeId, String email, String stuDesc) {
		super();
		this.stuNo = stuNo;
		this.stuName = stuName;
		this.sex = sex;
		this.birthday = birthday;
		this.gradeId = gradeId;
		this.email = email;
		this.stuDesc = stuDesc;
	}

	@Id
	@GenericGenerator(name="generator",strategy="increment")
	@GeneratedValue(generator="generator")
	@Column(name="stuId")
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	
	@Column(name="stuNo")
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	
	@Column(name="stuName")
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	@Column(name="sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name="birthday")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(name="gradeId")
	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="stuDesc")
	public String getStuDesc() {
		return stuDesc;
	}
	public void setStuDesc(String stuDesc) {
		this.stuDesc = stuDesc;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	
	
}
