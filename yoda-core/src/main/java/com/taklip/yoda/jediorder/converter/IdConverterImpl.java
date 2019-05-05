package com.taklip.yoda.jediorder.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taklip.yoda.jediorder.bean.Id;
import com.taklip.yoda.jediorder.bean.IdMeta;

@Component
public class IdConverterImpl implements IdConverter {
	@Autowired
	protected IdMeta idMeta;

	public IdConverterImpl() {
	}

	public long convert(Id id) {
		return doConvert(id);
	}

	protected long doConvert(Id id) {
		long ret = 0;

		ret |= id.getVersion();
		ret |= id.getTime() << idMeta.getTimeBitsStartPos();
		ret |= id.getMachine() << idMeta.getMachineBitsStartPos();
		ret |= id.getSequence() << idMeta.getSequenceBitsStartPos();

		return ret;
	}

	public Id convert(long id) {
		return doConvert(id);
	}

	protected Id doConvert(long id) {
		Id ret = new Id();

		ret.setVersion(id & idMeta.getVersionBitsMask());
		ret.setTime((id >>> idMeta.getTimeBitsStartPos()) & idMeta.getTimeBitsMask());
		ret.setMachine((id >>> idMeta.getMachineBitsStartPos()) & idMeta.getMachineBitsMask());
		ret.setSequence((id >>> idMeta.getSequenceBitsStartPos()) & idMeta.getSequenceBitsMask());

		return ret;
	}
}