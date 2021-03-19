package dbEx.score1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ScoreUI {
	private ScoreDAO dao = new ScoreDAOImpl();
	private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	
	public void insert() {
		System.out.println("\n������ �߰�...");
		
		ScoreDTO dto=new ScoreDTO();
		
		try {
			System.out.print("�й�?");
			dto.setHak(br.readLine());
			
			System.out.print("�̸�?");
			dto.setName(br.readLine());
			
			System.out.print("�������?");
			dto.setBirth(br.readLine());
			
			System.out.print("����?");
			dto.setKor(Integer.parseInt(br.readLine()));
			
			System.out.print("����?");
			dto.setEng(Integer.parseInt(br.readLine()));

			System.out.print("����?");
			dto.setMat(Integer.parseInt(br.readLine()));
			
			dao.insertScore(dto);
			
			System.out.println("�����͸� �߰��߽��ϴ�.\n");
			
		} catch (NumberFormatException e) {
			System.out.println("������ ���ڸ� �����մϴ�.\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("�߰� ����...\n");
		}
		
	}
	
	public void update() {
		System.out.println("\n������ ����...");
		
		try {
			String hak;
			System.out.print("������ �й�?");
			hak = br.readLine();
			
			ScoreDTO dto = dao.readScore(hak);
			if(dto==null) {
				System.out.println("��ϵ� �ڷᰡ �ƴմϴ�.\n");
				return;
			}
			
			System.out.println("["+dto.getHak()+", "+dto.getName()+"] �ڷ� ����...");
			
			System.out.print("�̸�?");
			dto.setName(br.readLine());
			
			System.out.print("�������?");
			dto.setBirth(br.readLine());
			
			System.out.print("����?");
			dto.setKor(Integer.parseInt(br.readLine()));
			
			System.out.print("����?");
			dto.setEng(Integer.parseInt(br.readLine()));

			System.out.print("����?");
			dto.setMat(Integer.parseInt(br.readLine()));
			
			dao.updateScore(dto);
			
			System.out.println("���� �Ϸ�...\n");
			
		} catch (NumberFormatException e) {
			System.out.println("������ ���ڸ� �����մϴ�.\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("���� ����...\n");
		}
		
	}
	
	public void delete() {
		System.out.println("\n������ ����...");
		
		String hak;
		
		try {
			System.out.print("������ �й�?");
			hak=br.readLine();
			
			int result = dao.deleteScore(hak);
			if(result == 0) {
				System.out.println("��ϵ� �ڷᰡ �ƴմϴ�.\n");
				return;
			}
			
			System.out.println("���� �Ϸ�...\n");
			
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("���� ����...\n");
		}
		
	}
	
	public void findByHak() {
		System.out.println("\n�й� �˻�...");
		
		String hak;
		
		try {
			System.out.print("�˻��� �й� ?");
			hak = br.readLine();
			
			ScoreDTO dto = dao.readScore(hak);
			if(dto==null) {
				System.out.println("��ϵ� �ڷᰡ �����ϴ�.\n");
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
		System.out.println("\n�̸� �˻�...");
		
		String name;
		try {
			System.out.print("�˻��� �̸�?");
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
		System.out.println("\n��ü ����Ʈ...");
		
		List<ScoreDTO> list = dao.listScore();
		
		System.out.println("��ü ���ڵ�� : "+list.size());
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
