package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.DatabaseManager;

/**
 * Servlet implementation class Authenticator
 */
@WebServlet("/Login")
public class Authenticator extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private DatabaseManager dataMgn;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Authenticator() {
	super();
	dataMgn = new DatabaseManager();
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
	try {
	    String userName = request.getParameter("user");
	    String password = request.getParameter("password");
	    String role = dataMgn.getRole(userName, password);
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    if (role.equals("")) {
		out.println("Error Page PLZ");
	    } else {
		out.println(role);
	    }
	    out.close();

	} catch (Throwable Exception) {
	    System.out.println(Exception);
	}

    }
}
