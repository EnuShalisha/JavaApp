package test0316;

import java.sql.Connection;

import db.util.DBConn;

public class TestApp {

	public static void main(String[] args) {
		Connection conn = DBConn.getConnection();
		System.out.println(conn);

	}

}
