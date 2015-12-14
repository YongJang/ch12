package ch12;


import java.sql.*;
import java.text.*;
import java.util.*;

public class LikeDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private DBConnectionMgr pool;
	
	public LikeDAO(){
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
	
	public boolean like(int num,String id) throws SQLException{	// num 은 글번호, id는 회원 id
		String tempNum = Integer.toString(num);
		String sql = "select * from Like where num=? and id='?'";
		try{
			connect();
			ps = con.prepareStatement(sql);
			ps.setString(1, tempNum);
			ps.setString(2, id);
			rs = ps.executeQuery();
			Vector<LikeDTO> list = makeList(rs);
			//LikeDTO regBean = list.get(0);
			if(list.size()!=0){
				sql = "delete from Like where num=? and id='?'";
				ps = con.prepareStatement(sql);
				ps.setString(1, tempNum);
				ps.setString(2, id);
				int delete = 0;
				delete = ps.executeUpdate();
				return false;		// 좋아요 해제
			}else{
				sql = "insert into Like (num, id) values(?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1,tempNum);
				ps.setString(2, id);
				int insert = 0;
				insert = ps.executeUpdate();
				return true;		// 좋아요 선택
			}
			
		}finally{
			close();
		}
	}
	
	public Vector<LikeDTO> getLikeList() throws SQLException{
		String sql = "select * from Like";
		try{
			connect();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			Vector<LikeDTO> list = makeList(rs);
			return list;
		}finally{
			close();
		}
	}
	
	public Vector<LikeDTO> makeList(ResultSet rs) throws SQLException{
		// ResultSet -> Vector<LikeDTO> 변환
		Vector<LikeDTO> list = new Vector<LikeDTO>();
		while(rs.next()){
			// 1. LikeDTO로 포장 준비
			LikeDTO tempBean = new LikeDTO();
			// 2. rs 데이터 추출
			tempBean.setNum(rs.getInt("num"));
			tempBean.setId(rs.getString("id"));
			// 3. list에 추가
			list.addElement(tempBean);
		}
		return list;
	}
	
}


