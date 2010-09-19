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

        List<String> addresses = Arrays.asList(new String[]{"tel:001", "tel:002"});
        MtMessage message = new MtMessage("message", addresses, new Date().getTime());

        MtMessageProcessor processor = new MtMessageProcessor(new MemoryMtMessageRepository(),
                new MemorySmsRepository(), new MemoryPhoneRepository());

        processor.process(message);

        SmsRepository smsRepo = new MemorySmsRepository();
        MtMessageRepository mtRepo = new MemoryMtMessageRepository();

        assertEquals(1, mtRepo.find(0).size());
        assertEquals(1, smsRepo.find("001",0).size());
        assertEquals(1, smsRepo.find("002",0).size());

    }

    public void testProcessSendAll(){

        SmsRepository smsRepo = new MemorySmsRepository();
        MtMessageRepository mtRepo = new MemoryMtMessageRepository();
        PhoneRepository phoneRepo = new MemoryPhoneRepository();

        phoneRepo.add(new Phone("001"));
        phoneRepo.add(new Phone("003"));
        phoneRepo.add(new Phone("002"));

        MtMessageProcessor processor = new MtMessageProcessor(mtRepo, smsRepo, phoneRepo);

        List<String> addresses = Arrays.asList(new String[]{"tel:005", "list:all_registered"});
        MtMessage message = new MtMessage("message", addresses, new Date().getTime());

        processor.process(message);
        
        assertEquals(1, mtRepo.find(0).size());
        assertEquals(1, smsRepo.find("001",0).size());
        assertEquals(1, smsRepo.find("002",0).size());
        assertEquals(1, smsRepo.find("003",0).size());
        assertEquals(1, smsRepo.find("005",0).size());
    }
}
