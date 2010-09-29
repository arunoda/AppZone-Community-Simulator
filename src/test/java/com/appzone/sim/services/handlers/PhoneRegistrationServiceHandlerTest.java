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
