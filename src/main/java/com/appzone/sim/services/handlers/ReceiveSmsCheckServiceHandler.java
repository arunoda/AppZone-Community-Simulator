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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class ReceiveSmsCheckServiceHandler extends AbstractServiceHandler {

	public final static String MATCHING_KEYWORD = "sms", KEY_SINCE = "since", KEY_ADDRESS = "address",
			JSON_KEY_MESSAGE = "message", JSON_KEY_RECEIVED_DATE = "receivedDate";

	private final static Logger logger = LoggerFactory.getLogger(ReceiveSmsCheckServiceHandler.class);

	private SmsRepository smsRepository;

	public ReceiveSmsCheckServiceHandler(SmsRepository smsRepository) {

		this.smsRepository = smsRepository;
		setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
	}

	@Override
	protected String doProcess(HttpServletRequest request) {

		String address = request.getParameter(KEY_ADDRESS);
		String sinceStr = request.getParameter(KEY_SINCE);
		logger.debug("request sms messages for: {} since: {}", address, sinceStr);
		long since = Long.parseLong(sinceStr);

		List<Sms> messages = smsRepository.find(address, since);

		JSONArray list = new JSONArray();

		for (Sms sms : messages) {
			JSONObject json = new JSONObject();
			json.put(JSON_KEY_MESSAGE, sms.getMessage());
			json.put(JSON_KEY_RECEIVED_DATE, sms.getReceivedDate());

			list.add(json);
		}

		logger.debug("returning response: {}", list);

		return list.toJSONString();
	}
}
