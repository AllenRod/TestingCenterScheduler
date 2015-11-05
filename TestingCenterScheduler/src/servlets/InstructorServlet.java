package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.Administrator;
import application.Instructor;
import application.LoggerWrapper;
import entity.UserAccount;

/**
 * Servlet implementation class InstructorServlet Handle Instructor request and
 * response
 * 
 * @author CSE308 Team Five
 */
@WebServlet("/InstructorHome")
public class InstructorServlet extends HttpServlet {

    // single Administrator object
    private Instructor instr;

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InstructorServlet() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	UserAccount user = (UserAccount) request.getSession().getAttribute(
		"user");
	if (instr == null) {
	    instr = new Instructor(user.getNetID());
	} else {
	    instr.setNetID(user.getNetID());
	}
	request.getSession().setAttribute("requests", instr.getRequests());
	request.getSession().setAttribute("login", true);
	LoggerWrapper.logger.info("Redirect to Instructor homepage");
	response.sendRedirect("Instructor.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	if (request.getSession().getAttribute("login") == null) {
	    doGet(request, response);
	    return;
	} 
	if (request.getSession().getAttribute("action") != null) {
	    LoggerWrapper.logger.info("Processing New Request");
	    instr.newRequest(request.getParameter("Rtype"),
		    request.getParameter("Rclass"),
		    request.getParameter("Rname"),
		    request.getParameter("Rduration"),
		    request.getParameter("Rsmon"),
		    request.getParameter("Rsday"),
		    request.getParameter("Rstime"),
		    request.getParameter("Remon"),
		    request.getParameter("Reday"),
		    request.getParameter("Retime"),
		    request.getParameter("Rlist"));
	    response.sendRedirect("Close.jsp");
	    request.getSession().setAttribute("requests", instr.getRequests());
	    response.sendRedirect("Requests.jsp");
	} else {
	    LoggerWrapper.logger.info("Unsupported Case");
	}
    }

}
