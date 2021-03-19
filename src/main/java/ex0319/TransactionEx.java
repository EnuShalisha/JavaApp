package ex0319;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dbEx.util.DBConn;

public class TransactionEx {
	private static Connection conn = DBConn.getConnection();
	public static void main(String[] args) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String id, name, birth, tel;
		PreparedStatement pstmt = null;
		try {
			System.out.print("아이디?");
			id=br.readLine();
			System.out.print("이름?");
			name=br.readLine();
			System.out.print("생일?");
			birth=br.readLine();
			System.out.print("전화번호?");
			tel=br.readLine();
			
			conn.setAutoCommit(false); // 자동커밋 안되도록 설정
			
			String sql = "Insert into test1(id, name) values(?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql = "Insert into test2(id, birth) values(?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, birth);
			pstmt.executeUpdate();
			pstmt.close();
			pstmt=null;
			
			sql = "Insert into test3(id, tel) values(?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, tel);
			pstmt.executeUpdate();
			
			//커밋
			conn.commit();
			System.out.println("데이터 추가 성공");
			
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			
			try {
				conn.setAutoCommit(true);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			DBConn.close();
			
			
		}
	}
	
	
}
