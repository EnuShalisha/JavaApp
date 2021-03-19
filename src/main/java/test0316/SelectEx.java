package test0316;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.util.DBConn;

public class SelectEx {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		String sql;
		
		sql="SELECT hak, name, birth, kor, eng, mat, kor+eng+mat FROM score ";
		//executeQuery : select문 실행 - 커서 리턴(resultSet)
		try(Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);) 
		{
			while(rs.next()) {
				System.out.print(rs.getString(1)+"\t");
				System.out.print(rs.getString(2)+"\t");
				System.out.print(rs.getString("birth")+"\t");
				System.out.print(rs.getInt("kor")+"\t");
				System.out.print(rs.getString(5)+"\t");
				System.out.print(rs.getString(6)+"\t");
				System.out.println(rs.getString(7)+"\t");	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

//rs.next()
//BOF
//1 <- true
//2 <- true
//3 <- true
//EOF <- false
