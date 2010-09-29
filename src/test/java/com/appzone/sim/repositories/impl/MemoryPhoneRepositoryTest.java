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
import junit.framework.TestCase;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MemoryPhoneRepositoryTest extends TestCase {

	public void setUp() {
		new MemoryPhoneRepository().removeAll();
	}

	public void testAddAndFind() {

		PhoneRepository repo = new MemoryPhoneRepository();
		repo.add(new Phone("sdssds"));
		repo.add(new Phone("sdssds"));
		repo.add(new Phone("sdssds"));
		repo.add(new Phone("sdssds"));

		assertEquals(4, repo.findAll().size());
	}

	public void testRemove() {

		PhoneRepository repo = new MemoryPhoneRepository();
		repo.add(new Phone("1111"));
		repo.add(new Phone("22"));

		repo.remove("1111");

		assertEquals(1, repo.findAll().size());
		assertEquals("22", repo.findAll().get(0).getAddress());
	}

	public void testRemoveAll() {

		PhoneRepository repo = new MemoryPhoneRepository();
		repo.add(new Phone("sdssds"));
		repo.add(new Phone("sdssds"));
		repo.add(new Phone("sdssds"));
		repo.add(new Phone("sdssds"));

		repo.removeAll();

		assertEquals(0, new MemoryPhoneRepository().findAll().size());
	}
}
