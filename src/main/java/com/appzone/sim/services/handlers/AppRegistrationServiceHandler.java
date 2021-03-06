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
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class AppRegistrationServiceHandler extends AbstractServiceHandler {

	private final static Logger logger = LoggerFactory.getLogger(AppRegistrationServiceHandler.class);

	public final static String MATCHING_KEYWORD = "app", KEY_URL = "url", KEY_USERNAME = "username",
			KEY_PASSWORD = "password", KEY_INFO_REQUEST = "infoRequest", JSON_KEY_URL = "url",
			JSON_KEY_USERNAME = "username", JSON_KEY_PASSWORD = "password";

	public AppRegistrationServiceHandler() {
		setKeyWordMatcher(new DefaultKewordMatcher(MATCHING_KEYWORD));
	}

	@Override
	protected String doProcess(HttpServletRequest request) {

		String infoRequest = request.getParameter(KEY_INFO_REQUEST);

		if (infoRequest != null) {
			Application application = Application.getApplication();
			JSONObject json = new JSONObject();
			json.put(JSON_KEY_URL, application.getUrl());
			json.put(JSON_KEY_USERNAME, application.getUsername());
			json.put(JSON_KEY_PASSWORD, application.getPassword());

			return json.toJSONString();
		} else {
			String url = request.getParameter(KEY_URL);
			String username = request.getParameter(KEY_USERNAME);
			String password = request.getParameter(KEY_PASSWORD);

			logger.info("configuring application with: " + url + " :: {} :: {}", username, password);

			Application.configure(url, username, password);

			JSONObject json = new JSONObject();
			json.put(ServiceHandler.JSON_KEY_RESULT, true);

			return json.toJSONString();
		}
	}
}
