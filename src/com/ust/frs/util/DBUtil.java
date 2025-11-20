package com.ust.frs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	public static Connection getDBConnection()
	{
		Connection con=null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con =  DriverManager.getConnection("jdbc:mysql://localhost:3306/ustglobal","root","pass@word1");
		}
		catch(ClassNotFoundException cnf)
		{
			System.out.println(cnf);
		}
		catch(SQLException sql)
		{
			System.out.println(sql);
		}
		return con ;
	}
}
