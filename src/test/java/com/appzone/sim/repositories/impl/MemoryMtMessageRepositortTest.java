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
