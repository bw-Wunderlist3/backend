package com.wunderlist.backend;

import com.wunderlist.backend.models.*;
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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

        System.out.println("s1 ID is: " + s1.getStatusid());
        System.out.println("s2 ID is: " + s2.getStatusid());
        System.out.println("s3 ID is: " + s3.getStatusid());

        User u1 = new User("admin","password" , "lambda@gmail.com", "Java" , "Backend");
        User u2 = new User("pasta", "penne", "pastalove@gmail.com", "Mr.Pasta", "Lasagna");
        User u3 = new User("futureKid", "IwillbePresident", "futureme@gmail.com", "President", "Cabage");
        User u4 = new User("reptile", "snakey", "scales@gmail.com", "Python", "Constrictor");

        Todolist t1 = new Todolist(u1,"Finish WonderList");
        Todolist t2 = new Todolist(u2,"The Pasta Festival" );
        Todolist t3 = new Todolist(u2,"The FuturePresident" );
        Todolist t4 = new Todolist(u2,"Reptile Expo" );

        Item i1 = new Item("step1", "github", new Date(1995-11-02),"once", t1);
        Item i2 = new Item("What I Need","Clothes,soap,Pasta", new Date(2020-12-02),"Bi-weekly", t2);
        Item i3 = new Item("My Weekly Goals","Vision Board", new Date(2020-1-21),"Weekly", t3);
        Item i4 = new Item("What I Need","Tickets", new Date(2020-9-31),"Bi-weekly", t4);

        //itemrepos.save(i1);

        t1.getItems().add(i1);
        t2.getItems().add(i2);
        t3.getItems().add(i3);
        t4.getItems().add(i4);

        /*
        t1 = todolistService.saveInitial(t1);
        t2 = todolistService.saveInitial(t2);
        t3 = todolistService.saveInitial(t3);
        t4 = todolistService.saveInitial(t4);
        */

        //System.out.println("Checking for user id for u1: " + u1.getUserid());

        u1.getTodolists().add(t1);
        u2.getTodolists().add(t2);
        u2.getTodolists().add(t3);
        u2.getTodolists().add(t4);

        //System.out.println("Checking if lists exists for u2: " + u2.getTodolists().size());

        u1 = userService.save(u1);
        u2 = userService.save(u2);
        u3 = userService.save(u3);
        u4 = userService.save(u4);


        System.out.println("Checking again on u1 id: " + u1.getUserid());

        //System.out.println("t2 id is: " + t2.getTodoid() + " and it's user's id is somewhere");



        //todolistService.saveSeedTodo(t1);



        /*
        i1.getStates().add(new Itemstate(i1, s1)); // Set default state for item 1 to "In progress"
        i2.getStates().add(new Itemstate(i2, s1)); // Set default state for item 2 to "In progress"
        i3.getStates().add(new Itemstate(i3, s1)); // Set default state for item 3 to "In progress"
        i4.getStates().add(new Itemstate(i4, s1)); // Set default state for item 4 to "In progress"

        t1.getItems().add(i1);
        t2.getItems().add(i2);
        t3.getItems().add(i3);
        t4.getItems().add(i4);
        */

        /*
        itemService.saveItem(t1.getTodoid(), i1); // I may be overthinking this, so this line may/may not need to be commented out
        itemService.saveItem(t2.getTodoid(), i2); // I may be overthinking this, so this line may/may not need to be commented out
        itemService.saveItem(t3.getTodoid(), i3); // I may be overthinking this, so this line may/may not need to be commented out
        itemService.saveItem(t4.getTodoid(), i4); // I may be overthinking this, so this line may/may not need to be commented out
        */


        /*
        todolistService.saveList(u1.getUserid(), t1.getTitle()); // I may be overthinking this, so this line may/may not need to be commented out
        todolistService.saveList(u2.getUserid(), t2.getTitle()); // I may be overthinking this, so this line may/may not need to be commented out
        todolistService.saveList(u2.getUserid(), t3.getTitle()); // I may be overthinking this, so this line may/may not need to be commented out
        todolistService.saveList(u2.getUserid(), t4.getTitle()); // I may be overthinking this, so this line may/may not need to be commented out
        */


    }
}
