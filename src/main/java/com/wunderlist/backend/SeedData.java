package com.wunderlist.backend;

import com.wunderlist.backend.models.Item;
import com.wunderlist.backend.models.Todolist;
import com.wunderlist.backend.models.User;
import com.wunderlist.backend.services.ItemService;
import com.wunderlist.backend.services.TodolistService;
import com.wunderlist.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    TodolistService todolistService;
    @Autowired
    ItemService itemService;

    @Transactional
    @Override
    public void run(String[] args) throws Exception {



        User u1 = new User("admin","password" , "lambda@gmail.com", "Java" , "Backend");
          u1.getTodolists();
              userService.save(u1);

        User u2 = new User("pasta", "penne", "pastalove@gmail.com", "Mr.Pasta", "Lasagna");
         u2.getTodolists();
                 userService.save(u2);

        User u3 = new User("futureKid", "IwillbePresident", "futureme@gmail.com", "President", "Cabage");
        u2.getTodolists()
                .add(new Todolist(u3,"Future Goals"));
        userService.save(u3);

        User u4 = new User("reptile", "snakey", "scales@gmail.com", "Python", "Constrictor");
        u2.getTodolists()
                .add(new Todolist(u2,"Reptile Expo"));
        userService.save(u4);

        Todolist t1 = new Todolist(u1,"Finish WonderList");
          t1.getItems()
               .add(new Item("step1", "github",  1995-12-31,"once",  1,t1, ));
        Todolist t2 = new Todolist(u2,"The Pasta Festival" );







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
