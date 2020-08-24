package com.wunderlist.backend.services;

import com.wunderlist.backend.models.Item;
import com.wunderlist.backend.models.Todolist;
import com.wunderlist.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "itemService")
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemrepos;

    @Autowired
    private TodolistService todolistService;

    @Override
    public List<Item> findAllItems() {
        List<Item> list = new ArrayList<>();
        itemrepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public Item findItemById(long itemid) {
        return itemrepos.findById(itemid)
                .orElseThrow(() -> new EntityNotFoundException("Item id: " + itemid + " was not found.")); // Change to ResponseNotFoundException
    }

    @Transactional
    @Override
    public Item saveItem(long todoid, Item item) {
        // The item object in the parameter above should be the one containing 4 fields instead of x6x, 5 (removed int status)

        Todolist currentTodo = todolistService.findListById(todoid);
        Item newItem = new Item(item.getName(), item.getDescription(), item.getDuedate(), item.getFrequency(), currentTodo);

        /*
        Requested Object shape:
            {
                "name": "required string",
                "description": "optional string",
                "duedate": dateValue,
                "frequency": "optional string",
            }
        */

        return itemrepos.save(newItem);
    }

    @Transactional
    @Override
    public Item changeItem(Item item) {
        // Note: unlike the saveItem method above, this item object needs x6x, 5 fields instead of 4:
        // name, description, duedate, frequency, -status-, todolist(obj)
        Item currentItem = new Item();

        currentItem.setName(item.getName());
        currentItem.setDescription(item.getDescription());
        currentItem.setDuedate(item.getDuedate());
        currentItem.setFrequency(item.getFrequency());
        //currentItem.setStatus(item.getStatus());
        currentItem.setTodolist(todolistService.findListById(item.getTodolist().getTodoid()));

        return itemrepos.save(currentItem);
    }

    @Transactional
    @Override
    public Item updateItem(Item item, long itemid) {
        Item currentItem = itemrepos.findById(itemid)
                .orElseThrow(() -> new EntityNotFoundException("Item id: " + itemid + " was not found.")); // Change to ResponseNotFoundException

        if(item.getName() != null) currentItem.setName(item.getName());
        if(item.getDescription() != null) currentItem.setDescription(item.getDescription());
        if(item.getDuedate() != null) currentItem.setDuedate(item.getDuedate());
        if(item.getFrequency() != null) currentItem.setFrequency(item.getFrequency());
        //if(item.getStatus() != 0) currentItem.setStatus(item.getStatus());

        return itemrepos.save(currentItem);
    }

    @Transactional
    @Override
    public void deleteItem(long itemid) {
        itemrepos.findById(itemid)
                .orElseThrow(() -> new EntityNotFoundException("Item id: " + itemid + " was not found.")); // Change to ResponseNotFoundException
        itemrepos.deleteById(itemid);
    }
}
