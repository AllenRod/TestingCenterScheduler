package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.CSVLoader;

/**
 * Servlet implementation class LoaderServlet Handle file loading functionality
 * Handle file loading request and response
 * @author CSE308 Team Five
 */
@WebServlet("/LoadCSV")
public class LoaderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CSVLoader loader;

    public LoaderServlet() {
	super();
	loader = new CSVLoader();
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
	    String fileName = request.getParameter("fileName");
	    String table = request.getParameter("table");
	    // dataMgn.loadCSV(fileName, table);
	    loader.loadCSV(fileName, table);
	} catch (Throwable Exception) {
	    System.out.println(Exception);
	}

    }

}
