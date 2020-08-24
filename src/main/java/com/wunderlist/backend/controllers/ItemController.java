package com.wunderlist.backend.controllers;

import com.wunderlist.backend.models.Item;
import com.wunderlist.backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
//@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /*
    Get all items
    Route: http://localhost:5280/items
    */
    @GetMapping(value = "/items", produces = "application/json")
    public ResponseEntity<?> listAllItems() {
        List<Item> allItems = itemService.findAllItems();

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    /*
    Get specific Item by ID
    Route: http://localhost:5280/items/:itemid
    */
    @GetMapping(value = "/items/{itemid}", produces = "application/json")
    public ResponseEntity<?> listanItem(@PathVariable long itemid) {
        Item currentItem = itemService.findItemById(itemid);

        return new ResponseEntity<>(currentItem, HttpStatus.OK);
    }

    /*
    Create a new Item
    Route: http://localhost:5280/items/t/:todoid
    */
    @PostMapping(value = "/items/t/{todoid}", consumes = "applciation/json")
    public ResponseEntity<?> createItem(@PathVariable long todoid, @Valid @RequestBody Item newitem) throws URISyntaxException {
        //Note: the newitem object in the parameter should only have 4 fields: name, description, duedate, frequency
        newitem = itemService.saveItem(todoid, newitem);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newItemURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemid}")
                .buildAndExpand(newitem.getItemid())
                .toUri();
        responseHeaders.setLocation(newItemURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    /*
    Replace an entire Item
    Route: http://localhost:5280/items/:itemid
    */
    // Also needing to change this similar to the way I changed PostMapping above
    @PutMapping(value = "/updateitem/{itemid}", consumes = "application/json")
    public ResponseEntity<?> updateFullItem(@Valid @RequestBody Item updateitem, @PathVariable long itemid) {
        // Unlike the Post Mapping, this updateitem object needs 5 fields: name, description, duedate, frequency, -status-, todolist(obj)
        updateitem.setItemid(itemid);
        itemService.changeItem(updateitem);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Updates a field for a current Item
    Route: http://localhost:5280/items/:itemid
    */
    @PutMapping(value = "/items/{itemid}", consumes = "application/json")
    public ResponseEntity<?> updateItemContent(@RequestBody Item updateitem, @PathVariable long itemid) {
        itemService.updateItem(updateitem, itemid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Deletes an Item
    Route: http://localhost:5280/items/:itemid
    */
    @DeleteMapping(value = "/items/{itemid}")
    public ResponseEntity<?> trashItem(@PathVariable long itemid) {
        itemService.deleteItem(itemid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
