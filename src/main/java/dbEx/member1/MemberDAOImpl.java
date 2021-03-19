package dbEx.member1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbEx.util.DBConn;

public class MemberDAOImpl implements MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int insertMember(MemberDTO dto) throws Exception {
		int result=0;
		Statement stmt=null;
		StringBuilder sb;
		
		// member1 테이블과 member2 테이블에 데이터 추가
		try {
			stmt = conn.createStatement();
			
			sb=new StringBuilder();
			sb.append("INSERT INTO member1(id,pwd,name) VALUES(");
			sb.append("'"+dto.getId()+"'");
			sb.append(",'"+dto.getPwd()+"'");
			sb.append(",'"+dto.getName()+"'");
			sb.append(")");
			result = stmt.executeUpdate(sb.toString());
			
			sb=new StringBuilder();
			sb.append("INSERT INTO member2(id,birth,email,tel) VALUES(");
			sb.append("'"+dto.getId()+"'");
			sb.append(",'"+dto.getBirth()+"'");
			sb.append(",'"+dto.getEmail()+"'");
			sb.append(",'"+dto.getTel()+"'");
			sb.append(")");
			result += stmt.executeUpdate(sb.toString());
			
/*
			sb=new StringBuilder();
			sb.append("INSERT ALL ");
			sb.append(" INTO member1(id,pwd,name) VALUES(");
			sb.append(" '"+dto.getId()+"'");
			sb.append(" ,'"+dto.getPwd()+"'");
			sb.append(" ,'"+dto.getName()+"'");
			sb.append(" )");
			sb.append(" INTO member2(id,birth,email,tel) VALUES(");
			sb.append(" '"+dto.getId()+"'");
			sb.append(" ,'"+dto.getBirth()+"'");
			sb.append(" ,'"+dto.getEmail()+"'");
			sb.append(" ,'"+dto.getTel()+"'");
			sb.append(" )");
			sb.append(" SELECT * FROM dual");
			
			result=stmt.executeUpdate(sb.toString());
 */
        } catch (SQLIntegrityConstraintViolationException e) {
        	System.out.println("기본키 중복, NOT NULL등의 제약조건 위반에 의한 예외 발생-무결성 제약조건 위반");
			throw e;
        } catch (SQLDataException e) {
        	System.out.println("날짜등의 데이터 형식 오류에 의한 예외 발생");
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
	public int updateMember(MemberDTO dto) throws Exception {
		int result = 0;
		Statement stmt = null;
		StringBuilder sb;

		// id 조건에 맞는 member1 테이블과 member2 테이블에 데이터 수정
		try {
			stmt = conn.createStatement();

			sb = new StringBuilder();
			sb.append("UPDATE member1 SET pwd='" + dto.getPwd() + "'");
			sb.append("  WHERE id='" + dto.getId() + "'");
			result = stmt.executeUpdate(sb.toString());

			sb = new StringBuilder();
			sb.append("UPDATE member2 SET birth='" + dto.getBirth() + "'");
			sb.append(", email='" + dto.getEmail() + "'");
			sb.append(", tel='" + dto.getTel() + "'");
			sb.append("  WHERE id='" + dto.getId() + "'");
			result += stmt.executeUpdate(sb.toString());

		} catch (SQLDataException e) {
			System.out.println("날짜등의 데이터 형식 오류에 의한 예외 발생");
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public int deleteMember(String id) throws Exception {
		int result = 0;
		Statement stmt = null;
		String sql;

		// id 조건에 맞는 member1 테이블과 member2 테이블에 데이터 삭제
		try {
			stmt = conn.createStatement();

			sql = "DELETE FROM member2 WHERE id='" + id + "'";
			result = stmt.executeUpdate(sql);

			sql = "DELETE FROM member1 WHERE id='" + id + "'";
			result += stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
			
			throw e;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public MemberDTO readMember(String id) {
		MemberDTO dto = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		// id 조건에 맞는 member1 테이블과 member2 테이블에 데이터를 OUTER JOIN 해서 검색
		try {
			sb.append("SELECT m1.id, pwd, name");
			sb.append(" ,TO_CHAR(birth, 'YYYY-MM-DD') birth, email, tel ");
			sb.append("  FROM member1 m1 ");
			sb.append("  LEFT OUTER JOIN member2 m2 ON m1.id = m2.id");
			sb.append("  WHERE m1.id='" + id + "'");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				// dto.setBirth(rs.getDate("birth").toString());
				// 오라클 DATE형은 자바에서 java.sql.Date로 반환 받는다.
				// java.sql.Date는 yyyy-mm-dd 형식으로 특화되어 있다.
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}

	@Override
	public List<MemberDTO> listMember() {
		List<MemberDTO> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		// member1 테이블과 member2 테이블의 전체 데이터를 OUTER JOIN 해서 전체 리스트
		try {
			sb.append("SELECT m1.id, pwd, name");
			sb.append(" ,TO_CHAR(birth, 'YYYY-MM-DD') birth, email, tel ");
			sb.append("  FROM member1 m1 ");
			sb.append("  LEFT OUTER JOIN member2 m2 ON m1.id = m2.id");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}

	@Override
	public List<MemberDTO> listMember(String val) {
		List<MemberDTO> list = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		// member1 테이블과 member2 테이블의 전체 데이터를 OUTER JOIN 해서 검색
		try {
			sb.append("SELECT m1.id, pwd, name");
			sb.append(" ,TO_CHAR(birth, 'YYYY-MM-DD') birth, email, tel ");
			sb.append("  FROM member1 m1 ");
			sb.append("  LEFT OUTER JOIN member2 m2 ON m1.id = m2.id");
			// sb.append(" WHERE name LIKE '%' || '"+val+"' || '%' ");
			sb.append("  WHERE INSTR(name, '" + val + "')>=1");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setTel(rs.getString("tel"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}

}
