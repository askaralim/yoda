package com.taklip.yoda.jediorder.converter;

import com.taklip.yoda.jediorder.bean.Id;

public interface IdConverter {
//	long convert(Id id, IdMeta idMeta);
//	Id convert(long id, IdMeta idMeta);
	long convert(Id id);
	Id convert(long id);
}