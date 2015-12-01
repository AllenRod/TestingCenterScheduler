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
import application.Instructor;
import application.LoggerWrapper;
import entity.Appointment;
import entity.Request;
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

	// single DatabaseManager object
	// private DatabaseManager dbManager;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InstructorServlet() {
		super();
		// dbManager = DatabaseManager.getSingleton();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAccount user = (UserAccount) request.getSession().getAttribute(
				"user");
		// dbManager.createEntityManager();
		if (instr == null) {
			instr = new Instructor(user.getNetID());
		} else {
			instr.setNetID(user.getNetID());
		}
		request.getSession().setAttribute("crequests",
				instr.getClassExamRequests());
		request.getSession().setAttribute("nrequests",
				instr.getNonClassRequests());
		request.getSession().setAttribute("courses", instr.getCourses());
		request.getSession().setAttribute("termlist", instr.getTerms());
		request.getSession().setAttribute("login", true);
		LoggerWrapper.logger.info("Redirect to Instructor homepage");
		// Close entity manager
		// dbManager.closeEntityManager();
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
		// dbManager.createEntityManager();
		if (request.getSession().getAttribute("action").equals("newRequest")) {
			LoggerWrapper.logger.info("Processing New Request");
			String s;
			Request newReq = instr.newRequest(request.getParameter("Rtype"),
					request.getParameter("Rclass"),
					request.getParameter("Rterm"),
					request.getParameter("Rname"),
					request.getParameter("Rduration"),
					request.getParameter("Rsmon"),
					request.getParameter("Rsday"),
					request.getParameter("Rstime"),
					request.getParameter("Remon"),
					request.getParameter("Reday"),
					request.getParameter("Retime"),
					request.getParameter("Rlist"));
			s = instr.checkRequest(newReq);
			if (s.equals("")) {
				request.getSession().setAttribute("utilList",
						instr.viewUtilizationWithRequest(newReq));
				request.getSession().setAttribute("newReq", newReq);
				LoggerWrapper.logger
						.info("Request is schedulable, forward to display utilization");
				RequestDispatcher rd = request
						.getRequestDispatcher("InstructorUtilization.jsp");
				rd.forward(request, response);
				return;
			} else {
				request.getSession().setAttribute("crequests",
						instr.getClassExamRequests());
				request.getSession().setAttribute("nrequests",
						instr.getNonClassRequests());
				request.getSession().setAttribute("courses", instr.getCourses());
				LoggerWrapper.logger.info("Error in making new request, return to request page");
				request.setAttribute("returnVal", s);
				RequestDispatcher rd = request.getRequestDispatcher("Requests.jsp");
				rd.forward(request, response);
				return;
			}
		} else if (request.getSession().getAttribute("action")
				.equals("submitRequest")) {
			Request r = (Request)request.getSession().getAttribute("newReq");
			String s;
			if (r != null) {
				s = instr.submitRequest(r);
				if (s.equals("Data import succeeds")) {
					s = "New Request Success";
				}
			} else {
				s= "Error in submitting request, please retry";
			}
			request.getSession().setAttribute("crequests",
					instr.getClassExamRequests());
			request.getSession().setAttribute("nrequests",
					instr.getNonClassRequests());
			request.getSession().setAttribute("courses", instr.getCourses());
			request.setAttribute("returnVal", s);
			RequestDispatcher rd = request.getRequestDispatcher("Requests.jsp");
			rd.forward(request, response);
		} else if (request.getSession().getAttribute("action")
				.equals("editRequest")) {
			String s = "";
			if (request.getParameter("editAction").equals("Delete")) {
				LoggerWrapper.logger.info("Processing delete Request");
				s = instr.deleteRequest(request.getParameter("RID"));
			} else if (request.getParameter("editAction").equals("Edit")) {
				LoggerWrapper.logger.info("Processing edit Request");
				s = instr.editRequest(request.getParameter("RID"),
						request.getParameter("Ryear"),
						request.getParameter("Rtype"),
						request.getParameter("Rname"),
						request.getParameter("Rduration"),
						request.getParameter("Rsmon"),
						request.getParameter("Rsday"),
						request.getParameter("Rstime"),
						request.getParameter("Remon"),
						request.getParameter("Reday"),
						request.getParameter("Retime"),
						request.getParameter("Rlist"));
			}

			else {
				LoggerWrapper.logger.info("Unsupported Edit Case");
			}
			request.getSession().setAttribute("crequests",
					instr.getClassExamRequests());
			request.getSession().setAttribute("nrequests",
					instr.getNonClassRequests());
			request.getSession().setAttribute("courses", instr.getCourses());
			request.setAttribute("returnVal", s);
			// Close entity manager
			// dbManager.closeEntityManager();
			RequestDispatcher rd = request.getRequestDispatcher("Requests.jsp");
			rd.forward(request, response);
		} else if (request.getSession().getAttribute("action")
				.equals("getExamAppointments")) {
			LoggerWrapper.logger.info("Fetching Appointments");
			List<Appointment> a = instr.getAppointmentsByExamID(request
					.getParameter("EID"));
			int s = instr.getStudentsInCourse(request.getParameter("CID"));
			request.getSession().setAttribute("returnVal", a);
			request.getSession().setAttribute("returnVal2", s);
			// Close entity manager
			// dbManager.closeEntityManager();
			response.sendRedirect(request.getHeader("referer"));
		} else {
			// Close entity manager
			// dbManager.closeEntityManager();
			LoggerWrapper.logger.info("Unsupported Case");
		}
	}
}
