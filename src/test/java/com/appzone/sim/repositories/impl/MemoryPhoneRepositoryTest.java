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
