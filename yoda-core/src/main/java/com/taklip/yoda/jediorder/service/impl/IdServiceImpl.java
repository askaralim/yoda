package com.taklip.yoda.jediorder.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taklip.yoda.jediorder.bean.Id;
import com.taklip.yoda.jediorder.converter.IdConverter;
import com.taklip.yoda.jediorder.populator.IdPopulator;
import com.taklip.yoda.jediorder.service.IdService;
import com.taklip.yoda.jediorder.service.MachineIdProvider;
import com.taklip.yoda.jediorder.timer.TimeService;

@Service
public class IdServiceImpl implements IdService {
	protected final Logger log = LoggerFactory.getLogger(IdServiceImpl.class);

	protected long version = 0;

	@Autowired
	protected IdConverter idConverter;

	@Autowired
	protected TimeService timeService;

	@Autowired
	protected IdPopulator idPopulator;

	@Autowired
	protected MachineIdProvider machineIdProvider;

	public Date translate(final long time) {
		return timeService.translateTime(time);
	}

	public Id explainId(long id) {
		return idConverter.convert(id);
	}

	public long generateId() {
		Id id = new Id();

		id.setMachine(machineIdProvider.getMachineId());
		id.setVersion(version);

		idPopulator.populateId(timeService, id);

		long ret = idConverter.convert(id);

		if (log.isTraceEnabled())
			log.trace(String.format("Id: %s => %d", id, ret));

		return ret;
	}
}