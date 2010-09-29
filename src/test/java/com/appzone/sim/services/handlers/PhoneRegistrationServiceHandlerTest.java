package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Application;
import com.appzone.sim.repositories.PhoneRepository;
import com.appzone.sim.repositories.impl.MemoryMtMessageRepository;
import com.appzone.sim.repositories.impl.MemoryPhoneRepository;
import com.appzone.sim.repositories.impl.MemorySmsRepository;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class PhoneRegistrationServiceHandlerTest extends TestCase {

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

	public void testServeNormal() {

		final HttpServletRequest request = context.mock(HttpServletRequest.class);

		PhoneRepository repository = new MemoryPhoneRepository();
		PhoneRegistrationServiceHandler handler = new PhoneRegistrationServiceHandler(repository);

		context.checking(new Expectations() {
			{
				allowing(request).getParameter(DefaultKewordMatcher.SERVICE_KEYWORD);
				will(returnValue(PhoneRegistrationServiceHandler.MATCHING_KEYWORD));

				allowing(request).getParameter(PhoneRegistrationServiceHandler.KEY_PHONE_NO);
				will(returnValue("00012"));

			}
		});

		assertTrue(handler.serve(request).contains(PhoneRegistrationServiceHandler.JSON_KEY_MD5_PHONE_NO));
	}
}
