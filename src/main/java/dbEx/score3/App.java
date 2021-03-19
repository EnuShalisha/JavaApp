package dbEx.score3;

import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		ScoreUI ui=new ScoreUI();
		Scanner sc=new Scanner(System.in);
		int ch;
			
		System.out.println("CallableStatement ����...");
			
		try {
			while(true) {
				do {
					System.out.print("1.��� 2.���� 3.���� 4.�й��˻� 5.�̸��˻� 6.����Ʈ 7.��� 8.���� => ");
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
