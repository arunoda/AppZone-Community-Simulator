package com.appzone.sim;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import com.appzone.sim.model.Application;
import com.appzone.sim.repositories.impl.MemoryMtMessageRepository;
import com.appzone.sim.repositories.impl.MemoryPhoneRepository;
import com.appzone.sim.repositories.impl.MemorySmsRepository;

import junit.framework.TestCase;

public class SimulatorTest extends TestCase {
	
	private Mockery context ;

    public void setUp() {
        context = new Mockery() {{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};

        Application.configure(null, null, null);
        new MemoryMtMessageRepository().removeAll();
        new MemorySmsRepository().removeAll();
        new MemoryPhoneRepository().removeAll();
    }
    
    
}
