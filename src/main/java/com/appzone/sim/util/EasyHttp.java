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
package com.appzone.sim.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class EasyHttp {

	private final static Logger logger = LoggerFactory.getLogger(EasyHttp.class);

	/*
	 * public MchoiceAventuraResponse sendMessage(String content, String
	 * address) throws MchoiceAventuraMessagingException {
	 * 
	 * try { String urlParameters = "version=" + URLEncoder.encode("1.0",
	 * "UTF-8") + "&message=" + URLEncoder.encode(content, "UTF-8") +
	 * "&address=" + URLEncoder.encode("tel:" + address, "UTF-8");
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug("requesting - httpHost:" +
	 * this.httpHost + "auth" + this.authId + ":" + this.password +
	 * "\nurlParams: " + urlParameters); }
	 * 
	 * String response = excutePost(this.httpHost, this.authId, this.password,
	 * urlParameters); logger.info("response :" + response);
	 * 
	 * return new MchoiceAventuraResponse(response);
	 * 
	 * } catch (UnsupportedEncodingException e) { throw new
	 * MchoiceAventuraMessagingException("Encoding error in urlParams", e); } }
	 */

	public String excutePost(String targetURL, String urlParameters) throws Exception {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();

			setSdpHeaderParams(connection);

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			sendRequest(urlParameters, connection);

			return getResponse(connection);

		} catch (Exception e) {
			logger.error("Error occcured while sending the request", e);
			throw e;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private void sendRequest(String urlParameters, HttpURLConnection connection) throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("sending the request: " + urlParameters);
		}
		// Send request
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
	}

	private String getResponse(HttpURLConnection connection) throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("getting the response");
		}

		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer response = new StringBuffer();
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		return response.toString();
	}

	private void setSdpHeaderParams(HttpURLConnection connection) throws ProtocolException {
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Accept", "text/xml");
		connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
	}

}
