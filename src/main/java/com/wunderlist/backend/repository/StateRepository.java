package com.wunderlist.backend.repository;

import com.wunderlist.backend.models.State;
import org.springframework.data.repository.CrudRepository;

public interface StateRepository extends CrudRepository<State, Long> {
}
