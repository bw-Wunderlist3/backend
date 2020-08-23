package com.wunderlist.backend.services;

import com.wunderlist.backend.models.Item;
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

    //fixme
    @Transactional
    @Override
    public Item saveItem(Item item) {
        Item newItem = new Item();

        return null;
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
        if(item.getStatus() != 0) currentItem.setStatus(item.getStatus());

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
