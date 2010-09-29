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
