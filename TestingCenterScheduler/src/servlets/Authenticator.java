package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.DatabaseManager;
import entity.Course;
import entity.UserAccount;

/**
 * Servlet implementation class Authenticator Handle client login and
 * authentication request and response
 * 
 * @author CSE308 Team Five
 */
@WebServlet("/Login")
public class Authenticator extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DatabaseManager dbManager;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authenticator() {
	super();
	dbManager = DatabaseManager.getSingleton();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	if (request.getSession(false) != null)
	    request.getSession(false).invalidate();
	response.sendRedirect("index.jsp");
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
	    UserAccount user = dbManager.getUser(userName, password);
	    if (user != null) {
		request.getSession().setAttribute("user", user);
		String role = user.getRole();
		if (role.equals("admin")) {
		    response.sendRedirect("Admin.jsp");
		} else if (role.equals("instr")) {
		    List<Course> courses = dbManager.I_getCourses(userName);
		    request.getSession().setAttribute("courses", courses);
		    response.sendRedirect("Instructor.jsp");
		}
		if (role.equals("student")) {
		    response.sendRedirect("Student.jsp");
		}
	    } else {
		request.setAttribute("returnVal",
			"Invalid username or password");
		RequestDispatcher rd = request
			.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	    }
	} catch (Exception error) {
	    System.out.println(error.getMessage());
	}

    }
}
