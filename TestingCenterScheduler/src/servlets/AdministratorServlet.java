package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Course;
import entity.User;
import entity.UserAccount;
import application.Administrator;
import application.DatabaseManager;
import application.Instructor;
import application.LoggerWrapper;

/**
 * Servlet implementation class AdministratorServlet Handle Administrator
 * request and response
 * 
 * @author CSE308 Team Five
 */
@WebServlet("/AdministratorHome")
public class AdministratorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // single Administrator object
    private Administrator admin;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministratorServlet() {
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
	if (admin == null) {
	    admin = new Administrator(user.getNetID());
	} else {
	    admin.setNetID(user.getNetID());
	}
	request.getSession().setAttribute("infolist", admin.getTCInfo());
	request.getSession().setAttribute("termlist", admin.getTerms());
	request.getSession().setAttribute("login", true);
	LoggerWrapper.logger.info("Redirectiong to Admin.jsp");
	response.sendRedirect("Admin.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	try {
	    if (request.getSession().getAttribute("login") == null) {
		doGet(request, response);
		return;
	    }
	    if (request.getParameter("edit") != null) {
		LoggerWrapper.logger.info("Admin " + admin.getNetID()
			+ " editing testing center info");
		String term = request.getParameter("term");
		String openHours = request.getParameter("mono") + "-"
			+ request.getParameter("monc") + ";"
			+ request.getParameter("tueo") + "-"
			+ request.getParameter("tuec") + ";"
			+ request.getParameter("wedo") + "-"
			+ request.getParameter("wedc") + ";"
			+ request.getParameter("thuo") + "-"
			+ request.getParameter("thuc") + ";"
			+ request.getParameter("frio") + "-"
			+ request.getParameter("frio") + ";"
			+ request.getParameter("sato") + "-"
			+ request.getParameter("satc") + ";"
			+ request.getParameter("suno") + "-"
			+ request.getParameter("sunc");
		int seats = Integer.parseInt(request.getParameter("seat"));
		int setAsideSeats = Integer.parseInt(request
			.getParameter("setseat"));
		String closedDate = request.getParameter("closing");
		String reserveTime = request.getParameter("reserve");
		int gapTime = Integer.parseInt(request.getParameter("gaptime"));
		int reminderInterval = Integer.parseInt(request
			.getParameter("reminder"));
		String s = admin.editTestCenterInfo(term, openHours, seats,
			setAsideSeats, closedDate, reserveTime, gapTime,
			reminderInterval);
		request.getSession()
			.setAttribute("infolist", admin.getTCInfo());
		response.sendRedirect("CenterHours.jsp");
	    }
	} catch (Exception error) {
	    LoggerWrapper.logger
		    .warning("Error occurs in AdministratorServlet:\n"
			    + error.getClass() + ":" + error.getMessage());
	}
    }
}
