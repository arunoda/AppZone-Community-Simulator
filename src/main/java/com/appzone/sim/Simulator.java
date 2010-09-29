package com.appzone.sim;

import com.appzone.sim.model.Application;
import com.appzone.sim.model.MtMessage;
import com.appzone.sim.repositories.MtMessageRepository;
import com.appzone.sim.repositories.PhoneRepository;
import com.appzone.sim.repositories.SmsRepository;
import com.appzone.sim.repositories.impl.MemoryMtMessageRepository;
import com.appzone.sim.repositories.impl.MemoryPhoneRepository;
import com.appzone.sim.repositories.impl.MemorySmsRepository;
import com.appzone.sim.services.AuthenticationService;
import com.appzone.sim.services.BasicAuthAuthenticationService;
import com.appzone.sim.services.MtMessageProcessor;
import com.appzone.sim.services.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class Simulator extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(Simulator.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Creating Response Generation Object
		ResponseGenerator responseGenerator = new ResponseGenerator();

		// authentication
		AuthenticationService authenticationService = new BasicAuthAuthenticationService(Application.getApplication());
		if (!authenticationService.authenticate(req)) {
			// authentication failed
			String response = responseGenerator.generateResponseWhenLoginFailed();
			resp.getWriter().print(response);
			return;
		}

		// generating mtMessage
		List<String> addresses = Arrays.asList(req.getParameterValues("address"));
		String message = req.getParameter("message");
		MtMessage mtMessage = new MtMessage(message, addresses, new Date().getTime());

		logger.info("message received {}", mtMessage);

		// creating dependencies
		MtMessageRepository mtMessageRepository = new MemoryMtMessageRepository();
		SmsRepository smsRepository = new MemorySmsRepository();
		PhoneRepository phoneRepository = new MemoryPhoneRepository();

		// creating mtMessage Processor
		MtMessageProcessor mtMessageProcessor = new MtMessageProcessor(mtMessageRepository, smsRepository,
				phoneRepository);

		// do the processing
		boolean processed = mtMessageProcessor.process(mtMessage);

		// sending the request
		String response = responseGenerator.generateResponseAfterMessageProcessed(processed);
		logger.info("message processed with the response: {}", response);
		resp.getWriter().print(response);

	}

}
