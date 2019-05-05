package com.taklip.yoda.jediorder.populator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taklip.yoda.jediorder.bean.Id;
import com.taklip.yoda.jediorder.bean.IdMeta;
import com.taklip.yoda.jediorder.timer.TimeService;

@Service
public class IdPopulatorImpl implements IdPopulator, ResetPopulator {
	@Autowired
	protected IdMeta idMeta;

	protected long sequence = 0;
	protected long lastTimestamp = -1;

	private Lock lock = new ReentrantLock();

	public void populateId(TimeService timer, Id id) {
		lock.lock();

		try {
			long timestamp = timer.generateTime();

			timer.validateTimestamp(lastTimestamp, timestamp);

			if (timestamp == lastTimestamp) {
				sequence++;
				sequence &= idMeta.getSequenceBitsMask();

				if (sequence == 0) {
					timestamp = timer.tillNextTimeUnit(lastTimestamp);
				}
			}
			else {
				lastTimestamp = timestamp;
				sequence = 0;
			}

			id.setSequence(sequence);
			id.setTime(timestamp);
		}
		finally {
			lock.unlock();
		}
	}

	public void reset() {
		this.sequence = 0;
		this.lastTimestamp = -1;
	}
}