package db.employee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Salary {
	private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	private SalaryDAO dao=new SalaryDAO();
	private Employee emp=null;
	
	public Salary(Employee emp) {
		this.emp = emp;
	}
	
	public void salaryManage() {
		int ch = 0;
		while(true) {
			System.out.println("\n[�޿�����]");
			do {
				System.out.print("1.���� 2.���� 3.���� 4.��������Ʈ 5.����˻� 6.����Ʈ 7.�������Ʈ 8.���� => ");
				try {
					ch = Integer.parseInt(br.readLine());
				} catch (Exception e) {
				}
			}while(ch<1||ch>8);
			
			if(ch==8) return;
			
			switch(ch) {
			case 1:payment(); break;
			case 2:update(); break;
			case 3:delete(); break;
			case 4:monthList(); break;
			case 5:searchSabeon(); break;
			case 6:list(); break;
			case 7:emp.list(); break;
			}
		}
	}
	
	public void payment() {
		System.out.println("\n�޿� ����...");
		
		SalaryDTO dto = new SalaryDTO();
		
		try {
			System.out.println("���? ");
			dto.setSabeon(br.readLine());
			System.out.println("�޿����?[yyyymm] ");
			dto.setPayDate(br.readLine());
			System.out.println("���޳����?[yyyymmdd] ");
			dto.setPaymentDate(br.readLine());
			System.out.println("�⺻��? ");
			int pay=Integer.parseInt(br.readLine());
			dto.setPay(pay);
			System.out.println("����? ");
			int sudang=Integer.parseInt(br.readLine());
			dto.setSudang(sudang);
			System.out.println("�޸�? ");
			dto.setMemo(br.readLine());
			
			int tax=0;
			if(pay+sudang>=3000000)
				tax=((int) Math.floor((pay+sudang)*0.003))*10;
			else if (pay+sudang>=2000000)
				tax=((int) Math.floor((pay+sudang)*0.002))*10;
			dto.setTax(tax);
			
			
			int result=dao.insertSalary(dto);
			if(result==1)
				System.out.println("��� �Ϸ�Ǿ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		System.out.println("\n�޿� ����...");
		
		try {
			System.out.println("�����ϰ��� �ϴ� �޿���ȣ? ");
			SalaryDTO dto=dao.readSalary(Integer.parseInt(br.readLine()));
			System.out.println("�޿����?[yyyymm] ");
			dto.setPayDate(br.readLine());
			System.out.println("���޳����?[yyyymmdd] ");
			dto.setPaymentDate(br.readLine());
			System.out.println("�⺻��? ");
			int pay=Integer.parseInt(br.readLine());
			dto.setPay(pay);
			System.out.println("����? ");
			int sudang=Integer.parseInt(br.readLine());
			dto.setSudang(sudang);
			System.out.println("�޸�? ");
			dto.setMemo(br.readLine());
			
			int tax=0;
			if(pay+sudang>=3000000)
				tax=((int) Math.floor((pay+sudang)*0.003))*10;
			else if (pay+sudang>=2000000)
				tax=((int) Math.floor((pay+sudang)*0.002))*10;
			dto.setTax(tax);
			
			
			int result=dao.updateSalary(dto);
			if(result==0) {
				System.out.println("��ϵ� �޿� ���� �����ϴ�.");
				return;
			}
				System.out.println("���� �Ϸ�Ǿ����ϴ�.");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void delete() {
		System.out.println("\n�޿� ����...");
		
		try {
			System.out.println("������ �޿���ȣ?");
			int result=dao.deleteSalary(Integer.parseInt(br.readLine()));
			if(result==0) {
				System.out.println("��ϵ� �޿� ���� �����ϴ�.");
				return;
			}
			System.out.println("���� �Ϸ�Ǿ����ϴ�.");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void searchSabeon() {
		System.out.println("\n��� �˻�...");
		
		try {
			System.out.println("�˻��� ���?");
			String sabeon=br.readLine();
			System.out.println("�˻��� �޿�����?[yyyymm]");
			String paydate=br.readLine();
			Map<String, Object> map = new Map()<sabeon, paydate>;
			dao.listSalary(Map<sabeon, paydate>);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	public void monthList() {
		System.out.println("\n���� ����Ʈ...");
		
	}
	
	public void list() {
		System.out.println("\n�޿� ����Ʈ...");
	
	}
}
