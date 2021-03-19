package db.member1;

import java.util.List;
import java.util.Scanner;


public class Member {
	private MemberDAO dao = new MemberDAOImpl();
	private Scanner sc = new Scanner(System.in);
	
	public void insert(){
		System.out.println("\n데이터 추가");
		
		MemberDTO dto = new MemberDTO();
		
		try {
			
			System.out.println("아이디?");
			dto.setId(sc.next());
			System.out.println("비밀번호?");
			dto.setPwd(sc.next());
			System.out.println("이름?");
			dto.setName(sc.next());
			System.out.println("생일?[yyyy-mm-dd]");
			dto.setBirth(sc.next());
			System.out.println("이메일?");
			dto.setEmail(sc.next());
			System.out.println("전화번호?");
			dto.setTel(sc.next());
			
			dao.insertMember(dto);
			System.out.println("회원 등록 완료");
			
		} catch (Exception e) {
			System.out.println("데이터 추가 실패");
			sc.nextLine();
		} 
	}
	
	public void update() {
		System.out.println("\n데이터 수정");
		
		try {
			
			System.out.println("수정할 아이디?");
			String id=sc.next();
			MemberDTO dto=dao.readMember(id);
			if(dto==null) {
				System.out.println("등록된 아이디가 아닙니다");
				return;
			}
			
			System.out.println("비밀번호?");
			dto.setPwd(sc.next());
			System.out.println("이름?");
			dto.setName(sc.next());
			System.out.println("생일?[yyyy-mm-dd]");
			dto.setBirth(sc.next());
			System.out.println("이메일?");
			dto.setEmail(sc.next());
			System.out.println("전화번호?");
			dto.setTel(sc.next());
			
			dao.updateMember(dto);
			System.out.println("데이터 수정 완료");
			
		} catch (Exception e) {
			System.out.println("데이터 수정 실패");
			sc.nextLine();
		} 
	}
	
	public void delete() {
		System.out.println("\n데이터 삭제");
		
		try {
			System.out.println("삭제할 아이디? ");
			String id=sc.next();
			if(dao.deleteMember(id)==0) {
				System.out.println("학번이 존재하지 않습니다");
				return;
			}
			System.out.println("삭제 완료");
			
		} catch (Exception e) {
			System.out.println("삭제 실패");
			sc.nextLine();
		} 
	}
	
	public void findById() {
		System.out.println("\n아이디 검색");
		
		String id;
		try {
			System.out.print("검색할 아이디 : ");
			id=sc.next();
			
			MemberDTO dto = dao.readMember(id);
			if(dto==null) {
				System.out.println("등록되지 않은 아이디");
				return;
			}
			System.out.println("아이디"+"\t"+"비밀번호"+"\t"+"이름"+"\t"+"생일"+"\t\t"+"이메일"+"\t"+"전화번호");
			System.out.print(dto.getId()+"\t");
			System.out.print(dto.getPwd()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getBirth()+"\t");
			System.out.print(dto.getEmail()+"\t");
			System.out.print(dto.getTel()+"\n");
			System.out.println();
			
		} catch (Exception e) {
			System.out.println("아이디 검색 실패");
		}
	}
	
	public void findByName() {
		System.out.println("\n이름 검색");
		String name;
		
		try {
			System.out.print("검색할 이름 : ");
			name=sc.next();
			
			List<MemberDTO> list = dao.listMember(name);
			if(list.size()==0) {
				System.out.println("등록된 이름 없음");
				return;
			}
			System.out.println("아이디"+"\t"+"비밀번호"+"\t"+"이름"+"\t"+"생일"+"\t\t"+"이메일"+"\t"+"전화번호");
			for(MemberDTO dto:list) {
				System.out.print(dto.getId()+"\t");
				System.out.print(dto.getPwd()+"\t");
				System.out.print(dto.getName()+"\t");
				System.out.print(dto.getBirth()+"\t");
				System.out.print(dto.getEmail()+"\t");
				System.out.print(dto.getTel()+"\n");
			}
			System.out.println();
			
		} catch (Exception e) {
			System.out.println("이름 검색 실패");
			sc.nextLine();
		}
	}
	
	public void listMember() {
		System.out.println("\n전체 리스트");
		
		List<MemberDTO> list = dao.listMember();
		
		System.out.println("전체 레코드수: "+list.size());
		System.out.println("아이디"+"\t"+"비밀번호"+"\t"+"이름"+"\t"+"생일"+"\t\t"+"이메일"+"\t"+"전화번호");
		for(MemberDTO dto:list) {
			System.out.print(dto.getId()+"\t");
			System.out.print(dto.getPwd()+"\t");
			System.out.print(dto.getName()+"\t");
			System.out.print(dto.getBirth()+"\t");
			System.out.print(dto.getEmail()+"\t");
			System.out.print(dto.getTel()+"\n");
		}
		System.out.println();
	}
}
