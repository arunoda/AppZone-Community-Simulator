/*
	Copyright 2010 Arunoda Susiripala

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
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
