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

import com.appzone.sim.model.Sms;
import com.appzone.sim.repositories.SmsRepository;
import com.appzone.sim.repositories.impl.MemoryMtMessageRepository;
import com.appzone.sim.repositories.impl.MemoryPhoneRepository;
import com.appzone.sim.repositories.impl.MemorySmsRepository;
import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class ReceiveSmsCheckServiceHandlerTest extends TestCase {

	private Mockery context;

	public void setUp() {
		context = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		new MemoryMtMessageRepository().removeAll();
		new MemorySmsRepository().removeAll();
		new MemoryPhoneRepository().removeAll();
	}

	public void testServeNormal() {

		final HttpServletRequest request = context.mock(HttpServletRequest.class);

		SmsRepository repository = new MemorySmsRepository();
		ServiceHandler handler = new ReceiveSmsCheckServiceHandler(repository);

		// setting some dummy messages
		repository.add(new Sms("message", "001", 10));
		repository.add(new Sms("message", "001", 12));
		repository.add(new Sms("message", "001", 13));
		repository.add(new Sms("message", "002", 10));
		repository.add(new Sms("message", "002", 10));

		context.checking(new Expectations() {
			{
				allowing(request).getParameter(DefaultKewordMatcher.SERVICE_KEYWORD);
				will(returnValue(ReceiveSmsCheckServiceHandler.MATCHING_KEYWORD));

				allowing(request).getParameter(ReceiveSmsCheckServiceHandler.KEY_ADDRESS);
				will(returnValue("001"));

				allowing(request).getParameter(ReceiveSmsCheckServiceHandler.KEY_SINCE);
				will(returnValue("11"));
			}
		});

		String jsonStr = handler.serve(request);
		JSONArray result = (JSONArray) JSONValue.parse(jsonStr);

		assertEquals(2, result.size());
	}
}
