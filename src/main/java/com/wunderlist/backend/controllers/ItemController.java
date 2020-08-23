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
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /*
    Get all items
    Route: http://localhost:5280/items
    */
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<?> listAllItems() {
        List<Item> allItems = itemService.findAllItems();

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    /*
    Get specific Item by ID
    Route: http://localhost:5280/items/:itemid
    */
    @GetMapping(value = "/{itemid}", produces = "application/json")
    public ResponseEntity<?> listanItem(@PathVariable long itemid) {
        Item currentItem = itemService.findItemById(itemid);

        return new ResponseEntity<>(currentItem, HttpStatus.OK);
    }

    /*
    Create a new Item
    Route: http://localhost:5280/items
    */
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<?> createItem(@Valid @RequestBody Item newitem) throws URISyntaxException {
        newitem.setItemid(0);
        newitem = itemService.saveItem(newitem);

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
    @PutMapping(value = "/{itemid}", consumes = "application/json")
    public ResponseEntity<?> updateFullItem(@Valid @RequestBody Item updateitem, @PathVariable long itemid) {
        updateitem.setItemid(itemid);
        itemService.saveItem(updateitem);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Updates a field for a current Item
    Route: http://localhost:5280/items/:itemid
    */
    @PatchMapping(value = "/{itemid}", consumes = "application/json")
    public ResponseEntity<?> updateItemContent(@RequestBody Item updateitem, @PathVariable long itemid) {
        itemService.updateItem(updateitem, itemid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Deletes an Item
    Route: http://localhost:5280/items/:itemid
    */
    @DeleteMapping(value = "/{itemid}")
    public ResponseEntity<?> trashItem(@PathVariable long itemid) {
        itemService.deleteItem(itemid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
