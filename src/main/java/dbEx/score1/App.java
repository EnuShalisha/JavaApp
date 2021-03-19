package dbEx.score1;

import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		ScoreUI ui=new ScoreUI();
		Scanner sc=new Scanner(System.in);
		int ch;
			
		System.out.println("Statement ����...");
			
		try {
			while(true) {
				do {
					System.out.print("1.��� 2.���� 3.���� 4.�й��˻� 5.�̸��˻� 6.����Ʈ 7.���� => ");
					ch = sc.nextInt();
				} while(ch<1||ch>7);
				
				if(ch==7) {
					break;
				}

				switch(ch) {
				case 1:ui.insert(); break;
				case 2:ui.update(); break;
				case 3:ui.delete(); break;
				case 4:ui.findByHak(); break;
				case 5:ui.findByName(); break;
				case 6:ui.list(); break;
				}
			}
		} finally {
			sc.close();
		}
		
	}

}
