package com.wunderlist.backend.services;

import com.wunderlist.backend.exceptions.ResourceNotFoundException;
import com.wunderlist.backend.models.Item;
import com.wunderlist.backend.models.Todolist;
import com.wunderlist.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
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
        Item newItem = new Item(item.getName(), item.getDescription(), item.getDate(), item.getFrequency(), currentTodo);

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
    public Item changeItem(long itemid, Item item) {
        // Note: unlike the saveItem method above, this item object needs x6x, 5 fields instead of 4:
        // name, description, duedate, frequency, -status-, todolist(obj)

        if(itemrepos.findById(itemid).isPresent()) {
            Item currentItem = findItemById(itemid);
            //FINALLY FIXED THIS TROUBLESOME DATE CONVERSION FOR THIS PUT METHOD! - Peter Wood
            LocalDate newDate = LocalDate.parse(item.getDate());

            currentItem.setName(item.getName());
            currentItem.setDescription(item.getDescription());
            currentItem.setDuedate(newDate);
            currentItem.setFrequency(item.getFrequency());
            //currentItem.setTodolist(item.getTodolist());
            //I'm questioning if the above line is needed for PUT methods

            return itemrepos.save(currentItem);
        } else throw new ResourceNotFoundException("Item with id: " + itemid + " was not found.");

        /*
        Item currentItem = findItemById(itemid);

        currentItem.setName(item.getName());
        currentItem.setDescription(item.getDescription());
        currentItem.setDuedate(item.getDuedate());
        currentItem.setFrequency(item.getFrequency());
        //currentItem.setStatus(item.getStatus());
        //currentItem.setTodolist(todolistService.findListById(item.getTodolist().getTodoid()));
        currentItem.setTodolist(item.getTodolist());
        */

        //return itemrepos.save(currentItem);
    }

    @Transactional
    @Override
    public Item updateItem(Item item, long itemid) {
        Item currentItem = itemrepos.findById(itemid)
                .orElseThrow(() -> new EntityNotFoundException("Item id: " + itemid + " was not found.")); // Change to ResponseNotFoundException

        LocalDate newLocalDate = LocalDate.parse(item.getDate());

        if(item.getName() != null) currentItem.setName(item.getName());
        if(item.getDescription() != null) currentItem.setDescription(item.getDescription());
        currentItem.setDuedate(newLocalDate);
        if(item.getFrequency() != null) currentItem.setFrequency(item.getFrequency());
        //if(item.getStatus() != 0) currentItem.setStatus(item.getStatus());
        if(item.getTodolist() != null) currentItem.setTodolist(item.getTodolist());

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
