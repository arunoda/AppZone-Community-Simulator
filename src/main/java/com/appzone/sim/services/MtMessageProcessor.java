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

import com.appzone.sim.model.MtMessage;
import com.appzone.sim.model.Phone;
import com.appzone.sim.model.Sms;
import com.appzone.sim.repositories.MtMessageRepository;
import com.appzone.sim.repositories.PhoneRepository;
import com.appzone.sim.repositories.SmsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MtMessageProcessor {

	private MtMessage mtMessage;
	private final MtMessageRepository mtMessageRepository;
	private final SmsRepository smsRepository;
	private final PhoneRepository phoneRepository;

	private final static Logger logger = LoggerFactory.getLogger(MtMessageProcessor.class);

	public MtMessageProcessor(MtMessageRepository mtMessageRepository, SmsRepository smsRepository,
			PhoneRepository phoneRepository) {

		logger.debug("creating MtMessageProcessor");
		this.mtMessageRepository = mtMessageRepository;
		this.smsRepository = smsRepository;
		this.phoneRepository = phoneRepository;
	}

	public boolean process(MtMessage mtMessage) {

		this.mtMessage = mtMessage;

		mtMessageRepository.add(this.mtMessage);
		logger.debug("processing mtMessage: {}", mtMessage);

		for (String address : this.mtMessage.getAddresses()) {
			if (!processForAddress(address)) {
				logger.error("Message Sending failed for adderess: {}", address);
				return false;
			}
		}

		return true;

	}

	public boolean processForAddress(String address) {

		boolean processed = false;
		String[] parts = address.split(":");

		logger.debug("parsing address string: {} into: {}", address, parts);
		if (parts.length == 2) {

			if ("tel".equals(parts[0])) {

				Sms sms = new Sms(mtMessage.getMessage(), parts[1], new Date().getTime());
				logger.debug("sending sms: {}", sms);
				smsRepository.add(sms);
				processed = true;

			} else if ("list".equals(parts[0])) {
				// send messages to all

				List<Phone> phones = phoneRepository.findAll();
				logger.debug("sending message: {} for all: {}", mtMessage, phones);

				for (Phone phone : phones) {
					Sms sms = new Sms(mtMessage.getMessage(), phone.getAddress(), new Date().getTime());
					smsRepository.add(sms);
				}
				processed = true;

			}

		}

		return processed;
	}
}
