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
