package com.wunderlist.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
    @Transactional
    @Override
    public void run(String[] args) throws Exception {
        /*
        Users Table
        long userid
        String username
        String password
        String email
        String firstname
        String lastmane

        Todolists Table
        long todoid
        String title
        long (foreign) userid

        Items Table
        long itemid
        String name
        String description
        String duedate
        String frequency (I thought about int, but changed my mind)
        int status
        long (foreign) todoid

        Status table
        long statusid
        String type

        Itemstatus table (jointable)
        long (foreign) itemid
        long (foreign) statusid

        Note: All of these tables need: created_by, created_date, last_modified_by,
        and last_modified_date (or whatever stylization/casing desired)
        */
    }
}
