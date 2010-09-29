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
import com.appzone.sim.repositories.MtMessageRepository;
import com.appzone.sim.repositories.PhoneRepository;
import com.appzone.sim.repositories.SmsRepository;
import com.appzone.sim.repositories.impl.MemoryMtMessageRepository;
import com.appzone.sim.repositories.impl.MemoryPhoneRepository;
import com.appzone.sim.repositories.impl.MemorySmsRepository;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MemoryMtMessageProcessorTest extends TestCase {

	public void setUp() {

		new MemoryMtMessageRepository().removeAll();
		new MemorySmsRepository().removeAll();
		new MemoryPhoneRepository().removeAll();
	}

	public void testProcessNormal() {

		List<String> addresses = Arrays.asList(new String[] { "tel:001", "tel:002" });
		MtMessage message = new MtMessage("message", addresses, new Date().getTime());

		MtMessageProcessor processor = new MtMessageProcessor(new MemoryMtMessageRepository(),
				new MemorySmsRepository(), new MemoryPhoneRepository());

		processor.process(message);

		SmsRepository smsRepo = new MemorySmsRepository();
		MtMessageRepository mtRepo = new MemoryMtMessageRepository();

		assertEquals(1, mtRepo.find(0).size());
		assertEquals(1, smsRepo.find("001", 0).size());
		assertEquals(1, smsRepo.find("002", 0).size());

	}

	public void testProcessSendAll() {

		SmsRepository smsRepo = new MemorySmsRepository();
		MtMessageRepository mtRepo = new MemoryMtMessageRepository();
		PhoneRepository phoneRepo = new MemoryPhoneRepository();

		phoneRepo.add(new Phone("001"));
		phoneRepo.add(new Phone("003"));
		phoneRepo.add(new Phone("002"));

		MtMessageProcessor processor = new MtMessageProcessor(mtRepo, smsRepo, phoneRepo);

		List<String> addresses = Arrays.asList(new String[] { "tel:005", "list:all_registered" });
		MtMessage message = new MtMessage("message", addresses, new Date().getTime());

		processor.process(message);

		assertEquals(1, mtRepo.find(0).size());
		assertEquals(1, smsRepo.find("001", 0).size());
		assertEquals(1, smsRepo.find("002", 0).size());
		assertEquals(1, smsRepo.find("003", 0).size());
		assertEquals(1, smsRepo.find("005", 0).size());
	}
}
