package ex0319;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import db.util.DBConn;

public class Sqlplus {
	private static Connection conn = DBConn.getConnection();
	
	private static Statement stmt;
	static {
		try {
			stmt=conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void execute(String sql) {
		ResultSet rs=null;
		ResultSetMetaData rsmd=null;
		
		try {
			if(sql.toUpperCase().startsWith("select")) {
				rs=stmt.executeQuery(sql);
				rsmd=rs.getMetaData();
				
				int cols=rsmd.getColumnCount();
				for(int i=1; i<=cols; i++) {
					System.out.print(rsmd.getColumnName(i)+"\t");
				}
				System.out.println();
				for(int i=1; i<=cols; i++) {
					System.out.print("----------------");
				}
				System.out.println();
				
				while(rs.next()) {
					for(int i=1; i<=cols; i++) {
						System.out.print(rs.getString(i)+"\t");
					}
					System.out.println();
				}
				
			} else {
				int result = stmt.executeUpdate(sql);
				
				if(sql.toUpperCase().startsWith("INSERT"))
					System.out.println(result+"행이 추가되었습니다.");
				else if(sql.toUpperCase().startsWith("UPDATE"))
					System.out.println(result+"행이 수정되었습니다.");
				else if(sql.toUpperCase().startsWith("DELETE"))
					System.out.println(result+"행이 삭제되었습니다.");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		String sql, s;
		int n;
		
		go:
		while(true) {
			try {
				System.out.print("SQL> ");
				sql="";
				n=1;
				
				do {
					s=br.readLine();
					if(s==null||s.equalsIgnoreCase("exit")) {
						DBConn.close();
						System.exit(0);
					}
					
					s=s.trim();
					sql+=s+" ";
					
					if(sql.trim().length()==0) 
						continue go;
					
					if(s.lastIndexOf(";")==-1) {
						System.out.print((++n)+" ");
					}
				}while(s.lastIndexOf(";")==-1);
					
					sql=sql.trim();
					sql=sql.substring(0, sql.lastIndexOf(";"));
					
					if(sql.trim().length()==0)
						continue;
					
					execute(sql);
				} catch (Exception e) {
				// TODO: handle exception
				}
		

		}
	}
	
}
		


