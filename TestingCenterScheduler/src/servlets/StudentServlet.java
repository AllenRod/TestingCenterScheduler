package servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.LoggerWrapper;
import entity.UserAccount;

/**
 * Servlet implementation class StudentServlet
 * Handle Student request and response
 * @author CSE308 Team Five
 */
@WebServlet("/StudentHome")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserAccount user = (UserAccount) request.getSession().getAttribute(
				"user");
		// Test for timeslot handler
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.set(2015, Calendar.NOVEMBER, 10);
		application.TimeSlotHandler handler = new application.TimeSlotHandler(c.getTime());
		request.getSession().setAttribute("timeslot", handler.getTimeSlot());
		LoggerWrapper.logger.info("Redirectiong to Student.jsp");
		response.sendRedirect("Student.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("login") == null) {
			doGet(request, response);
			return;
		}
	}

}
