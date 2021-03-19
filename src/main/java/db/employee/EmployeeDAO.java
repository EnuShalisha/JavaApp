package db.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;

public class EmployeeDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertEmployee(EmployeeDTO dto) throws Exception{
		int result=0;
		String sql="insert into employee values(?,?,?,?)";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dto.getSabeon());
			stmt.setString(2, dto.getName());
			stmt.setString(3, dto.getBirth());
			stmt.setString(4, dto.getTel());
			
			result=stmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.print("제약조건 위반입니다.");
			throw e;
		} catch (SQLDataException e) {
			System.out.println("데이터 형식 위반입니다.");
			throw e;
		} catch (SQLException e) {
			
			throw e;
		} catch (Exception e) {
			
			throw e;
		}	
		
		return result;
	}
	
	public int updateEmployee(EmployeeDTO dto) throws Exception{
		int result=0;
		String sql="update employee set name=?, birth=?, tel=? where sabeon=?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dto.getName());
			stmt.setString(2, dto.getBirth());
			stmt.setString(3, dto.getTel());
			stmt.setString(4, dto.getSabeon());
			
			result=stmt.executeUpdate();	
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.print("제약조건 위반입니다.");
			throw e;
		} catch (SQLDataException e) {
			System.out.println("데이터 형식 위반입니다.");
			throw e;
		} catch (SQLException e) {
		
			throw e;
		} catch (Exception e) {
		
			throw e;
		}
		
		return result;
	}
	
	public EmployeeDTO readEmployee(String sabeon) throws Exception {
		EmployeeDTO dto=null;
		String sql="select sabeon, name, to_char(birth, 'yyyy-mm-dd) birth, tel from employee ";
		sql+="where sabeon=?";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(sql))  {
			
			stmt.setString(1, sabeon);	
			rs = stmt.executeQuery();
			if (rs.next()) {
				dto = new EmployeeDTO();
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setTel(rs.getString("tel"));
			}
			
		} catch (SQLException e) {
		
			throw e;
		} catch (Exception e) {
		
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}
	
	public List<EmployeeDTO> listEmployee() throws Exception {
		List<EmployeeDTO> list=new ArrayList<>();
		String sql="select sabeon, name, to_char(birth, 'yyyy-mm-dd') birth, tel from employee";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			rs = stmt.executeQuery();
			while (rs.next()) {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setTel(rs.getString("tel"));
				
				list.add(dto);
			}
			
		} catch (SQLException e) {
		
			throw e;
		} catch (Exception e) {
		
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	public List<EmployeeDTO> listEmployee(String name) throws Exception {
		List<EmployeeDTO> list=new ArrayList<>();
		String sql="select sabeon, name, to_char(birth, 'yyyy-mm-dd') birth, tel from employee ";
		sql+="where name=?";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, name);
			
			rs=stmt.executeQuery();
			while (rs.next()) {
				EmployeeDTO dto = new EmployeeDTO();
				dto.setSabeon(rs.getString("sabeon"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setTel(rs.getString("tel"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
		
			throw e;
		} catch (Exception e) {
	
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
}
