package db.member1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;

public class MemberDAOImpl implements MemberDAO{

	private Connection conn = DBConn.getConnection(); 
	
	@Override
	public int insertMember(MemberDTO dto) throws Exception {

		int result=0;
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("INSERT ALL into member1 ");
			sb.append("values('"+dto.getId()+"','"+dto.getPwd()+"','"+dto.getName()+"')");
			sb.append(" into member2 values ('");
			sb.append(dto.getId()+"','"+dto.getBirth()+"','"+dto.getEmail()+"','"+dto.getTel()+"')");
			sb.append(" SELECT * from dual");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		try(Statement stmt=conn.createStatement()) {
			result = stmt.executeUpdate(sb.toString());
			
			if(result!=2){
				throw new Exception("데이터 추가에 문제 발생");
			}
			return result;
		} catch (SQLIntegrityConstraintViolationException e) {
			
			System.out.print("제약조건 위반입니다 : ");
			if(e.getErrorCode()==1) 
				System.out.println("기본키 중복 위반");
			else if(e.getErrorCode()==1400) 
				System.out.println("NOT NULL 위반");
			else System.out.println("제약 조건 위반");
			throw e;
			
		} catch (SQLDataException e) {
			System.out.println("데이터 형식 위반입니다.");
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}

	@Override
	public int updateMember(MemberDTO dto) throws Exception{
		int result=0;
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		try {
			sb1.append(" update member1 set pwd='"+dto.getPwd()+"'");
			sb1.append(" where id='"+dto.getId()+"'");
			sb2.append(" update member2 set birth='"+dto.getBirth()+"',email='"+dto.getEmail()+"',tel='"+dto.getTel()+"'");
			sb2.append(" where id='"+dto.getId()+"'");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		try(Statement stmt=conn.createStatement()) {
			result = stmt.executeUpdate(sb1.toString());
			if(result != stmt.executeUpdate(sb2.toString())) {
				throw new Exception("수정이 정상적으로 이루어지지 않았습니다.");
			}
			return result;
		} catch (SQLIntegrityConstraintViolationException e) {
			
			System.out.print("제약조건 위반입니다 : ");
			if(e.getErrorCode()==1) 
				System.out.println("기본키 중복 위반");
			else if(e.getErrorCode()==1400) 
				System.out.println("NOT NULL 위반");
			else System.out.println("제약 조건 위반");
			throw e;
			
		} catch (SQLDataException e) {
			System.out.println("데이터 형식 위반입니다.");
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}

	@Override
	public int deleteMember(String id) throws Exception{
		int result=0;
		String sql1, sql2;
		try(Statement stmt = conn.createStatement()) {
			sql1="delete from member2 where id='"+id+"'";
			result=stmt.executeUpdate(sql1);
			sql2="delete from member1 where id='"+id+"'";
			if(result!=stmt.executeUpdate(sql2)) {
				throw new Exception("삭제가 정상적으로 이루어지지 않았습니다.");
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public MemberDTO readMember(String id) {
		MemberDTO dto = null;
		StringBuilder sb= new StringBuilder();
		
		try {
			sb.append(" select member1.id, pwd, name,");
			sb.append("to_char(birth, 'yyyy-mm-dd') birth, email, tel");
			sb.append(" from member1, member2");
			sb.append(" where member1.id='"+id+"' and member1.id=member2.id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Statement stmt = conn.createStatement(); 
			ResultSet rs=stmt.executeQuery(sb.toString())) {
			
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public List<MemberDTO> listMember() {
		List<MemberDTO> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" select member1.id, pwd, name,");
			sb.append("to_char(birth, 'yyyy-mm-dd') birth, email, tel");
			sb.append(" from member1, member2");
			sb.append(" where member1.id=member2.id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Statement stmt = conn.createStatement(); 
			ResultSet rs=stmt.executeQuery(sb.toString());) {
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				list.add(dto);	
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<MemberDTO> listMember(String name) {
		List<MemberDTO> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" select member1.id, pwd, name,");
			sb.append("to_char(birth, 'yyyy-mm-dd') birth, email, tel");
			sb.append(" from member1, member2");
			sb.append(" where member1.name like '%"+name+"%' and member1.id=member2.id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Statement stmt = conn.createStatement(); 
			ResultSet rs=stmt.executeQuery(sb.toString())) {
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
				list.add(dto);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	
}
