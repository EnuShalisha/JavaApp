package com.score1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.util.DBConn;

public class ScoreDAOImpl implements ScoreDAO{

	private Connection conn = DBConn.getConnection(); 
	//트랜잭션은 conn에서 처리 - 이후 배울 예정
	//기본값: 완료되면 자동 커밋
	
	@Override
	public void insertScore(ScoreDTO dto) throws Exception {
		
		String sql;
		
		//try resource 쓸 때는 차후 대입 불가(초기값 null 주고 이후에 값 대입 불가)
		try (Statement stmt = conn.createStatement()){
			
			sql = "INSERT INTO score(hak, name, birth, kor, eng, mat) "
					+"VALUES ('"+dto.getHak()+"','"+dto.getName()+"','"+dto.getBirth()
					+"',"+dto.getKor()+","+dto.getEng()+","+dto.getMat()+")";
			//INSERT UPDATE DELETE는 리턴 값 = 실행 행 수
			stmt.executeUpdate(sql);
			
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
	public void updateScore(ScoreDTO dto) throws Exception {
		
		String sql;
		
		try(Statement stmt = conn.createStatement()) {
			
			sql =" UPDATE score SET "
				+"name='"+dto.getName()+"',birth='"+dto.getBirth()
				+"',kor="+dto.getKor()+",eng="+dto.getEng()+",mat="+dto.getMat()
				+" where hak="+dto.getHak();
			stmt.executeUpdate(sql);
			
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
	public int deleteScore(String hak) throws Exception {
		String sql;
		int result=0;
		
		try(Statement stmt = conn.createStatement()){
			
			sql="delete from score where hak='"+hak+"'";
			result=stmt.executeUpdate(sql);
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
		
	}

	@Override
	public ScoreDTO readScore(String hak) {
		
		ScoreDTO dto = null;
		StringBuilder sb= new StringBuilder();
		
		try {
			sb.append(" select hak, name, birth,");
			sb.append("kor, eng, mat");
			sb.append(" from score");
			sb.append(" where hak='"+hak+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Statement stmt = conn.createStatement(); 
			ResultSet rs=stmt.executeQuery(sb.toString())) {
			
			if(rs.next()) {
				dto = new ScoreDTO();
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}

	@Override
	public List<ScoreDTO> listScore() {
		
		List<ScoreDTO> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" select hak, name, to_char(birth, 'yyyy-mm-dd') birth,");
			sb.append("kor, eng, mat,");
			sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave,");
			sb.append("rank() over(order by (kor+eng+mat) desc) rank");
			sb.append(" from score");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Statement stmt = conn.createStatement(); 
			ResultSet rs=stmt.executeQuery(sb.toString());) {
			
			while(rs.next()) {
				ScoreDTO dto =new ScoreDTO();
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				//dto.setBirth(rs.getDate("birth").toString()); : 국가별 설정
				//rs.getDate()는 Date 객체로 못받음(sql.date != util.date)
				//.toString() 붙여서 각 국가별 기본값으로 받아오는 것
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getInt("ave"));
				dto.setRank(rs.getInt("rank"));
				
				//ArrayList 객체에 저장
				list.add(dto);	
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<ScoreDTO> listScore(String name) {
		
		List<ScoreDTO> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" select hak, name, birth,");
			sb.append("kor, eng, mat,");
			sb.append("kor+eng+mat tot, (kor+eng+mat)/3 ave");
			sb.append(" from score");
			sb.append(" where name like'%"+name+"%'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try(Statement stmt = conn.createStatement(); 
			ResultSet rs=stmt.executeQuery(sb.toString())) {
			
			while(rs.next()) {
				ScoreDTO dto = new ScoreDTO();
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getDate("birth").toString());
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getInt("ave"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
