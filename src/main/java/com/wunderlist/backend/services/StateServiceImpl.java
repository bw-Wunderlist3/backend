package com.wunderlist.backend.services;

import com.wunderlist.backend.models.State;
import com.wunderlist.backend.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "stateService")
public class StateServiceImpl implements StateService {
    @Autowired
    private StateRepository statrepos;

    @Override
    public List<State> findAllStatuses() {
        List<State> list = new ArrayList<>();
        statrepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public State findStatusById(long statusid) {
        return statrepos.findById(statusid)
                .orElseThrow(() -> new EntityNotFoundException("Status id: " + statusid + " was not found.")); // Change to ResponseNotFoundException later
    }

    @Transactional
    @Override
    public State save(State state) {
        if(state.getItems().size() > 0) throw new EntityNotFoundException("Item states are not updated through State.");

        return statrepos.save(state);
    }
}
