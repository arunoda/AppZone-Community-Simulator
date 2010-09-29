package com.appzone.sim.repositories.impl;

import com.appzone.sim.model.MtMessage;
import com.appzone.sim.repositories.MtMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MemoryMtMessageRepository implements MtMessageRepository {

	private final static List<MtMessage> messages = new ArrayList<MtMessage>();
	private final static Logger logger = LoggerFactory.getLogger(MemoryMtMessageRepository.class);

	@Override
	public void add(MtMessage message) {

		logger.debug("adding a mtMessage: {}", message);
		synchronized (messages) {
			messages.add(message);
		}
	}

	@Override
	public List<MtMessage> find(long since) {

		logger.debug("finding mtMessages since: {}", since);
		List<MtMessage> newList = new ArrayList<MtMessage>();

		synchronized (messages) {
			for (MtMessage message : messages) {
				if (message.getReceivedDate() > since) {
					newList.add(message);
				}
			}
		}

		return newList;
	}

	@Override
	public void removeAll() {

		logger.debug("removing all the MtMessages");
		synchronized (messages) {
			messages.clear();
		}
	}
}
