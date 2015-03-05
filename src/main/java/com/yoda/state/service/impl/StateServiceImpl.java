package com.yoda.state.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoda.state.dao.StateDAO;
import com.yoda.state.model.State;
import com.yoda.state.service.StateService;

@Service
public class StateServiceImpl implements StateService{

	@Autowired
	private StateDAO stateDAO;

	public List<State> getBySiteId(long siteId) {
		return stateDAO.getBySiteId(siteId);
	}

	public State getState(long siteId, String stateCode) {
		return stateDAO.getByS_SC(siteId, stateCode);
	}
}
