package dbEx.score1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbEx.util.DBConn;

public class ScoreDAOImpl implements ScoreDAO {
	private Connection conn=DBConn.getConnection();
	
	@Override
	public int insertScore(ScoreDTO dto) throws Exception {
		int result = 0;
		Statement stmt=null; // 쿼리 실행 객체
		String sql;
		
		try {
			// INSERT INTO score(hak,name,birth,kor,eng,mat) VALUES ('11','홍길동','2000-10-10',80,90,80);
			sql = "INSERT INTO score(hak,name,birth,kor,eng,mat) VALUES ("
				+ "'"+dto.getHak()+"','"+dto.getName()+"','"+dto.getBirth()+"',"
				+ dto.getKor()+","+dto.getEng()+","+dto.getMat()+")";
			
			// Statement 객체는 Connection에서 얻어옴
			stmt = conn.createStatement();
			
			// 쿼리 실행(INSERT, UPDATE, DELETE, CREATE, ALTER, DROP 등)
			result = stmt.executeUpdate(sql);
			  // 리턴값 : INSERT, UPDATE, DELETE은 실행 행수 반환
		} catch (SQLIntegrityConstraintViolationException e) {
			// 기본키 중복, NOT NULL등의 제약 조건 위반
			if(e.getErrorCode()==1) {
				System.out.println("기본키 제약 위반-학번 중복입니다.");
			} else if(e.getErrorCode()==1400) {
				System.out.println("NOT NULL 위반-모든 데이터는 반드시 입력해야 합니다.");
			} else {
				System.out.println("제약 조건 위반입니다.");
			}
			
			throw e; // 예외를 다시 던짐
		} catch (SQLDataException e) {
			System.out.println("날짜형식등의 오류 입니다.");
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public int updateScore(ScoreDTO dto) throws Exception {
		int result = 0;
		Statement stmt=null;
		String sql;
		
		try {
			sql = "UPDATE score SET name = '"+dto.getName()+"', "
				+ " birth = '"+dto.getBirth()+"', "
				+ " kor = "+dto.getKor()+", "
				+ " eng = "+dto.getEng()+", "
				+ " mat = "+dto.getMat()
				+ " WHERE hak='"+dto.getHak()+"'";
			
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch (SQLDataException e) {
			System.out.println("날짜형식등의 오류 입니다.");
			
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw e;
		} finally {
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public int deleteScore(String hak) throws Exception {
		int result=0;
		Statement stmt=null;
		String sql;
		try {
			sql = "DELETE FROM score WHERE hak='"+hak+"'";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}

	@Override
	public ScoreDTO readScore(String hak) {
		ScoreDTO dto=null;
		Statement stmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT hak, name, TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("  kor, eng, mat ");
			sb.append(" FROM score ");
			sb.append(" WHERE hak = '"+hak+"'");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
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
			
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
			
		}

		return dto;
	}

	@Override
	public List<ScoreDTO> listScore() {
		List<ScoreDTO> list=new ArrayList<>();
		Statement stmt=null;
		ResultSet rs=null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT hak, name, birth, ");
			// sb.append(" SELECT hak, name, TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("   kor, eng, mat,");
			sb.append("   kor+eng+mat tot, (kor+eng+mat)/3 ave,");
			sb.append("   RANK() OVER ( ORDER BY (kor+eng+mat) DESC ) rank ");
			sb.append(" FROM score ");
			
			stmt = conn.createStatement();
			
			// SELECT 실행
			rs = stmt.executeQuery(sb.toString());
			
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
			
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return list;
	}

	@Override
	public List<ScoreDTO> listScore(String name) {
		List<ScoreDTO> list=new ArrayList<>();
		Statement stmt=null;
		ResultSet rs=null;
		
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT hak, name, ");
			sb.append(" TO_CHAR(birth, 'YYYY-MM-DD') birth, ");
			sb.append("   kor, eng, mat,");
			sb.append("   kor+eng+mat tot, (kor+eng+mat)/3 ave ");
			sb.append(" FROM score ");
			sb.append(" WHERE INSTR(name, '"+name+"') >= 1 ");
			// sb.append(" WHERE name LIKE '%"+name+"%' ");
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			
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
			
			if(stmt!=null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return list;
	}

}
