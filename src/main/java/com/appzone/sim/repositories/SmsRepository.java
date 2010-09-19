package com.appzone.sim.repositories;

import com.appzone.sim.model.Sms;

import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public interface SmsRepository {

    void add(Sms message);

    List<Sms> find(String address, long since);

    void removeAll();
}
