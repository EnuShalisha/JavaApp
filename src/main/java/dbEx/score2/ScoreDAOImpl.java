package dbEx.score1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dbEx.util.DBConn;

public class ScoreDAOImpl implements ScoreDAO {
	private Connection conn=DBConn.getConnection();
	
	@Override
	public int insertScore(ScoreDTO dto) throws Exception {
		int result = 0;
		
		String sql=" INSERT INTO score(hak, name, birth, kor, eng, mat) ";
		sql+=" VALUES(?,?,?,?,?,?)";
		try(PreparedStatement pstmt=conn.prepareStatement(sql)) {
			
			pstmt.setString(1, dto.getHak());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getBirth());
			pstmt.setInt(4, dto.getKor());
			pstmt.setInt(5, dto.getEng());
			pstmt.setInt(6, dto.getMat());
			
			result = pstmt.executeUpdate();
			
			
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
		String sql="update score set name=?,birth=?,kor=?,eng=?,mat=? where hak=?";
		
		try(PreparedStatement pstmt=conn.prepareStatement(sql)) {
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getBirth());
			pstmt.setInt(3, dto.getKor());
			pstmt.setInt(4, dto.getEng());
			pstmt.setInt(5, dto.getMat());
			pstmt.setString(6, dto.getHak());
			
			result=pstmt.executeUpdate();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLDataException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
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
		String sql=("delete from score where hak=?");
		try (PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, hak);
			
			result=pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		
		return result;
	}

	@Override
	public ScoreDTO readScore(String hak) {
		ScoreDTO dto=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT hak, name, TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("  kor, eng, mat ");
			sb.append(" FROM score ");
			sb.append(" WHERE hak = ?");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, hak);
			rs=pstmt.executeQuery();
			
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
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}

		return dto;
	}

	@Override
	public List<ScoreDTO> listScore() {
		List<ScoreDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT hak, name, birth, ");
			// sb.append(" SELECT hak, name, TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("   kor, eng, mat,");
			sb.append("   kor+eng+mat tot, (kor+eng+mat)/3 ave,");
			sb.append("   RANK() OVER ( ORDER BY (kor+eng+mat) DESC ) rank ");
			sb.append(" FROM score ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			// SELECT 실행
			rs = pstmt.executeQuery();
			
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
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return list;
	}

	@Override
	public List<ScoreDTO> listScore(String name) {
		List<ScoreDTO> list=new ArrayList<>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT hak, name, ");
			sb.append(" TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("   kor, eng, mat,");
			sb.append("   kor+eng+mat tot, (kor+eng+mat)/3 ave ");
			sb.append(" FROM score ");
			sb.append(" WHERE INSTR(name, ?) >= 1 ");
			// sb.append(" WHERE name LIKE '%||?||%' ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
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
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return list;
	}

}
