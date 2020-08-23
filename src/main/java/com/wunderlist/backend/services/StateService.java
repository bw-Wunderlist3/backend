package com.wunderlist.backend.services;

import com.wunderlist.backend.models.State;

import java.util.List;

public interface StateService {
    List<State> findAllStatuses();

    State findStatusById(long statusid);
}
