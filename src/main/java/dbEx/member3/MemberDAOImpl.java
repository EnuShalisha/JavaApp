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
		
		// member1 ���̺�� member2 ���̺� ������ �߰�
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
        	System.out.println("�⺻Ű �ߺ�, NOT NULL���� �������� ���ݿ� ���� ���� �߻�-���Ἲ �������� ����");
			throw e;
        } catch (SQLDataException e) {
        	System.out.println("��¥���� ������ ���� ������ ���� ���� �߻�");
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
		
		
		// id ���ǿ� �´� member1 ���̺�� member2 ���̺� ������ ����
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
			System.out.println("��¥���� ������ ���� ������ ���� ���� �߻�");
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

		// id ���ǿ� �´� member1 ���̺�� member2 ���̺� ������ ����
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

		// id ���ǿ� �´� member1 ���̺�� member2 ���̺� �����͸� OUTER JOIN �ؼ� �˻�
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
				// ����Ŭ DATE���� �ڹٿ��� java.sql.Date�� ��ȯ �޴´�.
				// java.sql.Date�� yyyy-mm-dd �������� Ưȭ�Ǿ� �ִ�.
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

		// member1 ���̺�� member2 ���̺��� ��ü �����͸� OUTER JOIN �ؼ� ��ü ����Ʈ
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

		// member1 ���̺�� member2 ���̺��� ��ü �����͸� OUTER JOIN �ؼ� �˻�
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
