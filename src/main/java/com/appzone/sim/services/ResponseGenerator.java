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

/**
 * author: arunoda.susiripala@gmail.com
 */
public class ResponseGenerator {

	/**
	 * Generate response after the message processes
	 * 
	 * @param processed
	 *            result from the mtMessageProcessor
	 * @return
	 */
	public String generateResponseAfterMessageProcessed(boolean processed) {

		String correlator = getCorrelator();

		if (processed) {
			return generateXMLResponse(correlator, "SBL-SMS-MT-2000", "SUCCESS");
		} else {
			return generateXMLResponse(correlator, "SBL-SMS-MT-5000", "FAILED");
		}
	}

	private String generateXMLResponse(String correlator, String errorCode, String errorMessage) {

		String xmlString = "<mchoice_sdp_sms_response>\n" + "   <version>1.0</version>\n" + "   <correlator>"
				+ correlator + "</correlator>\n" + "   <status_code>" + errorCode + "</status_code>\n"
				+ "   <status_message>" + errorMessage + "</status_message>\n" + "</mchoice_sdp_sms_response>\n";

		return xmlString;
	}

	public String generateResponseWhenLoginFailed() {

		return generateXMLResponse(getCorrelator(), "401", "UnAuthorized");
	}

	public String getCorrelator() {
		return "" + ((int) (Math.random() * 1000000000L));
	}

}
