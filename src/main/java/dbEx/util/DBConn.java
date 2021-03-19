package dbEx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

// Singleton pattern
public class DBConn {
	private static Connection conn;
	
	private DBConn() {
	}
	
	public static Connection getConnection() {
		// String url="jdbc:oracle:thin:@127.0.0.1:1521:xe"; // 11g ���
		String url="jdbc:oracle:thin:@//127.0.0.1:1521/xe"; // 12c �̻�
		   // 1521:����Ŭ��Ʈ��ȣ, xe:SID
		String user="sky";
		String pwd="java$!";
		
		if(conn==null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver"); // �ڹ� 6���ʹ� ��������. �ڵ� �ε�
				conn = DriverManager.getConnection(url, user, pwd);
					// Connection ��ü ��ȯ
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	}
	
	public static Connection getConnection(String url, String user, String pwd) {
		if(conn==null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, user, pwd);
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return conn;
	}

	public static Connection getConnection(String url, String user, String pwd, String internal_logon) {
		if(conn==null) {
			try {
				Properties info = new Properties();
				info.put("user", user);
				info.put("password", pwd);
				info.put("internal_logon", internal_logon);  // sysdba ���� ��
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection(url, info);
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return conn;
	}
	
	public static void close() {
		if(conn!=null) {
			try {
				if(! conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		conn = null;
	}

}
