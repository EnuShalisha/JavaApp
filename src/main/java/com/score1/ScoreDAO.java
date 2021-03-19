package com.score1;

import java.util.List;

// CRUD : create, retrieve, update, delete
// DAO: data access object - insert update delete select 데이터 처리 목적
public interface ScoreDAO {
	public void insertScore(ScoreDTO dto) throws Exception;
	public void updateScore(ScoreDTO dto) throws Exception;
	public int deleteScore(String hak) throws Exception;
	
	public ScoreDTO readScore(String hak);
	public List<ScoreDTO> listScore();
	public List<ScoreDTO> listScore(String name);
	
	
}
