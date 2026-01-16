package com.util;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.util.DBUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet{

	
		private static final long serialVersionUID = 1L;
		public login() {
			super();
		}
	  @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			 Connection connection=null;
			 PreparedStatement preparedStatement=null;
			 ResultSet resultSet=null;
	         res.setContentType("text/html");
	         HttpSession session = req.getSession(true);
	     
			 String username=req.getParameter("username").toLowerCase();
			
			 String enetrpassword= req.getParameter("password");
			
			 try { 
                               connection = DBUtil.getMySqlConnection("gpsdata");
				preparedStatement=connection.prepareStatement("select password from gpsdata.login where username=?");
				preparedStatement.setString(1, username);
				
				resultSet=preparedStatement.executeQuery();
				
				if(resultSet.next())
				{
					 String actualPassword= resultSet.getString("password");
					 if(enetrpassword.equals(actualPassword)) {
						 session.setAttribute("username", username);
					
//					 res.getWriter().print("<h1 style='color:red; margin-left:250px;height:40px;width:70%; padding:20px; text-align:center;'>Login Successfully</h1>");
				     res.sendRedirect("mainpage.jsp");
//					 req.getRequestDispatcher("mainPage.html").forward(req, res);
//					  viewName = "mainpage.jsp";
				     
					 }
					 else {
						 req.setAttribute("USERVALIDATION", "password invalid");
//							viewName = "login1.jsp";
							req.getRequestDispatcher("login.jsp").forward(req, res);
					 }
				 }
				
				 else {
					 
					 //res.getWriter().println("<h3 style='color:red;margin-top:2%;margin-left:60%; position: absolute; '>Login not Successfully</h3>");
					 req.setAttribute("USERVALIDATION", "user not found");
//						viewName = "login1.jsp";
						req.getRequestDispatcher("login.jsp").forward(req, res);
					 //res.sendRedirect("Login.html");
					 
				 }
				
				
				resultSet.close();
				preparedStatement.close();
				connection.close();
				 
		} 
			 
				catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	 
		
	}


	}


