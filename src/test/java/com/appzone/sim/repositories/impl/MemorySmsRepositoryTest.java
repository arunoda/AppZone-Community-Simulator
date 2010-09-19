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
