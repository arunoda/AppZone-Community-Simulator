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
package com.appzone.sim.repositories.impl;

import com.appzone.sim.model.Sms;
import com.appzone.sim.repositories.SmsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MemorySmsRepository implements SmsRepository {

	private static Map<String, List<Sms>> map = new HashMap<String, List<Sms>>();

	private final static Logger logger = LoggerFactory.getLogger(MemorySmsRepository.class);

	@Override
	public void add(Sms message) {

		logger.debug("adding sms: {}", message);

		List<Sms> smsList = map.get(message.getAddress());
		if (smsList == null) {
			smsList = new ArrayList<Sms>();
			logger.debug("creating a new list for the address: {}", message.getAddress());
			map.put(message.getAddress(), smsList);
		}

		smsList.add(message);
	}

	@Override
	public List<Sms> find(String address, long since) {

		logger.debug("finding sms for address: {} since: {}", address, since);
		List<Sms> smsList = (map.get(address) == null) ? new ArrayList<Sms>() : map.get(address);
		List<Sms> newList = new ArrayList<Sms>();

		for (Sms sms : smsList) {
			if (sms.getReceivedDate() > since) {
				newList.add(sms);
			}
		}

		return newList;
	}

	@Override
	public void removeAll() {

		logger.debug("removing all the mtMessages");
		map.clear();
	}
}
