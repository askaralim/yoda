package com.taklip.yoda.jediorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taklip.yoda.jediorder.bean.IdMeta;
import com.taklip.yoda.jediorder.property.JediOrderProperties;

@Service
public class MachineIdProvider implements InitializingBean {
	protected final Logger log = LoggerFactory.getLogger(MachineIdProvider.class);

	protected final JediOrderProperties properties;
	private long machineId = -1;

	@Autowired
	protected IdMeta idMeta;

	@Autowired
	public MachineIdProvider(JediOrderProperties properties) {
		this.properties = properties;
	}

	public void afterPropertiesSet() {
		this.machineId = properties.getMachineId();

		validateMachineId(this.machineId);
	}

	private void validateMachineId(long machineId) {
		if (machineId < 0 || machineId >= (1 << this.idMeta.getMachineBits())) {
			log.error("Machine ID[" + machineId + "] is not valid, Machine ID should be a value in [0, " + (1 << this.idMeta.getMachineBits()) + "), service is enable.");

			throw new IllegalStateException("Machine ID[" + machineId + "] is not valid, Machine ID should be a value in [0, " + (1 << this.idMeta.getMachineBits()) + "), service is enable.");
		}
	}

	public long getMachineId() {
		return this.machineId;
	}
}