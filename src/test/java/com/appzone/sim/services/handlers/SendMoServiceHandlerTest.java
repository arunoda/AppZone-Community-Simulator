package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Application;
import com.appzone.sim.repositories.impl.MemoryMtMessageRepository;
import com.appzone.sim.repositories.impl.MemoryPhoneRepository;
import com.appzone.sim.repositories.impl.MemorySmsRepository;
import com.appzone.sim.util.EasyHttp;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class SendMoServiceHandlerTest extends TestCase {

	private Mockery context;

	public void setUp() {
		context = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		Application.configure(null, null, null);
		new MemoryMtMessageRepository().removeAll();
		new MemorySmsRepository().removeAll();
		new MemoryPhoneRepository().removeAll();
	}

	public void testServeNormal() throws IOException {

		final HttpServletRequest request = context.mock(HttpServletRequest.class);
		final EasyHttp http = context.mock(EasyHttp.class);

		SendMoServiceHandler handler = new SendMoServiceHandler(Application.getApplication(), http);

		context.checking(new Expectations() {
			{
				allowing(request).getParameter(DefaultKewordMatcher.SERVICE_KEYWORD);
				will(returnValue(SendMoServiceHandler.MATCHING_KEYWORD));

				allowing(request).getParameter(SendMoServiceHandler.KEY_ADDRESS);
				will(returnValue("0714000000"));

				allowing(request).getParameter(SendMoServiceHandler.KEY_MESSAGE);
				will(returnValue("mpoll sss 1"));

				allowing(http);
			}
		});

		JSONObject json = new JSONObject();
		json.put(ServiceHandler.JSON_KEY_RESULT, "");

		assertEquals(json.toJSONString(), handler.serve(request));
	}

	/*
	 * public void testReal() {
	 * 
	 * final HttpServletRequest request =
	 * context.mock(HttpServletRequest.class);
	 * 
	 * context.checking(new Expectations(){{
	 * allowing(request).getParameter(DefaultKewordMatcher.SERVICE_KEYWORD);
	 * will(returnValue(SendMoServiceHandler.MATCHING_KEYWORD));
	 * 
	 * allowing(request).getParameter(SendMoServiceHandler.KEY_ADDRESS);
	 * will(returnValue("0714000000"));
	 * 
	 * allowing(request).getParameter(SendMoServiceHandler.KEY_MESSAGE);
	 * will(returnValue("mpoll sss 1"));
	 * 
	 * }});
	 * 
	 * Application.configure("http://localhost:4455/molistener", "", "");
	 * SendMoServiceHandler handler = new
	 * SendMoServiceHandler(Application.getApplication(), new EasyHttp());
	 * handler.doProcess(request); }
	 */
}
