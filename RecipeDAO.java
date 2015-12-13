package ch12;

import java.sql.*;
import java.text.*;
import java.util.*;

public class RecipeDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private DBConnectionMgr pool;
	
	public RecipeDAO(){
		try{
			pool = DBConnectionMgr.getInstance();
		}catch(Exception e){
			System.out.println("Error : 커넥션 연결 실패");
		}
	}
	
	public void connect(){
		try{
			con = pool.getConnection();
		}catch(Exception e){
			System.out.println("연결 오류 발생");
		}
	}
	
	public void close(){
		try{
			if(pool != null)
				pool.freeConnection(con,ps,rs);
		}catch(Exception e){
			System.out.println("자원 반납 오류");
		}
	}
	
	public RecipeDTO getRecipe(int num) throws SQLException{
		String tempNum = Integer.toString(num);
		String sql = "select * from recipe where num=?";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			rs = ps.executeQuery();
			Vector<RecipeDTO> list = makeList(rs);
			RecipeDTO regBean = list.get(0);
			return regBean;
		}finally{
			close();
		}
	}
	
	public Vector<RecipeDTO> getRecipeList() throws SQLException{
		String sql = "select * from recipe";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<RecipeDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<RecipeDTO> getRecipeListbySearch(String keyword, String str) throws SQLException{
		String key;
		if(keyword.equals("name")){
			key = "cookname";
		}else if(keyword.equals("ingredient")){
			key = "ingredient";
		}else{
			key = "recipe";
		}
		String sql = "select * from recipe where "+key+" like '"+"%"+str+"%'";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<RecipeDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<RecipeDTO> makeList(ResultSet rs) throws SQLException{
		// ResultSet -> Vector<RecipeDTO> 변환
		Vector<RecipeDTO> list = new Vector<RecipeDTO>();
		while(rs.next()){
			// 1. RecipeDTO로 포장 준비
			RecipeDTO tempBean = new RecipeDTO();
			// 2. rs 데이터 추출
			tempBean.setNum(rs.getInt("num"));
			tempBean.setCookname(rs.getString("cookname"));
			tempBean.setIngredient(rs.getString("ingredient"));
			tempBean.setSeasoned(rs.getString("seasoned"));
			tempBean.setRecipe(rs.getString("recipe"));
			tempBean.setImgpath(rs.getString("imgpath"));
			tempBean.setRate(rs.getInt("rate"));
			// 3. list에 추가
			list.addElement(tempBean);
		}
		return list;
	}
	
}
