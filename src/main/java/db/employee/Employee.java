package db.employee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Employee {
	private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	private EmployeeDAO dao=new EmployeeDAO();
	
	public void employeeManage() {
		int ch = 0;
		while(true) {
			System.out.println("\n[사원관리]");
			
			do {
				System.out.print("1.사원등록 2.정보수정 3.사번검색 4.이름검색 5.리스트 6.메인 => ");
				try {
					ch = Integer.parseInt(br.readLine());
				} catch (Exception e) {
				}
			}while(ch<1||ch>6);
			
			if(ch==6) return;
			
			switch(ch) {
			case 1: insert(); break;
			case 2: update(); break;
			case 3: searchSabeon(); break;
			case 4: searchName(); break;
			case 5: list(); break;
			}
		}
	}
	
	public void insert() {
		System.out.println("\n사원 등록...");
		
		EmployeeDTO dto = new EmployeeDTO();
		
		try {
			System.out.println("사번? ");
			dto.setSabeon(br.readLine());
			System.out.println("이름? ");
			dto.setName(br.readLine());
			System.out.println("생년월일? ");
			dto.setBirth(br.readLine());
			System.out.println("전화번호? ");
			dto.setTel(br.readLine());
			
			int result=dao.insertEmployee(dto);
			if(result==1)
				System.out.println("등록 완료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void update() {
		System.out.println("\n사원 정보 수정...");
		
		try {
			System.out.println("수정하고자 하는 사번? ");
			EmployeeDTO dto=dao.readEmployee(br.readLine());
			if(dto==null) {
				System.out.println("등록된 사번이 없습니다.");
				return;
			}
			
			System.out.println("이름? ");
			dto.setName(br.readLine());
			System.out.println("생년월일? ");
			dto.setBirth(br.readLine());
			System.out.println("전화번호? ");
			dto.setTel(br.readLine());
			
			int result=dao.updateEmployee(dto);
			if(result==0) {
				System.out.println("등록된 사원이 없습니다.");
				return;
			}
			System.out.println("수정 완료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void searchSabeon() {
		System.out.println("\n사번 검색...");
		
		try {
			System.out.println("검색할 사번?");
			EmployeeDTO dto = dao.readEmployee(br.readLine());
			if(dto==null) {
				System.out.println("등록된 사번이 없습니다.");
				return;
			}
			
			System.out.println("사번\t이름\t생년월일\t\t전화번호");
			System.out.print(dto.getSabeon()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getBirth()+"\t");
			System.out.print(dto.getTel()+"\n");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		
		
	}

	public void searchName() {
		System.out.println("\n이름 검색...");
		
		try {
			System.out.println("검색할 이름?");
			List<EmployeeDTO> list = dao.listEmployee(br.readLine());
			if(list.size()==0) {
				System.out.println("등록된 이름이 없습니다.");
				return;
			}
			
			System.out.println("검색된 사원: "+list.size()+"명");
			System.out.println("사번\t이름\t생년월일\t\t전화번호");
			for(EmployeeDTO dto:list) {
				System.out.print(dto.getSabeon()+"\t");
				System.out.print(dto.getName()+"\t");
				System.out.print(dto.getBirth()+"\t");
				System.out.print(dto.getTel()+"\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}
	
	public void list() {
		System.out.println("\n사원 리스트...");
		
		try {
			List<EmployeeDTO> list = dao.listEmployee();
			if(list.size()==0) {
				System.out.println("등록된 사원이 없습니다.");
				return;
			}
			
			System.out.println("검색된 사원: "+list.size()+"명");
			System.out.println("사번\t이름\t생년월일\t\t전화번호");
			for(EmployeeDTO dto:list) {
				System.out.print(dto.getSabeon()+"\t");
				System.out.print(dto.getName()+"\t");
				System.out.print(dto.getBirth()+"\t");
				System.out.print(dto.getTel()+"\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();

	}
	
}
