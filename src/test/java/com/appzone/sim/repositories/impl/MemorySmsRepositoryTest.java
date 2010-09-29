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
import junit.framework.TestCase;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MemorySmsRepositoryTest extends TestCase {

	public void setUp() {
		new MemorySmsRepository().removeAll();
	}

	public void testAddAndFind() {

		SmsRepository repo = new MemorySmsRepository();
		repo.add(new Sms("m1", "001", 1));
		repo.add(new Sms("m2", "001", 3));
		repo.add(new Sms("m3", "002", 3));

		assertEquals(2, repo.find("001", 0).size());
		assertEquals(1, repo.find("001", 1).size());
		assertEquals(0, repo.find("003", 1).size());
	}

	public void testRemoveAll() {

		SmsRepository repo = new MemorySmsRepository();
		repo.add(new Sms("m1", "001", 1));
		repo.add(new Sms("m2", "001", 3));
		repo.add(new Sms("m3", "002", 3));

		repo.removeAll();

		assertEquals(0, repo.find("001", 0).size());
		assertEquals(0, repo.find("001", 1).size());
		assertEquals(0, repo.find("003", 1).size());
	}
}
