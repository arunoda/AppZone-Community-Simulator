package com.appzone.sim.repositories;

import com.appzone.sim.model.MtMessage;

import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public interface MtMessageRepository {

    void add(MtMessage message);

    List<MtMessage> find(long since);

    void removeAll();
    
}
