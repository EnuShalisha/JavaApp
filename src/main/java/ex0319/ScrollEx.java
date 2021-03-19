package ex0319;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import db.util.DBConn;

public class ScrollEx {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		Statement stmt=null;
		ResultSet rs=null;
		
		char ch;
		String sql;
		
		try {
			sql = "select hak, name, birth, kor, eng, mat from score";
			
			//순방향 이동만 가능 stm=con.createstatement();
			
			//결과 집합: 
			//TYPE_FORWARD_ONLY: 기본값, 순방향만 가능
			//TYPE_SCROLL_SENSITIVE: 순/역방향 이동 가능, 수정결과 바로반영
			//항상 최신의 데이터
			//TYPE_SCROLL_INSENSITIVE: 순/역방향 이동 가능, 수정결과 반영 안됨
			//대신 속도 빠름
			
			//동시성:
			//CONCUR_READ_ONLY: 기본값, 읽기 전용
			//CONCUR_UPDATBLE: 수정 가능, 즉 업데이트 가능	
			stmt=conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			
			rs=stmt.executeQuery(sql);
			
			while(true) {
				do {
					System.out.print("1. 처음 2. 이전 3. 다음 4. 마지막 5. 종료");
					ch=(char)System.in.read();
					System.in.skip(2);
				} while (ch<'1'||ch>'5');
			
				if(ch=='5') break;
				
				switch(ch) {
				case '1':
					if(rs.first())
						System.out.println("처음->"+rs.getString(1)+","+rs.getString(2)); 
					break;
				case '2': 
					if(rs.previous())
						System.out.println("이전->"+rs.getString(1)+","+rs.getString(2));
					break;
				case '3': 
					if(rs.next())
						System.out.println("다음->"+rs.getString(1)+","+rs.getString(2));
					break;
				case '4': 
					if(rs.last())
						System.out.println("끝->"+rs.getString(1)+","+rs.getString(2));
					break;
				}
			
			}
			
			rs.close();
			stmt.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
