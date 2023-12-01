package com.jdbcFirstProgs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCInteractions {

	private static Connection con;
	private static PreparedStatement stmt;
	private static ResultSet res;
	private static Statement statemnt;
	private static final String url = "jdbc:mysql://localhost:3306/jdbc_practice";
	private static final String name = "root";
	private static final String password = "root";

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		try {
			con = DriverManager.getConnection(url,name,password);
						
			do {
				System.out.println("Do you want to\n"+" 1.Insert\n"+" 2.Delete\n"+" 3.Update\n"+" 4.display\n"+" press the corresponding number or press any other number to 'Exit'");
				int ch = sc.nextInt();
				if(ch == 1) {
					insert();	
				}
				else if(ch == 2) {
					delete();
				}
				else if(ch == 3) {
					update();
				}
				else if(ch==4) {
					display(con);
				}
				else {
					System.out.println("Thank you, You'r exited..");
					System.exit(1);
				}
				System.out.println();
			}
			while(true);	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			sc.close();
		}
	}

	
	private static void close() throws SQLException {
		if(res != null)
			res.close();
		if(statemnt != null)
			statemnt.close();
		if(stmt != null)
			stmt.close();
		if(con != null)
			con.close();
	
	}

	private static void insert() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		stmt = con.prepareStatement("insert into `employee`(`id`,`name`,`email`,`department`,`salary`) values(?,?,?,?,?)");
		
		System.out.println("Enter id:");
		stmt.setInt(1,sc.nextInt());
		sc.nextLine();
		
		System.out.println("Enter name:");
		stmt.setString(2,sc.nextLine());
		
		System.out.println("Enter email:");
		stmt.setString(3,sc.next());
		sc.nextLine();
		
		System.out.println("Enter department:");
		stmt.setString(4,sc.next());
		sc.nextLine();
		
		System.out.println("Enter salary:");
		stmt.setInt(5,sc.nextInt());
		
		
		int i = stmt.executeUpdate();
		System.out.println(i+" Rows Inserted");
	}
	
	private static void delete() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter column name:");
		String col = sc.nextLine();
		
		System.out.println("Enter value:");

		stmt = con.prepareStatement("Delete from `employee` where "+col+" = ?");
		
		if(col.equals("id")||col.equals("salary")) {
			stmt.setInt(1, sc.nextInt());
			sc.nextLine();
		}
		else {
			stmt.setString(1, sc.nextLine());
		}
	
		int i = stmt.executeUpdate();
		System.out.println(i+" Rows Deleted");	
		
	}
	
	private static void update() throws SQLException {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Column name:");
		String colname = sc.nextLine();
		
		stmt = con.prepareStatement("update `employee` set "+colname+" = ? where `id` = ?");
		System.out.println("Enter value:");
		
		if(colname.equalsIgnoreCase("salary")) {
			stmt.setInt(1, sc.nextInt());
			sc.nextLine();
		}
		else {
			stmt.setString(1, sc.nextLine());
		}
		
		System.out.println("Enter id number:"); 
		stmt.setInt(2, sc.nextInt());
		
		int i = stmt.executeUpdate();
		
		System.out.println(i+" Rows Updated");
		
	}
	
	public static void display(Connection con) throws SQLException {
		statemnt = con.createStatement();
		res = statemnt.executeQuery("SELECT * FROM `EMPLOYEE`");
		while(res.next()) {
			//System.out.printf("%-3d | %-9s | %-18s | %-8s | %d\n",res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5));
			System.out.println(res.getInt(1)+" "+res.getString(2)+" "+res.getString(3)+" "+res.getString(4)+" "+res.getInt(5)+" "+res.getBlob(6));
		}
		
	}


}
