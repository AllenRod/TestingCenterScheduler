package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.LoggerWrapper;
import application.Student;
import entity.UserAccount;

/**
 * Servlet implementation class StudentServlet Handle Student request and
 * response
 * 
 * @author CSE308 Team Five
 */
@WebServlet("/StudentHome")
public class StudentServlet extends HttpServlet {

	// single Student object
	private Student stu;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentServlet() {
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
		if (stu == null) {
			stu = new Student(user.getNetID());
		} else {
			stu.setNetID(user.getNetID());
		}
		if (request.getSession().getAttribute("action") != null) {
			if (request.getSession().getAttribute("action")
					.equals("getRequests")) {
				request.getSession()
						.setAttribute("requests", stu.getRequests());
				// response.sendRedirect("NewAppointment.jsp");
				response.sendRedirect("StudentRequest.jsp");
				LoggerWrapper.logger.info("Redirect to Student Request page");
				return;
			}
		}
		request.getSession()
				.setAttribute("appointments", stu.getAppointments());
		request.getSession().setAttribute("login", true);
		LoggerWrapper.logger.info("Redirect to Student homepage");
		response.sendRedirect("Student.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int reqID = 0;
		if (request.getSession().getAttribute("login") == null) {
			doGet(request, response);
			return;
		}
		if (request.getSession().getAttribute("action")
				.equals("newAppointment")) {
			LoggerWrapper.logger.info("Processing New Appointment");
			String s = stu.newAppointment(request.getParameter("AreqID"),
					request.getParameter("Atime"));
			request.getSession().setAttribute("appointments",
					stu.getAppointments());
			request.setAttribute("returnVal", s);
			RequestDispatcher rd = request.getRequestDispatcher("Student.jsp");
			rd.forward(request, response);
		} else if (request.getSession().getAttribute("action")
				.equals("createAppointment")) {
			LoggerWrapper.logger.info("Creating new appointment for student");
			reqID = Integer.parseInt(request.getParameter("Areq"));
			request.getSession().setAttribute("AreqID", reqID);
			request.getSession().setAttribute("timeSlot",
					stu.generateTimeSlot(reqID));
			response.sendRedirect("NewAppointment.jsp");
		} else if (request.getParameter("app_cancel") != null) {
			LoggerWrapper.logger.info("Cancelling appointment for student");
			System.out.println(request.getParameter("app_cancel"));
			String[] valList = request.getParameter("app_cancel").split(":");
			boolean cancelled = stu.cancelAppointment(
					Integer.parseInt(valList[0]), valList[1], valList[2]);
			request.getSession().setAttribute("returnVal2", cancelled);
			request.getSession().setAttribute("appointments",
					stu.getAppointments());
			response.sendRedirect("Student.jsp");
		} else {
			LoggerWrapper.logger.info("Unsupported Case");
		}
	}
}
