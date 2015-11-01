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

/**
 * Servlet implementation class AdministratorServlet Handle Administrator
 * request and response
 * 
 * @author CSE308 Team Five
 */
@WebServlet("/Administrator")
public class AdministratorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Administrator admin;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdministratorServlet() {
	super();
	// TODO Auto-generated constructor stub
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
	// TODO Auto-generated method stub
	System.out.println("here");
	UserAccount user = (UserAccount) request.getSession().getAttribute(
		"user");
	if (admin == null) {
	    admin = new Administrator(user.getNetID());
	} else {
	    admin.setNetID(user.getNetID());
	}
	try {
	    if (request.getParameter("edit") != null) {
		System.out.println(request.getParameter("edit"));
		String term = request.getParameter("term");
		String openHours = request.getParameter("mon") + ";"
			+ request.getParameter("tue") + ";"
			+ request.getParameter("wed") + ";"
			+ request.getParameter("thu") + ";"
			+ request.getParameter("fri") + ";"
			+ request.getParameter("sat") + ";"
			+ request.getParameter("sun");
		int seats = Integer.parseInt(request.getParameter("seat"));
		int setAsideSeats = Integer.parseInt(request
			.getParameter("setseat"));
		String reserveTime = request.getParameter("reserve");
		int gapTime = Integer.parseInt(request.getParameter("gaptime"));
		int reminderInterval = Integer.parseInt(request
			.getParameter("reminder"));
		admin.editTestCenterInfo(term, openHours, seats, setAsideSeats,
			reserveTime, gapTime, reminderInterval);
	    }
	} catch (Exception error) {
	    System.out.println(error.getMessage());
	}
    }
}
