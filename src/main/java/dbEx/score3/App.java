package dbEx.score3;

import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		ScoreUI ui=new ScoreUI();
		Scanner sc=new Scanner(System.in);
		int ch;
			
		System.out.println("CallableStatement 예제...");
			
		try {
			while(true) {
				do {
					System.out.print("1.등록 2.수정 3.삭제 4.학번검색 5.이름검색 6.리스트 7.평균 8.종료 => ");
					ch = sc.nextInt();
				} while(ch<1||ch>8);
				
				if(ch==8) {
					break;
				}

				switch(ch) {
				case 1:ui.insert(); break;
				case 2:ui.update(); break;
				case 3:ui.delete(); break;
				case 4:ui.findByHak(); break;
				case 5:ui.findByName(); break;
				case 6:ui.list(); break;
				case 7:ui.average(); break;
				}
			}
		} finally {
			sc.close();
		}
		
	}

}
