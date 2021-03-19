package dbEx.member3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import dbEx.util.DBConn;
import oracle.jdbc.OracleTypes;

public class MemberDAOImpl implements MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	@Override
	public int insertMember(MemberDTO dto) throws Exception {
		int result=0;
		CallableStatement cstmt=null;
		String sql="{call insertmember(?,?,?,?,?,?)}";
		
		// member1 테이블과 member2 테이블에 데이터 추가
		try {
			
			cstmt = conn.prepareCall(sql);
			
			cstmt.setString(1, dto.getId());
			cstmt.setString(2, dto.getPwd());
			cstmt.setString(3, dto.getName());
			cstmt.setString(4, dto.getBirth());
			cstmt.setString(5, dto.getEmail());
			cstmt.setString(6, dto.getTel());
			
			cstmt.executeUpdate();
			result = 1;
			
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
			if(cstmt!=null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;

	}

	@Override
	public int updateMember(MemberDTO dto) throws Exception {
		int result = 0;
		CallableStatement cstmt=null;
		String sql="{call updatemember(?,?,?,?,?)}";
		
		
		// id 조건에 맞는 member1 테이블과 member2 테이블에 데이터 수정
		try {
			
			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, dto.getId());
			cstmt.setString(2, dto.getPwd());
			cstmt.setString(3, dto.getBirth());
			cstmt.setString(4, dto.getEmail());
			cstmt.setString(5, dto.getTel());

			cstmt.executeUpdate();
			result = 1;

		} catch (SQLDataException e) {
			System.out.println("날짜등의 데이터 형식 오류에 의한 예외 발생");
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public int deleteMember(String id) throws Exception {
		int result = 0;
		CallableStatement cstmt=null;
		String sql="{call deletemember(?)}";

		// id 조건에 맞는 member1 테이블과 member2 테이블에 데이터 삭제
		try {

			cstmt = conn.prepareCall(sql);
			cstmt.setString(1, id);
			cstmt.executeUpdate();
			result = 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw e;
		} finally {
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return result;
	}

	@Override
	public MemberDTO readMember(String id) {
		MemberDTO dto = null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		String sql = "{call readmember(?, ?)";

		// id 조건에 맞는 member1 테이블과 member2 테이블에 데이터를 OUTER JOIN 해서 검색
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, id);
			cstmt.executeUpdate();
			rs = (ResultSet)cstmt.getObject(1);
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
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return dto;
	}

	@Override
	public List<MemberDTO> listMember() {
		List<MemberDTO> list = new ArrayList<>();
		CallableStatement cstmt=null;
		ResultSet rs = null;
		String sql="{call listmember(?)}";

		// member1 테이블과 member2 테이블의 전체 데이터를 OUTER JOIN 해서 전체 리스트
		try {
			
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeQuery();
			rs = (ResultSet)cstmt.getObject(1);
			
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
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}

	@Override
	public List<MemberDTO> listMember(String val) {
		List<MemberDTO> list = new ArrayList<>();
		CallableStatement cstmt=null;
		ResultSet rs = null;
		String sql="{call searchnamemember(?, ?)}";

		// member1 테이블과 member2 테이블의 전체 데이터를 OUTER JOIN 해서 검색
		try {
			
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, val);
			cstmt.executeQuery();
			rs = (ResultSet) (cstmt.getObject(1));
					
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
			if (cstmt != null) {
				try {
					cstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}

}
