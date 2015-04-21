package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestHandler.services;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.openejb.util.Base64;

import com.google.gson.Gson;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.authenticationManager.contracts.IAuthenticationManager;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.contracts.IRequestDispatcher;

/**
 * Servlet implementation class RequestHandler. This class is the entry point
 * for our server side logic. It is repsonsible for receiving HTTP requests from
 * the client and forwarding them to the RequestDispatcher for processing. It
 * also is responsible for sending an HTTP response back to the client.
 * 
 * @author tejasvamsingh
 */

@WebServlet(urlPatterns = "/RequestHandler", asyncSupported = true)
public class RequestHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	IRequestDispatcher iRequestDispatcherObject;

	@Inject
	IAuthenticationManager authenticationManager;

	List<Map<String, String>> result;

	ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// ********* LOGGING *********
		java.io.Writer w = response.getWriter();
		w.append("<html>");
		w.append("<body>");
		w.append("<h1>This is something cool.</h1>");
		w.append("</body>");
		w.append("</html>");
		// ********* LOGGING *********

	}

	/**
	 * 
	 * This method accepts post requests, calls the request dispatcher on the
	 * request, obtains response and sends the response back to the client.
	 * 
	 * @author tejasvamsingh
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Reached doPost in RequestHandler");
		AsyncContext startAsync = request.startAsync();

		System.out.println("HEADER : " + request.getHeader("Authorization"));

		String requestID = request.getParameterMap().get("requestID")[0];
		String requestType = request.getParameterMap().get("requestType")[0];

		System.out.println("Request is : " + requestID + " " + requestType);

		startAsync.addListener(new AsyncListener() {

			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStartAsync(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(AsyncEvent arg0) throws IOException {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				Writer w = response.getWriter();
				Gson gson = new Gson();
				w.append(gson.toJson(result));

			}
		});

		// executor.execute(new Runnable() {
		startAsync.start(new Runnable() {

			@Override
			public void run() {
				result = new ArrayList<Map<String, String>>();
				Map<String, String[]> map = startAsync.getRequest()
						.getParameterMap();

				// set default failure.
				result.add(new HashMap<String, String>());
				result.get(0).put("Status", "Failed");
				result.get(0).put("Reason",
						"Unauthorized request. Please log in again.");

				if (request.getHeader("Authorization") == null)
					startAsync.complete();

				// authenticate using token
				Base64 decoder = new Base64();
				String authorization = request.getHeader("Authorization");
				String base64Credentials = authorization.substring(
						"Basic".length()).trim();
				byte[] userPassBytesArray = Base64
						.decodeBase64(base64Credentials.getBytes());
				String userPassString = StringUtils
						.newStringUtf8(userPassBytesArray);
				System.out.println("User credentials : " + userPassString);

				// we also want to let a registering user through..
				String requestID = map.get("requestID")[0];
				String requestType = map.get("requestType")[0];

				if (authenticationManager.isUserAuthenticated(userPassString)
						|| (requestID.equals("Account") && requestType
								.equals("create"))) {
					System.out.println("user is authenticated.");
					result = iRequestDispatcherObject.dispatchRequest(map);
				}

				startAsync.complete();
			}
		});

	}
}
