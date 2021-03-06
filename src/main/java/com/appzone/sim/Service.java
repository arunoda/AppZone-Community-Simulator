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
package com.appzone.sim;

import com.appzone.sim.model.Application;
import com.appzone.sim.repositories.MtMessageRepository;
import com.appzone.sim.repositories.PhoneRepository;
import com.appzone.sim.repositories.SmsRepository;
import com.appzone.sim.repositories.impl.MemoryMtMessageRepository;
import com.appzone.sim.repositories.impl.MemoryPhoneRepository;
import com.appzone.sim.repositories.impl.MemorySmsRepository;
import com.appzone.sim.services.handlers.*;
import com.appzone.sim.util.EasyHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class Service extends HttpServlet {

	private final static Logger logger = LoggerFactory.getLogger(Service.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		logger.debug("Starting service with : {}", req.getQueryString());

		ServiceHandler serviceHandler = buildServiceHandlerChain();
		String response = serviceHandler.serve(req);

		logger.debug("Printing response: {}", response);
		resp.getWriter().print(response);
	}

	private ServiceHandler buildServiceHandlerChain() {

		logger.debug("creating ServiceHandler chain");

		SmsRepository smsRepository = new MemorySmsRepository();
		MtMessageRepository mtMessageRepository = new MemoryMtMessageRepository();
		PhoneRepository phoneRepository = new MemoryPhoneRepository();
		Application application = Application.getApplication();
		EasyHttp http = new EasyHttp();

		ServiceHandler appRegistrationServiceHandler = new AppRegistrationServiceHandler();
		ServiceHandler phoneRegistrationServiceHandler = new PhoneRegistrationServiceHandler(phoneRepository);
		ServiceHandler receiveSmsCheckServiceHandler = new ReceiveSmsCheckServiceHandler(smsRepository);
		ServiceHandler mtLogCheckServiceHandler = new MtLogCheckServiceHandler(mtMessageRepository);
		ServiceHandler sendMoServiceHandler = new SendMoServiceHandler(application, http);

		ServiceHandler rootServiceHandler = mtLogCheckServiceHandler;

		rootServiceHandler.setNextServiceHandler(receiveSmsCheckServiceHandler);
		receiveSmsCheckServiceHandler.setNextServiceHandler(sendMoServiceHandler);
		sendMoServiceHandler.setNextServiceHandler(phoneRegistrationServiceHandler);
		phoneRegistrationServiceHandler.setNextServiceHandler(appRegistrationServiceHandler);

		return rootServiceHandler;

	}
}
