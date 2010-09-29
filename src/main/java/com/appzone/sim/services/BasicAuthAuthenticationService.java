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
package com.appzone.sim.services;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appzone.sim.model.Application;

public class BasicAuthAuthenticationService implements AuthenticationService {

	private final Application application;
	private final static Logger logger = LoggerFactory.getLogger(BasicAuthAuthenticationService.class);

	public BasicAuthAuthenticationService(Application application) {

		this.application = application;
	}

	@Override
	public boolean authenticate(HttpServletRequest request) {

		// Autherization header
		String auth = request.getHeader("Authorization");
		logger.debug("Basic Auth header: {}", auth);
		if (auth == null || auth.trim().length() < 6)
			return false;

		auth = auth.substring(6); // remove "BASIC "

		String decoded = new String(Base64.decodeBase64(auth));
		logger.debug("Decoded Basic Auth: {}", decoded);

		String parts[] = decoded.split(":");

		return checkAuthentication(parts[0], parts[1]);
	}

	private boolean checkAuthentication(String username, String password) {

		logger.debug("Authenticate against: {} : {}", username, password);
		return username.equals(application.getUsername()) && password.equals(application.getPassword());
	}

}
