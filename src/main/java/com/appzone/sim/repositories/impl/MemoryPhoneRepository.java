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
