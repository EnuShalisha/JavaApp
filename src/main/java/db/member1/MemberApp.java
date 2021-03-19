package db.member1;

import java.util.Scanner;

public class MemberApp {

	public static void main(String[] args) {
		Member ui = new Member();
		Scanner sc = new Scanner(System.in);
		int ch;
		
		
		try {
			while(true) {
				do {
					System.out.print("1. 등록 2. 수정 3. 삭제 4. 아이디검색 5. 이름검색 6. 리스트 7. 종료 ");
					ch=sc.nextInt();
				}while(ch<1||ch>7);
				
				if(ch==7) {
					break;
				}
				
				switch(ch) {
				case 1:ui.insert(); break;
				case 2:ui.update(); break;
				case 3:ui.delete(); break;
				case 4:ui.findById(); break;
				case 5:ui.findByName(); break;
				case 6:ui.listMember(); break;
				}
			}
		} finally {
			sc.close();
		}

	}

}
