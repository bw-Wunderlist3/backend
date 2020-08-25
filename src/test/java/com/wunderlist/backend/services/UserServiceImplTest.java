package com.wunderlist.backend.services;

import com.wunderlist.backend.BackendApplication;
import com.wunderlist.backend.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<User> myList = userService.findAll();
        for (User u : myList)
        {
            System.out.println(u.getUserid() + " " + u.getUsername());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUserById() {
        assertEquals("admin", userService.findUserById(4).getUsername());
    }

    @Test
    public void save() {
        User u = new User("Web Dev", "Internetawsome","icreatewebsites@gmail.com","Jonna","Ark");
       userService.save(u);
       assertEquals(5,userService.findAll().size());
    }

    @Test
    public void update() {
        User updateU = new User("Web Dev", "InternetisEvil","Ihatebugs@gmail.com","Jonna","Ark");
        assertEquals("web dev", userService.findUserById(16).getUsername());

    }

    @Test
    public void zdelete() {
        userService.delete(4);
        assertEquals(4, userService.findAll().size());
    }
}