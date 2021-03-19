package ex0319;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class MetaDataEx {

	public static void main(String[] args) {
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		String sql;
		
		try {
			sql = "select * from score";
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			rsmd=rs.getMetaData();
			
			int cols=rsmd.getColumnCount();
			System.out.println("전체컬럼수 : "+cols);
			System.out.println("컬럼명\t컬럼타입\t컬럼타입명\t컬럼명");
			for(int i=1; i<=cols; i++) {
				System.out.print(rsmd.getColumnName(i)+"\t");
				System.out.print(rsmd.getColumnType(i)+"\t");
				System.out.print(rsmd.getColumnType(i)+"\t");
				System.out.println(rsmd.getPrecision(i));
			}
			System.out.println();
			
			while(rs.next()) {
				for(int i=1; i<=cols; i++) {
					System.out.print(rs.getString(i)+"\t");
				}
				System.out.println();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
