package dbEx.score3;

import java.util.List;
import java.util.Map;

// CRUD : Create(insert), Retrieve(select), Update, Delete
// DAO : data Access Object 
//       - insert, update, delete, select ���� ������ ó���� �� �������� �ϴ� Ŭ����
public interface ScoreDAO {
	public int insertScore(ScoreDTO dto) throws Exception;
	public int updateScore(ScoreDTO dto) throws Exception;
	public int deleteScore(String hak) throws Exception;
	
	public ScoreDTO readScore(String hak);
	public List<ScoreDTO> listScore();
	public List<ScoreDTO> listScore(String name);
	
	public Map<String, Integer> averageScore();
}
