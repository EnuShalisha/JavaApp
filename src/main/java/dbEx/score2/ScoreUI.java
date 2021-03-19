package dbEx.score1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ScoreUI {
	private ScoreDAO dao = new ScoreDAOImpl();
	private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	
	public void insert() {
		System.out.println("\n데이터 추가...");
		
		ScoreDTO dto=new ScoreDTO();
		
		try {
			System.out.print("학번?");
			dto.setHak(br.readLine());
			
			System.out.print("이름?");
			dto.setName(br.readLine());
			
			System.out.print("생년월일?");
			dto.setBirth(br.readLine());
			
			System.out.print("국어?");
			dto.setKor(Integer.parseInt(br.readLine()));
			
			System.out.print("영어?");
			dto.setEng(Integer.parseInt(br.readLine()));

			System.out.print("수학?");
			dto.setMat(Integer.parseInt(br.readLine()));
			
			dao.insertScore(dto);
			
			System.out.println("데이터를 추가했습니다.\n");
			
		} catch (NumberFormatException e) {
			System.out.println("점수는 숫자만 가능합니다.\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("추가 실패...\n");
		}
		
	}
	
	public void update() {
		System.out.println("\n데이터 수정...");
		
		try {
			String hak;
			System.out.print("수정할 학번?");
			hak = br.readLine();
			
			ScoreDTO dto = dao.readScore(hak);
			if(dto==null) {
				System.out.println("등록된 자료가 아닙니다.\n");
				return;
			}
			
			System.out.println("["+dto.getHak()+", "+dto.getName()+"] 자료 수정...");
			
			System.out.print("이름?");
			dto.setName(br.readLine());
			
			System.out.print("생년월일?");
			dto.setBirth(br.readLine());
			
			System.out.print("국어?");
			dto.setKor(Integer.parseInt(br.readLine()));
			
			System.out.print("영어?");
			dto.setEng(Integer.parseInt(br.readLine()));

			System.out.print("수학?");
			dto.setMat(Integer.parseInt(br.readLine()));
			
			dao.updateScore(dto);
			
			System.out.println("수정 완료...\n");
			
		} catch (NumberFormatException e) {
			System.out.println("점수는 숫자만 가능합니다.\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("수정 실패...\n");
		}
		
	}
	
	public void delete() {
		System.out.println("\n데이터 삭제...");
		
		String hak;
		
		try {
			System.out.print("삭제할 학번?");
			hak=br.readLine();
			
			int result = dao.deleteScore(hak);
			if(result == 0) {
				System.out.println("등록된 자료가 아닙니다.\n");
				return;
			}
			
			System.out.println("삭제 완료...\n");
			
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("삭제 실패...\n");
		}
		
	}
	
	public void findByHak() {
		System.out.println("\n학번 검색...");
		
		String hak;
		
		try {
			System.out.print("검색할 학번 ?");
			hak = br.readLine();
			
			ScoreDTO dto = dao.readScore(hak);
			if(dto==null) {
				System.out.println("등록된 자료가 없습니다.\n");
				return;
			}
			
			System.out.print(dto.getHak()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getBirth()+"\t");
			System.out.print(dto.getKor()+"\t");
			System.out.print(dto.getEng()+"\t");
			System.out.print(dto.getMat()+"\n");
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void findByName() {
		System.out.println("\n이름 검색...");
		
		String name;
		try {
			System.out.print("검색할 이름?");
			name=br.readLine();
			
			List<ScoreDTO> list = dao.listScore(name);
			for(ScoreDTO dto : list) {
				System.out.print(dto.getHak()+"\t");
				System.out.print(dto.getName()+"\t");
				System.out.print(dto.getBirth()+"\t");
				System.out.print(dto.getKor()+"\t");
				System.out.print(dto.getEng()+"\t");
				System.out.print(dto.getMat()+"\t");
				System.out.print(dto.getTot()+"\t");
				System.out.print(dto.getAve()+"\n");
			}
			System.out.println();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void list() {
		System.out.println("\n전체 리스트...");
		
		List<ScoreDTO> list = dao.listScore();
		
		System.out.println("전체 레코드수 : "+list.size());
		for(ScoreDTO dto : list) {
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
		System.out.println();
	}
	
}
