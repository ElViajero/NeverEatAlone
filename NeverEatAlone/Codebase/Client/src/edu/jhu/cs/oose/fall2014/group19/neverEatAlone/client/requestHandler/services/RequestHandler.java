package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers.MessageToasterHelper;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestHandler.contracts.IRequestHandler;

/**
 * This class is the handler class for all client requests. For every request,
 * the handleRequest method of this class is called.
 * 
 * @author tejasvamsingh
 *
 */

public class RequestHandler implements IRequestHandler {

	/**
	 * This method handles requests that the client wishes to send to the
	 * server. It returns the response to the appropriate event handler. It
	 * executes the requests by calling the execute method on RequestExecutor.
	 * 
	 * @author tejasvamsingh
	 * @throws RequestAbortedException
	 */

	// This one SuppressWarning is required.
	// It's to do with varargs which is what
	// the AsnycTask execute method accepts.

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> handleRequest(Activity activity,
			Map<String, Object> requestMap, String requestID, String requestType)
			throws RequestAbortedException {

		// Get the name value pair list.
		List<NameValuePair> requestList = getRequestList(requestMap);

		// Add request headers.
		requestList.add(new BasicNameValuePair("requestID", requestID));
		requestList.add(new BasicNameValuePair("requestType", requestType));

		// call the asynchronous result executor.
		List<Map<String, String>> resultMapList = null;
		try {

			RequestExecutor requestExecutor = new RequestExecutor();
			resultMapList = requestExecutor.execute(requestList).get();

			if (resultMapList.get(0).get("Status").equals("Failed")) {
				String reason = "Failed";
				if (resultMapList.get(0).containsKey("Reason"))
					reason = resultMapList.get(0).get("Reason");

				MessageToasterHelper.toastMessage(activity, reason);
				throw new RequestAbortedException("Exception already handled.");
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// This is a status that must be added to show that
			// the server is unreachable.
			System.out
					.println("Null in RequestHandler. Couldn't reach the server.");
			MessageToasterHelper.toastMessage(activity,
					"Could not connect to the server.");
			throw new RequestAbortedException("Exception already handled.");
		}

		resultMapList.remove(0);
		return resultMapList;
	}

	@Override
	public void cleanUp() {
		RequestExecutor.cleanUp();
	}

	/**
	 * This method formats the request correctly before sending it to the
	 * RequestExecutor
	 * 
	 * @return
	 */

	private List<NameValuePair> getRequestList(Map<String, Object> requestMap) {

		List<NameValuePair> requestList = new ArrayList<NameValuePair>();

		for (Map.Entry<String, Object> entry : requestMap.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof String) {
				requestList.add(new BasicNameValuePair(key, (String) value));
				continue;
			}

			if (value instanceof List<?>) {
				List<?> valueList = (List<?>) value;
				for (Object valueObject : valueList) {
					requestList.add(new BasicNameValuePair(key,
							(String) valueObject));
				}

				continue;
			}

		}

		return requestList;

	}

}
