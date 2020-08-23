package com.wunderlist.backend.services;

import com.wunderlist.backend.models.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAllItems();

    Item findItemById(long itemid);

    Item saveItem(Item item);

    Item updateItem(Item item, long itemid);

    void deleteItem(long itemid);
}
