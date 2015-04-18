package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestHandler.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.AsyncContext;

import com.google.gson.Gson;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.contracts.IRequestDispatcher;

public class AsynchronousRequestHandler implements Runnable {

	AsyncContext asyncContext;
	@Inject
	IRequestDispatcher iRequestDispatcherObject;

	public AsynchronousRequestHandler(AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
	}

	@Override
	public void run() {

		// Inject the bean

		Map<String, String[]> map = asyncContext.getRequest().getParameterMap();

		// call the beans DispatchRequest method.
		List<Map<String, String>> result = iRequestDispatcherObject
				.dispatchRequest(map);

		asyncContext.

		// For now just print the status of the query (success or failed).
		java.io.Writer w = null;
		try {
			w = asyncContext.getResponse().getWriter();
			Gson gson = new Gson();
			w.append(gson.toJson(result));
			asyncContext.complete();
		} catch (IOException e) {
		} finally {
		}

	}

}
