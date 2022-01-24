package java_Assgn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


class StudentDemo
{
	int STUDENT_NO;
	String STUDENT_NAME = new String();
	Date STUDENT_DOB = new Date(0);
	Date STUDENT_DOJ = new Date(0);
	
	public void setValues() throws IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Enter Name: ");
		STUDENT_NAME = (br.readLine());
		
		System.out.print("Enter Roll No: ");
		STUDENT_NO = Integer.parseInt(br.readLine());
		
		System.out.print("Enter Date of Birth: ");
		STUDENT_DOB = Date.valueOf(br.readLine());
		
		System.out.print("Enter Date of Joining: ");
		STUDENT_DOJ = Date.valueOf(br.readLine());
	}
	
	public Connection CreateConnection() throws Exception
	{
		String url = "jdbc:mysql://localhost:3306/student";
		String user = "root";
		String pass = "TEMA3CTgGO";
			
		Connection con = null;
		
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, pass);
			
			return con;
		} 
		catch(Exception e) 
		{
			System.out.println(e);
		} 
		
		return null;
	}
	
	public void insertRecord() throws Exception 
	{
		Connection con = CreateConnection();
		String query = "INSERT INTO student(STUDENT_NO, STUDENT_NAME, STUDENT_DOB, STUDENT_DOJ) VALUES(?, ?, ?, ?)";
	
		if(con.isValid(1)) 
		{
//			System.out.println("Connection successful");
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setInt(1, STUDENT_NO);
			stmt.setString(2, STUDENT_NAME);
			stmt.setDate(3, STUDENT_DOB);
			stmt.setDate(4, STUDENT_DOJ);
			
			int record = stmt.executeUpdate();
			System.out.println(record + " record inserted!");
			con.close();
		}
	}
	
	public void updateValues() throws Exception
	{
		Connection con = CreateConnection();
		String sql = "UPDATE student SET STUDENT_NAME = (?), STUDENT_DOB = (?) , STUDENT_DOJ = (?) WHERE STUDENT_NO = (?)";
		setValues();
		
		if(con.isValid(1))
		{
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(4, STUDENT_NO);
			stmt.setString(1, STUDENT_NAME);
			stmt.setDate(2, STUDENT_DOB);
			stmt.setDate(3, STUDENT_DOJ);
	 
		int rowsUpdated = stmt.executeUpdate();
		
		if (rowsUpdated > 0) 
		{
		    System.out.println("An existing user was updated successfully!");
		    con.close();
		}
		con.close();
		}	
	  
	}


	
	public void deleteValues() throws Exception
	{
		Connection con = CreateConnection();
		String sql = "DELETE FROM student WHERE STUDENT_NO=(?)";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter student roll no: ");
		int rno = Integer.parseInt(br.readLine());
		
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setInt(1, rno);
		 
		int rowsDeleted = statement.executeUpdate();
		if (rowsDeleted > 0) 
		{
		    System.out.println("A user was deleted successfully!");
		    con.close();
		}
		con.close();
	}
	
		public void getStudentList() throws Exception
		{
			Connection con = CreateConnection();
			ResultSet rs = null;
			
			try
			{
				String sql = "SELECT *  FROM student ";
				PreparedStatement statement = con.prepareStatement(sql);
				rs = statement.executeQuery();
				System.out.println("RollNo\t Name\t DOB\t DOJ");
				
				while (rs.next()) 
				{
					 
	                int STUDENT_NO = rs.getInt("ROLLNO");
	                String STUDENT_NAME = rs.getString("NAME");
	                Date STUDENT_DOB = rs.getDate("DOB");
	                Date STUDENT_DOJ = rs.getDate("DOJ");
	                System.out.println( STUDENT_NO + "\t\t" + STUDENT_NAME + "\t\t" + STUDENT_DOB + "\t\t" + STUDENT_DOJ);
	            }
				con.close();
				
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
			
		}

}

public class Student 
{
	public static void main(String args[]) throws Exception
	{
		StudentDemo studObj = new StudentDemo();
		for(int count = 0; count < 3; count++) 
		{
			studObj.setValues();
			studObj.insertRecord();
			studObj.updateValues();
			studObj.deleteValues();
			studObj.getStudentList();
		}
	}

}
