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

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * author: arunoda.susiripala@gmail.com
 */
public abstract class AbstractServiceHandler implements ServiceHandler {

	private ServiceHandler nextServiceHandler;
	private KeywordMatcher keywordMatcher;

	private static final Logger logger = LoggerFactory.getLogger(AbstractServiceHandler.class);

	@Override
	public void setNextServiceHandler(ServiceHandler serviceHandler) {
		this.nextServiceHandler = serviceHandler;
	}

	@Override
	public void setKeyWordMatcher(KeywordMatcher keywordMatcher) {
		this.keywordMatcher = keywordMatcher;
	}

	@Override
	public String serve(HttpServletRequest request) {

		logger.debug("start serving request");
		if (keywordMatcher.match(request)) {
			return doProcess(request);
		} else {

			if (nextServiceHandler != null) {
				return nextServiceHandler.serve(request);
			} else {
				logger.debug("cannot find any Handler to serve");
				JSONObject json = new JSONObject();
				json.put("error", "no handler found");

				return json.toJSONString();
			}

		}
	}

	protected abstract String doProcess(HttpServletRequest request);
}
