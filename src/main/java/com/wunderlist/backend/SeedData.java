package com.wunderlist.backend;

import com.wunderlist.backend.models.Item;
import com.wunderlist.backend.models.State;
import com.wunderlist.backend.models.Todolist;
import com.wunderlist.backend.models.User;
import com.wunderlist.backend.repository.ItemRepository;
import com.wunderlist.backend.repository.TodolistRepository;
import com.wunderlist.backend.services.ItemService;
import com.wunderlist.backend.services.StateService;
import com.wunderlist.backend.services.TodolistService;
import com.wunderlist.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Transactional
@Component
public class SeedData implements CommandLineRunner {
    /*****************************
     * Created by Elisa Alvarez  *
     * Java Backend              *
     * Aug 23, 2020              *
     *****************************/

    @Autowired
    UserService userService;

    @Autowired
    TodolistService todolistService;

    @Autowired
    ItemService itemService;

    @Autowired
    StateService stateService;

    @Autowired
    ItemRepository itemrepos;

    @Autowired
    TodolistRepository todorepos;

    @Transactional
    @Override
    public void run(String[] args) throws Exception {



/*        User u1 = new User("admin","password" , "lambda@gmail.com", "Java" , "Backend");
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
        Todolist t2 = new Todolist(u2,"The Pasta Festival" );*/


        State s1 = new State("Pending");
        State s2 = new State("Canceled");
        State s3 = new State("Complete");

        s1 = stateService.save(s1);
        s2 = stateService.save(s2);
        s3 = stateService.save(s3);

        User u1 = new User("admin","password" , "lambda@gmail.com", "Java" , "Backend");
        User u2 = new User("pasta", "penne", "pastalove@gmail.com", "Mr.Pasta", "Lasagna");
        User u3 = new User("futureKid", "IwillbePresident", "futureme@gmail.com", "President", "Cabage");
        User u4 = new User("reptile", "snakey", "scales@gmail.com", "Python", "Constrictor");

        Todolist t1 = new Todolist(u1,"Finish WonderList");
        Todolist t2 = new Todolist(u2,"The Pasta Festival" );
        Todolist t3 = new Todolist(u2,"The FuturePresident" );
        Todolist t4 = new Todolist(u2,"Reptile Expo" );

        String setarbitrarydate = "2020-08-31"; //Testing string formats for Dates

        Item i1 = new Item("step1", "github", "1995-11-02","once", t1);
        Item i2 = new Item("What I Need","Clothes,soap,Pasta", "2020-12-02","Bi-weekly", t2);
        Item i3 = new Item("My Weekly Goals","Vision Board", "2020-01-21","Weekly", t3);
        Item i4 = new Item("What I Need","Tickets", setarbitrarydate,"Bi-weekly", t4); // String date format used here

        t1.getItems().add(i1);
        t2.getItems().add(i2);
        t3.getItems().add(i3);
        t4.getItems().add(i4);

        u1.getTodolists().add(t1);
        u2.getTodolists().add(t2);
        u2.getTodolists().add(t3);
        u2.getTodolists().add(t4);

        u1 = userService.save(u1);
        u2 = userService.save(u2);
        u3 = userService.save(u3);
        u4 = userService.save(u4);
    }
}
