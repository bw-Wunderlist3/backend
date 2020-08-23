package com.wunderlist.backend.repository;

import com.wunderlist.backend.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
