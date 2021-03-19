package dbEx.score3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbEx.util.DBConn;
import oracle.jdbc.OracleTypes;

public class ScoreDAOImpl implements ScoreDAO {
	private Connection conn=DBConn.getConnection();
	
	@Override
	public int insertScore(ScoreDTO dto) throws Exception {
		int result = 0;
		
		//여태까지 sql문에 비해 가장 큰 장점 - 트랜잭션 구현 가능
		String sql="{ CALL insertscore(?,?,?,?,?,?)}";
		try(CallableStatement cstmt=conn.prepareCall(sql)) {
			
			
			cstmt.setString(1, dto.getHak());
			cstmt.setString(2, dto.getName());
			cstmt.setString(3, dto.getBirth());
			cstmt.setInt(4, dto.getKor());
			cstmt.setInt(5, dto.getEng());
			cstmt.setInt(6, dto.getMat());
			
			//쿼리 실행 - 절대 파라미터에 쿼리 전달 x
			//리턴값은 insert가 아닌 프로시저 실행 여부
			cstmt.executeUpdate();
			result=1;
			
			
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
		
		return result;
	}

	@Override
	public int updateScore(ScoreDTO dto) throws Exception {
		int result = 0;
		String sql="{ call updatescore(?,?,?,?,?,?)}";
		
		try(CallableStatement cstmt=conn.prepareCall(sql)) {
			
			cstmt.setString(1, dto.getHak());
			cstmt.setString(2, dto.getName());
			cstmt.setString(3, dto.getBirth());
			cstmt.setInt(4, dto.getKor());
			cstmt.setInt(5, dto.getEng());
			cstmt.setInt(6, dto.getMat());
			
			cstmt.executeUpdate();
			result=1;
			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLDataException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			if(e.getErrorCode()==20100) 
				System.out.println("등록된 자료 없음");
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	@Override
	public int deleteScore(String hak) throws Exception {
		int result=0;
		String sql="{ call deletescore(?)}";
		try (CallableStatement cstmt = conn.prepareCall(sql)){
			
			cstmt.setString(1, hak);
			
			cstmt.executeUpdate();
			result=1;
			
		} catch (SQLException e) {
			if(e.getErrorCode()==20100) 
				System.out.println("등록된 자료 없음");
			e.printStackTrace();
			throw e;
		} 
		
		return result;
	}

	@Override
	public ScoreDTO readScore(String hak) {
		ScoreDTO dto=null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		
		try {
			String sql= "{call readscore(?, ?)}";
			
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR); // 이게 out 파라미터
			cstmt.setString(2, hak);
			//executeQuery: select문 실행 후 리턴을 ResultSet으로 받는 경우
			//executeUpdate: ResultSet에 리턴을 받는 게 아닌 파라미터 실행만 하는 것
			//모든 프로시저는 executeUpdate()로 실행
			cstmt.executeUpdate();
			//out 파라미터를 object로 받아오는 경우
			rs=(ResultSet) cstmt.getObject(1);
			
			if(rs.next()) {
				dto=new ScoreDTO();
				
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(cstmt!=null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}

		return dto;
	}

	@Override
	public List<ScoreDTO> listScore() {
		List<ScoreDTO> list=new ArrayList<>();
		CallableStatement cstmt=null;
		ResultSet rs=null;
		String sql="{ call listScore(?)}";
		
		try {
			
			cstmt = conn.prepareCall(sql);
			
			// SELECT 실행
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeUpdate();
			rs = (ResultSet) cstmt.getObject(1);
			
			while(rs.next()) {
				ScoreDTO dto=new ScoreDTO();
				
				dto.setHak(rs.getString("hak"));
				// dto.setHak(rs.getString(1));
				dto.setName(rs.getString("name"));
				// dto.setBirth(rs.getString("birth")); // 기본:yyyy-mm-dd hh:mi:ss
				dto.setBirth(rs.getDate("birth").toString()); // yyyy-mm-dd(국가설정이한국인경우)
				    // rs.getDate() => java.sql.Date 형
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getInt("ave"));
				dto.setRank(rs.getInt("rank"));
				
				// ArrayList객체에 저장
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(cstmt!=null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return list;
	}

	@Override
	public List<ScoreDTO> listScore(String name) {
		List<ScoreDTO> list=new ArrayList<>();
		CallableStatement cstmt=null;
		ResultSet rs=null;
		
		String sql= "{call searchnamescore(?, ?)}";
		
		try {
			
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, name);
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(1);
			
			while(rs.next()) {
				ScoreDTO dto=new ScoreDTO();
				dto.setHak(rs.getString("hak"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getInt("ave"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(cstmt!=null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return list;
	}

	@Override
	public Map<String, Integer> averageScore() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		String sql = "{call averageScore(?, ?, ?)}";
		try(CallableStatement cstmt = conn.prepareCall(sql)) {
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.registerOutParameter(2, OracleTypes.INTEGER);
			cstmt.registerOutParameter(3, OracleTypes.INTEGER);
			
			cstmt.executeUpdate();
			
			int kor = cstmt.getInt(1);
			int eng = cstmt.getInt(2);
			int mat = cstmt.getInt(3);
			
			map.put("kor",kor);
			map.put("eng",eng);
			map.put("mat",mat);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

}
