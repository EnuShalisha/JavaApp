package dbEx.member1;

import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		MemberUI ui = new MemberUI();
		Scanner sc=new Scanner(System.in);
		int ch;
		
		try {
			while(true) {
				do {
					System.out.print("1.ȸ������ 2.�������� 3.ȸ��Ż�� 4.���̵�˻� 5.�̸��˻� 6.����Ʈ 7.���� => ");
					ch = sc.nextInt();
				} while(ch<1||ch>7);
				
				if(ch==7)
					break;
				
				switch(ch) {
				case 1:ui.insert(); break;
				case 2:ui.update(); break;
				case 3:ui.delete(); break;
				case 4:ui.findById(); break;
				case 5:ui.findByName(); break;
				case 6:ui.list(); break;
				}
			}
		} finally {
			sc.close();
		}
	}
}
