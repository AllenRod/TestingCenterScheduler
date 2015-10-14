package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.UserAccount;
import application.DatabaseManager;

/**
 * Servlet implementation class Authenticator
 */
@WebServlet("/Login")
public class Authenticator extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DatabaseManager dataMgn;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authenticator() {
	super();
	dataMgn = new DatabaseManager();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	try {
	    String userName = request.getParameter("user");
	    String password = request.getParameter("password");
	    UserAccount user = dataMgn.getUser(userName, password);
	    if (user != null) {
		request.getSession().setAttribute("user", user);
		String role = user.getRoles();
		if (role.equals("admin")) {
		    response.sendRedirect("Admin.jsp");
		} else if (role.equals("instr")) {
		    response.sendRedirect("Instructor.jsp");
		} if (role.equals("student")) {
		    response.sendRedirect("Student.jsp");
		}
	    } else {
		RequestDispatcher rd = request.getRequestDispatcher("index.html");
		rd.forward(request, response);
	    }
	} catch (Throwable Exception) {
	    System.out.println(Exception);
	}

    }
}
