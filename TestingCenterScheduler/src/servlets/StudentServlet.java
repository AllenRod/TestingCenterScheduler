package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.Student;
import application.LoggerWrapper;
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
		request.getSession().setAttribute("appointments", stu.getAppointments());
		//request.getSession().setAttribute("exams", stu.getExams()); FIXXXXXXXXXX
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
		if (request.getSession().getAttribute("login") == null) {
			doGet(request, response);
			return;
		}
		if (request.getSession().getAttribute("action").equals("newAppointment")) {
			LoggerWrapper.logger.info("Processing New Appointment");
			String s;
			s = stu.newAppointment(request.getParameter("Aclass"),
					request.getParameter("Asmon"),
					request.getParameter("Asday"),
					request.getParameter("Astime"));
			if (s.equals("Data import succeeds"))
				s = "New Request Success";
			request.getSession().setAttribute("appointments", stu.getAppointments());
			request.getSession().setAttribute("exams", stu.getExams());
			request.setAttribute("returnVal", s);
			RequestDispatcher rd = request.getRequestDispatcher("Student.jsp");
			rd.forward(request, response);
			// response.sendRedirect("Requests.jsp");
		} else {
			LoggerWrapper.logger.info("Unsupported Case");
		}
	}

}
