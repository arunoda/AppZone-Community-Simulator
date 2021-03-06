/*
	Copyright 2010 Arunoda Susiripala

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package com.appzone.sim.services.handlers;

import com.appzone.sim.model.Application;
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
public class AppRegistrationServiceHandlerTest extends TestCase {

	private Mockery context;

	public void setUp() {
		context = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		Application.configure("", "", "");
		new MemoryMtMessageRepository().removeAll();
		new MemorySmsRepository().removeAll();
		new MemoryPhoneRepository().removeAll();
	}

	public void testServeConfigureApplication() {

		final HttpServletRequest request = context.mock(HttpServletRequest.class);

		AppRegistrationServiceHandler handler = new AppRegistrationServiceHandler();

		context.checking(new Expectations() {
			{
				allowing(request).getParameter(DefaultKewordMatcher.SERVICE_KEYWORD);
				will(returnValue(AppRegistrationServiceHandler.MATCHING_KEYWORD));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_INFO_REQUEST);
				will(returnValue(null));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_URL);
				will(returnValue("the url"));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_USERNAME);
				will(returnValue("the username"));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_PASSWORD);
				will(returnValue("the password"));
			}
		});

		Application.configure("http://url", "username", "password");

		Application app = Application.getApplication();
		JSONObject json = new JSONObject();
		json.put(AppRegistrationServiceHandler.JSON_KEY_RESULT, true);

		assertEquals(json.toJSONString(), handler.serve(request));
	}

	public void testServeConfigureInfoRequest() {

		final HttpServletRequest request = context.mock(HttpServletRequest.class);

		AppRegistrationServiceHandler handler = new AppRegistrationServiceHandler();

		context.checking(new Expectations() {
			{
				allowing(request).getParameter(DefaultKewordMatcher.SERVICE_KEYWORD);
				will(returnValue(AppRegistrationServiceHandler.MATCHING_KEYWORD));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_INFO_REQUEST);
				will(returnValue("true"));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_URL);
				will(returnValue("the url"));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_USERNAME);
				will(returnValue("the username"));

				allowing(request).getParameter(AppRegistrationServiceHandler.KEY_PASSWORD);
				will(returnValue("the password"));
			}
		});

		JSONObject json = new JSONObject();
		Application.configure("http://url", "username", "password");

		json.put(AppRegistrationServiceHandler.JSON_KEY_URL, "http://url");
		json.put(AppRegistrationServiceHandler.JSON_KEY_USERNAME, "username");
		json.put(AppRegistrationServiceHandler.JSON_KEY_PASSWORD, "password");

		assertEquals(json.toJSONString(), handler.serve(request));
	}
}
