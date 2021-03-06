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

import com.appzone.sim.model.MtMessage;
import com.appzone.sim.repositories.MtMessageRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MtLogCheckServiceHandler extends AbstractServiceHandler {

	public final static String MATCHING_KEYWORD = "mtlog", KEY_SINCE = "since", JSON_KEY_MESSAGE = "message",
			JSON_KEY_ADDRESSES = "addresses", JSON_KEY_RECEIVED_DATE = "receivedDate";

	private final static Logger logger = LoggerFactory.getLogger(MtLogCheckServiceHandler.class);

	private MtMessageRepository mtMessageRepository;

	public MtLogCheckServiceHandler(MtMessageRepository mtMessageRepository) {

		this.mtMessageRepository = mtMessageRepository;
		setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
	}

	@Override
	protected String doProcess(HttpServletRequest request) {

		String sinceStr = request.getParameter(KEY_SINCE);
		logger.debug("request mt logs since: {}", sinceStr);

		long since = Long.parseLong(sinceStr);

		List<MtMessage> messages = mtMessageRepository.find(since);

		JSONArray list = new JSONArray();

		for (MtMessage mtMessage : messages) {

			String message = mtMessage.getMessage();
			String addresses = JSONValue.toJSONString(mtMessage.getAddresses());
			String receivedDate = "" + mtMessage.getReceivedDate();

			JSONObject json = new JSONObject();
			json.put(JSON_KEY_MESSAGE, message);
			json.put(JSON_KEY_ADDRESSES, JSONValue.parse(addresses));
			json.put(JSON_KEY_RECEIVED_DATE, receivedDate);

			list.add(json);
		}

		logger.debug("returning response: {}", list);

		return list.toJSONString();
	}
}
