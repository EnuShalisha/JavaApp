package db.gate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyGateDAO {
	private Connection conn = null;
	private String[] columnNames;
	
	private String message;
	
	public MyGateDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	// SELECT
	public List<String[]> getResultList(String sql) {
		List<String[]> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData resultMetaData = null;
		
		message="";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			resultMetaData = rs.getMetaData();  // ��Ÿ����

			// �ʵ� �� ���ϱ�
			int cols = resultMetaData.getColumnCount();

			// �ʵ�� ���ϱ�
			columnNames = new String[cols];
			for(int i = 0; i<cols; i++) {
				columnNames[i] = resultMetaData.getColumnName(i+1);
			}
			 
			// ���
			list = new ArrayList<String[]>();
			while(rs.next()) {
				String []results = new String[cols];

				for(int i=0; i<cols; i++) {
					results[i] = rs.getString(i+1);
				}
				
				list.add(results);
			}
		} catch (SQLException e) {
			message = e.getMessage();
		} catch (Exception e) {
			message = "������ ������ �ֽ��ϴ�.";
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}

	public List<String[]> getDescList(String sql) {
		List<String[]> list = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData resultMetaData = null;
		
		message="";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			resultMetaData = rs.getMetaData();  // ��Ÿ����

			list = new ArrayList<String[]>();
			
			// �ʵ� �� ���ϱ�
			int cols = resultMetaData.getColumnCount();
			for(int i = 0; i<cols; i++) {
				String []results = new String[3];
				
				// �÷���
				results[0] = resultMetaData.getColumnName(i+1);
				if(resultMetaData.isNullable(i+1) == 1)
					results[1] = "(null)";
				else
					results[1] = "NOT NULL";
				
				int size = resultMetaData.getPrecision(i+1);
				if(size==0)
					results[2] = resultMetaData.getColumnTypeName(i+1);
				else
					results[2] = resultMetaData.getColumnTypeName(i+1)+"("+size+")";
				list.add(results);
			}
		} catch (SQLException e) {
			message = e.getMessage();
		} catch (Exception e) {
			message = "������ ������ �ֽ��ϴ�.";
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	public boolean execute(String sql) {
		int result = 0;
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			message = e.getMessage();
			return false;
		} catch (Exception e) {
			message = "������ ������ �ֽ��ϴ�.";
			e.printStackTrace();
			return false;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		if(sql.toLowerCase().startsWith("insert"))
			message = result + " ���� �����Ͱ� �߰� �Ǿ����ϴ�.";
		else if(sql.toLowerCase().startsWith("update"))
			message = result + " ���� �����Ͱ� ���� �Ǿ����ϴ�.";
		else if(sql.toLowerCase().startsWith("detete"))
			message = result + " ���� �����Ͱ� ���� �Ǿ����ϴ�.";
		else
			message = " ������ ���������� ���� �Ǿ����ϴ�.";
		
		return true;
	}
	
	public String[] getColumnNames() {
		return columnNames;
	}

	public String getMessage() {
		return message;
	}
}
