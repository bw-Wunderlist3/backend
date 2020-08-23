package com.wunderlist.backend.repository;

import com.wunderlist.backend.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
