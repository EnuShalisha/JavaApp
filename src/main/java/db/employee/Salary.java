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
			System.out.println("\n[급여관리]");
			do {
				System.out.print("1.지급 2.수정 3.삭제 4.월별리스트 5.사번검색 6.리스트 7.사원리스트 8.메인 => ");
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
		System.out.println("\n급여 지급...");
		
		SalaryDTO dto = new SalaryDTO();
		
		try {
			System.out.println("사번? ");
			dto.setSabeon(br.readLine());
			System.out.println("급여년월?[yyyymm] ");
			dto.setPayDate(br.readLine());
			System.out.println("지급년월일?[yyyymmdd] ");
			dto.setPaymentDate(br.readLine());
			System.out.println("기본급? ");
			int pay=Integer.parseInt(br.readLine());
			dto.setPay(pay);
			System.out.println("수당? ");
			int sudang=Integer.parseInt(br.readLine());
			dto.setSudang(sudang);
			System.out.println("메모? ");
			dto.setMemo(br.readLine());
			
			int tax=0;
			if(pay+sudang>=3000000)
				tax=((int) Math.floor((pay+sudang)*0.003))*10;
			else if (pay+sudang>=2000000)
				tax=((int) Math.floor((pay+sudang)*0.002))*10;
			dto.setTax(tax);
			
			
			int result=dao.insertSalary(dto);
			if(result==1)
				System.out.println("등록 완료되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		System.out.println("\n급여 수정...");
		
		try {
			System.out.println("수정하고자 하는 급여번호? ");
			SalaryDTO dto=dao.readSalary(Integer.parseInt(br.readLine()));
			System.out.println("급여년월?[yyyymm] ");
			dto.setPayDate(br.readLine());
			System.out.println("지급년월일?[yyyymmdd] ");
			dto.setPaymentDate(br.readLine());
			System.out.println("기본급? ");
			int pay=Integer.parseInt(br.readLine());
			dto.setPay(pay);
			System.out.println("수당? ");
			int sudang=Integer.parseInt(br.readLine());
			dto.setSudang(sudang);
			System.out.println("메모? ");
			dto.setMemo(br.readLine());
			
			int tax=0;
			if(pay+sudang>=3000000)
				tax=((int) Math.floor((pay+sudang)*0.003))*10;
			else if (pay+sudang>=2000000)
				tax=((int) Math.floor((pay+sudang)*0.002))*10;
			dto.setTax(tax);
			
			
			int result=dao.updateSalary(dto);
			if(result==0) {
				System.out.println("등록된 급여 사항 없습니다.");
				return;
			}
				System.out.println("수정 완료되었습니다.");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void delete() {
		System.out.println("\n급여 삭제...");
		
		try {
			System.out.println("삭제할 급여번호?");
			int result=dao.deleteSalary(Integer.parseInt(br.readLine()));
			if(result==0) {
				System.out.println("등록된 급여 사항 없습니다.");
				return;
			}
			System.out.println("삭제 완료되었습니다.");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void searchSabeon() {
		System.out.println("\n사번 검색...");
		
		try {
			System.out.println("검색할 사번?");
			String sabeon=br.readLine();
			System.out.println("검색할 급여월일?[yyyymm]");
			String paydate=br.readLine();
			Map<String, Object> map = new Map()<sabeon, paydate>;
			dao.listSalary(Map<sabeon, paydate>);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	public void monthList() {
		System.out.println("\n월별 리스트...");
		
	}
	
	public void list() {
		System.out.println("\n급여 리스트...");
	
	}
}
