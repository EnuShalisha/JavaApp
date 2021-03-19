package db.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.util.DBConn;

public class SalaryDAO {
	private Connection conn=DBConn.getConnection();
	
	public int insertSalary(SalaryDTO dto) throws Exception{
		int result=0;
		String sql = "insert into salary values(salary_seq.nextval, ?, to_char(to_date(?, 'yyyymm'), 'yyyymm'), to_date(?, 'yyyymmdd'), ";
		sql+= "?, ?, ?, ?)";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dto.getSabeon());
			stmt.setString(2, dto.getPayDate());
			stmt.setString(3, dto.getPaymentDate());
			stmt.setInt(4, dto.getPay());
			stmt.setInt(5, dto.getSudang());
			stmt.setInt(6, dto.getTax());
			stmt.setString(7, dto.getMemo());
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
	
	public int updateSalary(SalaryDTO dto) throws Exception{
		int result=0;
		String sql="update salary set ";
		sql+="paydate=to_char(to_date(?, 'yyyymm'), 'yyyymm'), paymentdate=to_date(?, 'yyyymmdd'), ";
		sql+= "pay=?, sudang=?, tax=? memo=?)";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, dto.getPayDate());
			stmt.setString(2, dto.getPaymentDate());
			stmt.setInt(3, dto.getPay());
			stmt.setInt(4, dto.getSudang());
			stmt.setInt(5, dto.getTax());
			stmt.setString(6, dto.getMemo());
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

	public int deleteSalary(int salaryNum) throws Exception{
		int result=0;
		String sql="delete from salary where salarynum=?";
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, salaryNum);
			result=stmt.executeUpdate();
		} catch (SQLException e) {
			
			throw e;
		} catch (Exception e) {
			
			throw e;
		}	

		return result;
	}
	
	public SalaryDTO readSalary(int salaryNum) throws Exception{
		SalaryDTO dto = null;
		String sql=" select salarynum, s.sabeon, name, ";
		sql+="paydate, paymentdate, pay, sudang, (pay+sudang) tot";
		sql+=" from salary s left outer join employee e on s.sabeon=e.sabeon";
		sql+=" where salarynum=?";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, salaryNum);
			rs=stmt.executeQuery();
			if (rs.next()) {
				dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salaryNum"));
				dto.setSabeon(rs.getString("Sabeon"));
				dto.setName(rs.getString("Name"));
				dto.setPayDate(rs.getString("PayDate"));
				dto.setPaymentDate(rs.getString("PaymentDate"));
				dto.setPay(rs.getInt("Pay"));
				dto.setSudang(rs.getInt("Sudang"));
				dto.setTot(rs.getInt("Tot"));
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
	
	public List<SalaryDTO> listSalary(String payDate) throws Exception{
		List<SalaryDTO> list=new ArrayList<>();
		String sql=" select salarynum, s.sabeon, name, ";
		sql+="paydate, paymentdate, pay, sudang, (pay+sudang) tot, tax, (pay+sudang-tax) afterpay";
		sql+=" from salary s left outer join employee e on s.sabeon=e.sabeon";
		sql+=" where payDate=?";
		ResultSet rs = null;
		try(PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, payDate);
			rs=stmt.executeQuery();
			if (rs.next()) {
				SalaryDTO dto = new SalaryDTO();
				dto.setSalaryNum(rs.getInt("salaryNum"));
				dto.setSabeon(rs.getString("Sabeon"));
				dto.setName(rs.getString("Name"));
				dto.setPayDate(rs.getString("PayDate"));
				dto.setPaymentDate(rs.getString("PaymentDate"));
				dto.setPay(rs.getInt("Pay"));
				dto.setSudang(rs.getInt("Sudang"));
				dto.setTot(rs.getInt("Tot"));
				dto.setTax(rs.getInt("Tax"));
				dto.setAfterPay(rs.getInt("AfterPay"));
				
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
	
	public List<SalaryDTO> listSalary(Map<String, Object> map) {
		List<SalaryDTO> list=new ArrayList<>();
		
		return list;
	}

	public List<SalaryDTO> listSalary() {
		List<SalaryDTO> list=new ArrayList<>();
		
		
		return list;
	}

}
