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
		
		//���±��� sql���� ���� ���� ū ���� - Ʈ����� ���� ����
		String sql="{ CALL insertscore(?,?,?,?,?,?)}";
		try(CallableStatement cstmt=conn.prepareCall(sql)) {
			
			
			cstmt.setString(1, dto.getHak());
			cstmt.setString(2, dto.getName());
			cstmt.setString(3, dto.getBirth());
			cstmt.setInt(4, dto.getKor());
			cstmt.setInt(5, dto.getEng());
			cstmt.setInt(6, dto.getMat());
			
			//���� ���� - ���� �Ķ���Ϳ� ���� ���� x
			//���ϰ��� insert�� �ƴ� ���ν��� ���� ����
			cstmt.executeUpdate();
			result=1;
			
			
		} catch (SQLIntegrityConstraintViolationException e) {
			
			System.out.print("�������� �����Դϴ� : ");
			if(e.getErrorCode()==1) 
				System.out.println("�⺻Ű �ߺ� ����");
			else if(e.getErrorCode()==1400) 
				System.out.println("NOT NULL ����");
			else System.out.println("���� ���� ����");
			throw e;
			
		} catch (SQLDataException e) {
			
			System.out.println("������ ���� �����Դϴ�.");
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
				System.out.println("��ϵ� �ڷ� ����");
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
				System.out.println("��ϵ� �ڷ� ����");
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
			cstmt.registerOutParameter(1, OracleTypes.CURSOR); // �̰� out �Ķ����
			cstmt.setString(2, hak);
			//executeQuery: select�� ���� �� ������ ResultSet���� �޴� ���
			//executeUpdate: ResultSet�� ������ �޴� �� �ƴ� �Ķ���� ���ุ �ϴ� ��
			//��� ���ν����� executeUpdate()�� ����
			cstmt.executeUpdate();
			//out �Ķ���͸� object�� �޾ƿ��� ���
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
			
			// SELECT ����
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.executeUpdate();
			rs = (ResultSet) cstmt.getObject(1);
			
			while(rs.next()) {
				ScoreDTO dto=new ScoreDTO();
				
				dto.setHak(rs.getString("hak"));
				// dto.setHak(rs.getString(1));
				dto.setName(rs.getString("name"));
				// dto.setBirth(rs.getString("birth")); // �⺻:yyyy-mm-dd hh:mi:ss
				dto.setBirth(rs.getDate("birth").toString()); // yyyy-mm-dd(�����������ѱ��ΰ��)
				    // rs.getDate() => java.sql.Date ��
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getInt("ave"));
				dto.setRank(rs.getInt("rank"));
				
				// ArrayList��ü�� ����
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
