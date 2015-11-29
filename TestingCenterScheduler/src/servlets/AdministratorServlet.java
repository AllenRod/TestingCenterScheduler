package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.Administrator;
import application.DatabaseManager;
import application.LoggerWrapper;
import application.Report;
import entity.UserAccount;

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
	
	// single DatabaseManager object
	private DatabaseManager dbManager;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdministratorServlet() {
		super();
		dbManager = DatabaseManager.getSingleton();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserAccount user = (UserAccount) request.getSession().getAttribute(
				"user");
		dbManager.createEntityManager();
		if (admin == null) {
			admin = new Administrator(user.getNetID());
		} else {
			admin.setNetID(user.getNetID());
		}
		request.getSession().setAttribute("infolist", admin.getTCInfo());
		request.getSession().setAttribute("termlist", admin.getTerms());
		request.getSession().setAttribute("login", true);
		LoggerWrapper.logger.info("Redirectiong to Admin.jsp");

		request.getSession().setAttribute("crequests",
				admin.getRequests("CLASS"));
		request.getSession().setAttribute("nrequests",
				admin.getRequests("AD HOC"));

		request.getSession().setAttribute("appointments",
				admin.getAllAppointments());

		// Close entity manager
		dbManager.closeEntityManager();
		response.sendRedirect("Admin.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		dbManager.createEntityManager();
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
				// Close entity manager
				dbManager.closeEntityManager();
				response.sendRedirect("CenterHours.jsp");
			}
			if (request.getParameter("utilization") != null) {
				LoggerWrapper.logger.info("Admin " + admin.getNetID()
						+ "viewing utilization");
				String term = request.getParameter("termVal");
				String startMonth = request.getParameter("startmon");
				String startDay = request.getParameter("startday");
				String endMonth = request.getParameter("endmon");
				String endDay = request.getParameter("endday");
				LoggerWrapper.logger.info(term + " : " + startMonth + "/"
						+ startDay + "-" + endMonth + "/" + endDay);
				List<String> result = admin.viewUtilization(term, startMonth,
						startDay, endMonth, endDay);
				if (result.size() == 0) {
					request.getSession().setAttribute("returnVal",
							"Start date is after end date!");
				} else {
					request.getSession().setAttribute("returnVal", result);
				}
				// Close entity manager
				dbManager.closeEntityManager();
				response.sendRedirect("AdminUtilization.jsp");
			}
			if (request.getParameter("report") != null) {
				LoggerWrapper.logger.info("Admin " + admin.getNetID()
						+ " generating reports");
				String startTerm = request.getParameter("termVal1");
				String endTerm = request.getParameter("termVal2");
				List<Report> reports = admin.generateReport_All(startTerm,
						endTerm);
				request.getSession().setAttribute("returnVal", reports);
				// Close entity manager
				dbManager.closeEntityManager();
				response.sendRedirect("Report.jsp");
			}
			if (request.getParameterValues("checkin") != null) {
				LoggerWrapper.logger.info("Admin " + admin.getNetID()
						+ " checkin students");
				String[] students = request.getParameterValues("checkin");
				boolean noError = true;
				for (String s : students) {
					String[] idList = s.split(":");
					boolean b = admin.checkInStudent(idList[0],
							Integer.parseInt(idList[1]), idList[2]);
					if (!b) {
						noError = b;
					}
				}
				request.getSession().setAttribute("returnVal",
						Boolean.valueOf(noError));
				// Close entity manager
				dbManager.closeEntityManager();
				response.sendRedirect("AdminAppointments.jsp");
			}
			if (request.getParameter("request_approve") != null) {
				LoggerWrapper.logger.info("Admin " + admin.getNetID()
						+ " approving a request");
				boolean approved = admin.approveRequest(request
						.getParameter("request_approve"));
				request.getSession().setAttribute("returnVal1",
						"Request Approval: ");
				request.getSession().setAttribute("returnVal2",
						Boolean.valueOf(approved));
				// Close entity manager
				dbManager.closeEntityManager();
				response.sendRedirect("AdminRequests.jsp");
			}
			if (request.getParameter("request_deny") != null) {
				LoggerWrapper.logger.info("Admin " + admin.getNetID()
						+ " denying a request");
				boolean denied = admin.denyRequest(request
						.getParameter("request_deny"));
				request.getSession().setAttribute("returnVal1",
						"Request Denial: ");
				request.getSession().setAttribute("returnVal2",
						Boolean.valueOf(denied));
				// Close entity manager
				dbManager.closeEntityManager();
				response.sendRedirect("AdminRequests.jsp");
			}
		} catch (Exception error) {
			LoggerWrapper.logger
					.warning("Error occurs in AdministratorServlet:\n"
							+ error.getClass() + ":" + error.getMessage());
		}
	}
}
