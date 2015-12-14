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
			System.out.println("Error : Ŀ�ؼ� ���� ����");
		}
	}
	
	public void connect(){
		try{
			con = pool.getConnection();
		}catch(Exception e){
			System.out.println("���� ���� �߻�");
		}
	}
	
	public void close(){
		try{
			if(pool != null)
				pool.freeConnection(con,ps,rs);
		}catch(Exception e){
			System.out.println("�ڿ� �ݳ� ����");
		}
	}
	
	public boolean like(int num,String id) throws SQLException{	// num �� �۹�ȣ, id�� ȸ�� id
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
				return false;		// ���ƿ� ����
			}else{
				sql = "insert into Like (num, id) values(?,?)";
				ps = con.prepareStatement(sql);
				ps.setString(1,tempNum);
				ps.setString(2, id);
				int insert = 0;
				insert = ps.executeUpdate();
				return true;		// ���ƿ� ����
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
		// ResultSet -> Vector<LikeDTO> ��ȯ
		Vector<LikeDTO> list = new Vector<LikeDTO>();
		while(rs.next()){
			// 1. LikeDTO�� ���� �غ�
			LikeDTO tempBean = new LikeDTO();
			// 2. rs ������ ����
			tempBean.setNum(rs.getInt("num"));
			tempBean.setId(rs.getString("id"));
			// 3. list�� �߰�
			list.addElement(tempBean);
		}
		return list;
	}
	
}


