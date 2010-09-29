package com.appzone.sim.repositories;

import com.appzone.sim.model.Phone;

import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public interface PhoneRepository {

	void add(Phone phone);

	List<Phone> findAll();

	boolean remove(String address);

	void removeAll();
}
