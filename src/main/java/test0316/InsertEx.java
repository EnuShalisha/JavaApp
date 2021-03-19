package test0316;

import java.sql.Connection;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import db.util.DBConn;

public class InsertEx {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		Statement stmt=null; // 쿼리를 실행하는 객체
		String sql;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			sql = "INSERT INTO score(hak, name, birth, kor, eng, mat)"
					+ "VALUES ('1002', '너자바', '2001-10-10', 100, 90, 80)";
				//쿼리 실행
			result = stmt.executeUpdate(sql);
				//executeUpdate() : insert, update, delete, create, alter, drop 등 거의 다 됨
				//select 빼고
			System.out.println(result+"행이 추가되었습니다.");
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("기본키 중복, not null 등의 조언 - 가입하면 안 됨");
			System.out.println("에러코드: "+e.getErrorCode());
		//위에 sql 문 만지면서
		//1. 기본키 등 유일성 제한 위반
		//1400. not null 걸려버림
		//01861. 형식 오류
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		} catch (SQLDataException e) {
			System.out.println("날짜 등의 형식 문제로 인한 예외");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
			DBConn.close();
		}
		
		
		
	}

}
