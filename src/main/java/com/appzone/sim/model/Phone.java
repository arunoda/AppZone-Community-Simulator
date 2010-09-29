package com.appzone.sim.model;

/**
 * author: arunoda.susiripala@gmail.com
 */
public class Phone {

	private String address;

	public Phone(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Phone phone = (Phone) o;

		if (address != null ? !address.equals(phone.address) : phone.address != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return address != null ? address.hashCode() : 0;
	}
}
