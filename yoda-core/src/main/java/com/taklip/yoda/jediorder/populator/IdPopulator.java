package com.taklip.yoda.jediorder.populator;

import com.taklip.yoda.jediorder.bean.Id;
import com.taklip.yoda.jediorder.timer.TimeService;

public interface IdPopulator {
	void populateId(TimeService timer, Id id);
}