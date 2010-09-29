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
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MemoryMtMessageRepositortTest extends TestCase {

	public void setUp() {
		new MemoryMtMessageRepository().removeAll();
	}

	public void testAddAndFind() {

		MtMessage m1 = new MtMessage("message", Arrays.asList(new String[] { "add1", "add2" }), 112);
		MtMessageRepository repo = new MemoryMtMessageRepository();
		repo.add(m1);

		assertEquals(1, repo.find(0).size());
		assertEquals("message", repo.find(0).get(0).getMessage());
	}

	public void testFind() {

		MtMessageRepository repo = new MemoryMtMessageRepository();
		repo.add(new MtMessage("m1", null, 10));
		repo.add(new MtMessage("m2", null, 20));
		repo.add(new MtMessage("m3", null, 22));
		repo.add(new MtMessage("m4", null, 9));

		assertEquals(2, repo.find(10).size());

	}

	public void testRemoveAll() {

		MtMessageRepository repo = new MemoryMtMessageRepository();
		repo.add(new MtMessage("m1", null, 10));
		repo.add(new MtMessage("m2", null, 20));
		repo.add(new MtMessage("m3", null, 22));
		repo.add(new MtMessage("m4", null, 9));

		repo.removeAll();
		assertEquals(0, repo.find(0).size());
	}

}
