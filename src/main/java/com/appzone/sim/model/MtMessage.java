package com.appzone.sim.model;

import java.util.List;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class MtMessage {

	String message;
	List<String> addresses;
	long receivedDate;

	public MtMessage(String message, List<String> addresses, long receivedDate) {
		this.message = message;
		this.addresses = addresses;
		this.receivedDate = receivedDate;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getAddresses() {
		return addresses;
	}

	public long getReceivedDate() {
		return receivedDate;
	}

	@Override
	public String toString() {
		return String.format("%s :: %s :: %s", message, addresses, receivedDate);
	}
}
