package com.helloqidi.action;

import java.sql.Connection;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.helloqidi.dao.GradeDao;
import com.helloqidi.dao.StudentDao;
import com.helloqidi.model.Grade;
import com.helloqidi.model.PageBean;
import com.helloqidi.util.DbUtil;
import com.helloqidi.util.JsonUtil;
import com.helloqidi.util.ResponseUtil;
import com.helloqidi.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class GradeAction extends ActionSupport {
	private String page;
	private String rows;

	private String s_gradeName="";
	private Grade grade;
	
	private String delIds;
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public String getS_gradeName() {
		return s_gradeName;
	}

	public void setS_gradeName(String s_gradeName) {
		this.s_gradeName = s_gradeName;
	}

	DbUtil dbUtil=new DbUtil();
	GradeDao gradeDao=new GradeDao();
	StudentDao studentDao = new StudentDao();
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try{
			if(grade == null){
				grade = new Grade();
			}
			grade.setGradeName(s_gradeName);
			
			JSONObject result=new JSONObject();
			//转换查询结果resultset为json格式
			//JSONArray jsonArray=JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, pageBean,grade));
			List<Grade> gradeList=gradeDao.gradeList(pageBean, grade);
			JSONArray jsonArray=new JSONArray();
			for(int i=0;i<gradeList.size();i++){
				Grade grade=(Grade)gradeList.get(i);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", grade.getId());
				jsonObject.put("gradeName", grade.getGradeName());
				jsonObject.put("gradeDesc", grade.getGradeDesc());
				jsonArray.add(jsonObject);
			}
			int total=gradeDao.gradeCount(grade);
			//easyui-datagrid接收的参数是rows,total
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	
	public String delete() throws Exception {
		try{
			JSONObject result=new JSONObject();
			String str[]=delIds.split(",");
			for(int i=0;i<str.length;i++){
				boolean f=studentDao.getStudentByGradeId(str[i]);
				if(f){
					result.put("errorIndex", i);
					result.put("errorMsg", "班级下面有学生，不能删除！");
					ResponseUtil.write(ServletActionContext.getResponse(), result);
					return null;
				}
			}
			int delNums=gradeDao.gradeDelete(delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}
	  return null;
	}

	public String save() throws Exception {	
		if (StringUtil.isNotEmpty(id)) {
			grade.setId(Integer.parseInt(id));
		}
		
		try {
			int saveNums = 0;
			JSONObject result = new JSONObject();
			saveNums = gradeDao.gradeSave(grade);
			if (saveNums > 0) {
				result.put("success", "true");
			} else {
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String gradeComboList() throws Exception {
		try{
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("gradeName", "请选择...");
			jsonArray.add(jsonObject);
			//jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, null,null)));
			jsonArray.addAll(jsonArray.fromObject(gradeDao.gradeList(null,null)));
			ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
