package com.score1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ScoreUI {
	private ScoreDAO dao = new ScoreDAOImpl();
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public void insert(){
		System.out.println("\n데이터 추가");
		
		ScoreDTO dto = new ScoreDTO();
		
		try {
			
			System.out.println("학번?");
			dto.setHak(br.readLine());
			System.out.println("이름?");
			dto.setName(br.readLine());
			System.out.println("생일?[yyyy-mm-dd]");
			dto.setBirth(br.readLine());
			System.out.println("국어?");
			dto.setKor(Integer.parseInt(br.readLine()));
			System.out.println("영어?");
			dto.setEng(Integer.parseInt(br.readLine()));
			System.out.println("수학?");
			dto.setMat(Integer.parseInt(br.readLine()));
			
			dao.insertScore(dto);
			System.out.println("데이터 추가 완료");
			
		} catch (NumberFormatException e) {
			System.out.println("점수는 숫자만 가능합니다");
		} catch (Exception e) {
			System.out.println("데이터 추가 실패");
		}
	}
	
	public void update() {
		System.out.println("\n데이터 수정");
		
		try {
			
			System.out.println("수정할 학번?");
			String hak=br.readLine();
			ScoreDTO dto=dao.readScore(hak);
			if(dto==null) {
				System.out.println("등록된 자료 아닙니다");
				return;
			}
			
			System.out.println("이름?");
			dto.setName(br.readLine());
			System.out.println("생일?[yyyy-mm-dd]");
			dto.setBirth(br.readLine());
			System.out.println("국어?");
			dto.setKor(Integer.parseInt(br.readLine()));
			System.out.println("영어?");
			dto.setEng(Integer.parseInt(br.readLine()));
			System.out.println("수학?");
			dto.setMat(Integer.parseInt(br.readLine()));
			
			dao.updateScore(dto);
			System.out.println("데이터 수정 완료");
			
		} catch (NumberFormatException e) {
			System.out.println("점수는 숫자만 가능합니다");
		} catch (Exception e) {
			System.out.println("데이터 수정 실패");
		}
	}
	
	public void delete() {
		System.out.println("\n데이터 삭제");
		
		try {
			System.out.println("삭제할 학번? ");
			String hak=br.readLine();
			if(dao.deleteScore(hak)==0) {
				System.out.println("학번이 존재하지 않습니다");
				return;
			}
			System.out.println("삭제 완료");
			
		} catch (Exception e) {
			System.out.println("삭제 실패");
		}
	}
	
	public void findByHak() {
		System.out.println("\n학번 검색");
		
		String hak;
		try {
			System.out.print("검색할 학번 : ");
			hak=br.readLine();
			
			ScoreDTO dto = dao.readScore(hak);
			if(dto==null) {
				System.out.println("등록되지 않은 학번");
			}
			System.out.println("학번"+"\t"+"이름"+"\t"+"생일"+"\t\t"+"국어"+"\t"+"영어"+"\t"+"수학");
			System.out.print(dto.getHak()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getBirth()+"\t");
			System.out.print(dto.getKor()+"\t");
			System.out.print(dto.getEng()+"\t");
			System.out.print(dto.getMat()+"\n");
			System.out.println();
			
		} catch (Exception e) {
			System.out.println("학번 검색 실패");
		}
	}
	
	public void findByName() {
		System.out.println("\n이름 검색");
		
		String name;
		try {
			System.out.print("검색할 이름 : ");
			name=br.readLine();
			
			List<ScoreDTO> list = dao.listScore(name);
			if(list.size()==0) {
				System.out.println("등록된 이름 없음");
			}
			System.out.println("학번"+"\t"+"이름"+"\t"+"생일"+"\t\t"+"국어"+"\t"+"영어"+"\t"+"수학"
					+"\t"+"합계"+"\t"+"평균");
			for(ScoreDTO dto:list) {
				System.out.print(dto.getHak()+"\t");
				System.out.print(dto.getName()+"\t");
				System.out.print(dto.getBirth()+"\t");
				System.out.print(dto.getKor()+"\t");
				System.out.print(dto.getEng()+"\t");
				System.out.print(dto.getMat()+"\t");
				System.out.print(dto.getTot()+"\t");
				System.out.print(dto.getAve()+"\n");
			}
			
		} catch (Exception e) {
			System.out.println("이름 검색 실패");
		}
	}
	
	public void list() {
		System.out.println("\n전체 리스트");
		
		List<ScoreDTO> list = dao.listScore();
		
		System.out.println("전체 레코드수: "+list.size());
		System.out.println("학번"+"\t"+"이름"+"\t"+"생일"+"\t\t"+"국어"+"\t"+"영어"+"\t"+"수학"+"\t"+"합계"
				+"\t"+"평균"+"\t"+"등수");
		for(ScoreDTO dto:list) {
			System.out.print(dto.getHak()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getBirth()+"\t");
			System.out.print(dto.getKor()+"\t");
			System.out.print(dto.getEng()+"\t");
			System.out.print(dto.getMat()+"\t");
			System.out.print(dto.getTot()+"\t");
			System.out.print(dto.getAve()+"\t");
			System.out.print(dto.getRank()+"\n");
		}
	}
}
