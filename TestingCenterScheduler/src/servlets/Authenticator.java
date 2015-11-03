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
import application.LoggerWrapper;
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

    // single DatabaseManager object
    private DatabaseManager dbManager;
    
    // single logger wrapper object
    private LoggerWrapper wrapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authenticator() {
	super();
	dbManager = DatabaseManager.getSingleton();
	wrapper = LoggerWrapper.getInstance();
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
		request.getSession(false).invalidate();
	    String userName = request.getParameter("user");
	    String password = request.getParameter("password");
	    wrapper.logger.info("User log in using netID " + userName);
	    UserAccount user = dbManager.getUser(userName, password);
	    if (user != null) {
		request.getSession().setAttribute("user", user);
		String role = user.getRole();
		if (role.equals("admin")) {
		    wrapper.logger.info("Forward to Administrator");
		    RequestDispatcher rd = request.getRequestDispatcher("/Administrator");
		    rd.forward(request,response);
		} else if (role.equals("instr")) {
		    wrapper.logger.info("Forward to Instructor");
		    RequestDispatcher rd = request.getRequestDispatcher("/InstructorHome");
		    rd.forward(request,response);
		}
		if (role.equals("student")) {
		    wrapper.logger.info("Forward to Student");
		    response.sendRedirect("Student.jsp");
		}
	    } else {
		wrapper.logger.info("Invalid login info, return to index page");
		request.setAttribute("returnVal",
			"Invalid username or password");
		RequestDispatcher rd = request
			.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	    }
	} catch (Exception error) {
	    wrapper.logger.warning("Error occurs in Authenticator:\n" + 
		    error.getClass() + ":" + error.getMessage());
	}

    }
}
