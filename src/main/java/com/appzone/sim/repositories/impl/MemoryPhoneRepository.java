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

import com.appzone.sim.model.Phone;
import com.appzone.sim.repositories.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MemoryPhoneRepository implements PhoneRepository {

	private final static List<Phone> phones = new ArrayList<Phone>();

	private final static Logger logger = LoggerFactory.getLogger(MemoryPhoneRepository.class);

	@Override
	public void add(Phone phone) {

		logger.debug("adding Phone: {}", phone);

		synchronized (phones) {
			phones.add(phone);
		}
	}

	@Override
	public boolean remove(String address) {

		logger.debug("removing phone with address: {}", address);
		boolean rtn = false;
		synchronized (phones) {
			rtn = phones.remove(new Phone(address));
		}

		return rtn;
	}

	@Override
	public void removeAll() {

		logger.debug("removinf add phones");

		synchronized (phones) {
			phones.clear();
		}
	}

	@Override
	public List<Phone> findAll() {
		return phones;
	}
}
