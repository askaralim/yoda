package com.yoda.state.service;

import java.util.List;

import com.yoda.state.model.State;

public interface StateService {
	public List<State> getBySiteId(long siteId);

	public State getState(long siteId, String stateCode);
}
