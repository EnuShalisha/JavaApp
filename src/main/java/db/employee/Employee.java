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
			System.out.println("\n[�������]");
			
			do {
				System.out.print("1.������ 2.�������� 3.����˻� 4.�̸��˻� 5.����Ʈ 6.���� => ");
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
		System.out.println("\n��� ���...");
		
		EmployeeDTO dto = new EmployeeDTO();
		
		try {
			System.out.println("���? ");
			dto.setSabeon(br.readLine());
			System.out.println("�̸�? ");
			dto.setName(br.readLine());
			System.out.println("�������? ");
			dto.setBirth(br.readLine());
			System.out.println("��ȭ��ȣ? ");
			dto.setTel(br.readLine());
			
			int result=dao.insertEmployee(dto);
			if(result==1)
				System.out.println("��� �Ϸ�Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void update() {
		System.out.println("\n��� ���� ����...");
		
		try {
			System.out.println("�����ϰ��� �ϴ� ���? ");
			EmployeeDTO dto=dao.readEmployee(br.readLine());
			if(dto==null) {
				System.out.println("��ϵ� ����� �����ϴ�.");
				return;
			}
			
			System.out.println("�̸�? ");
			dto.setName(br.readLine());
			System.out.println("�������? ");
			dto.setBirth(br.readLine());
			System.out.println("��ȭ��ȣ? ");
			dto.setTel(br.readLine());
			
			int result=dao.updateEmployee(dto);
			if(result==0) {
				System.out.println("��ϵ� ����� �����ϴ�.");
				return;
			}
			System.out.println("���� �Ϸ�Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public void searchSabeon() {
		System.out.println("\n��� �˻�...");
		
		try {
			System.out.println("�˻��� ���?");
			EmployeeDTO dto = dao.readEmployee(br.readLine());
			if(dto==null) {
				System.out.println("��ϵ� ����� �����ϴ�.");
				return;
			}
			
			System.out.println("���\t�̸�\t�������\t\t��ȭ��ȣ");
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
		System.out.println("\n�̸� �˻�...");
		
		try {
			System.out.println("�˻��� �̸�?");
			List<EmployeeDTO> list = dao.listEmployee(br.readLine());
			if(list.size()==0) {
				System.out.println("��ϵ� �̸��� �����ϴ�.");
				return;
			}
			
			System.out.println("�˻��� ���: "+list.size()+"��");
			System.out.println("���\t�̸�\t�������\t\t��ȭ��ȣ");
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
		System.out.println("\n��� ����Ʈ...");
		
		try {
			List<EmployeeDTO> list = dao.listEmployee();
			if(list.size()==0) {
				System.out.println("��ϵ� ����� �����ϴ�.");
				return;
			}
			
			System.out.println("�˻��� ���: "+list.size()+"��");
			System.out.println("���\t�̸�\t�������\t\t��ȭ��ȣ");
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
