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
		Statement stmt=null; // ���� ���� ��ü
		String sql;
		
		try {
			// INSERT INTO score(hak,name,birth,kor,eng,mat) VALUES ('11','ȫ�浿','2000-10-10',80,90,80);
			sql = "INSERT INTO score(hak,name,birth,kor,eng,mat) VALUES ("
				+ "'"+dto.getHak()+"','"+dto.getName()+"','"+dto.getBirth()+"',"
				+ dto.getKor()+","+dto.getEng()+","+dto.getMat()+")";
			
			// Statement ��ü�� Connection���� ����
			stmt = conn.createStatement();
			
			// ���� ����(INSERT, UPDATE, DELETE, CREATE, ALTER, DROP ��)
			result = stmt.executeUpdate(sql);
			  // ���ϰ� : INSERT, UPDATE, DELETE�� ���� ��� ��ȯ
		} catch (SQLIntegrityConstraintViolationException e) {
			// �⺻Ű �ߺ�, NOT NULL���� ���� ���� ����
			if(e.getErrorCode()==1) {
				System.out.println("�⺻Ű ���� ����-�й� �ߺ��Դϴ�.");
			} else if(e.getErrorCode()==1400) {
				System.out.println("NOT NULL ����-��� �����ʹ� �ݵ�� �Է��ؾ� �մϴ�.");
			} else {
				System.out.println("���� ���� �����Դϴ�.");
			}
			
			throw e; // ���ܸ� �ٽ� ����
		} catch (SQLDataException e) {
			System.out.println("��¥���ĵ��� ���� �Դϴ�.");
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
			System.out.println("��¥���ĵ��� ���� �Դϴ�.");
			
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
			
			// SELECT ����
			rs = stmt.executeQuery(sb.toString());
			
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
