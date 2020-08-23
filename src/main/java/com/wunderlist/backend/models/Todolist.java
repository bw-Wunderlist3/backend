package com.wunderlist.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todolists")
public class Todolist extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = "todolists", allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "todolist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "todolist", allowSetters = true)
    private List<Item> items = new ArrayList<>();

    public Todolist() {}

    public Todolist(String title, User user, List<Item> items) {
        this.title = title;
        this.user = user;
        this.items = items;
    }

    public long getTodoid() {
        return todoid;
    }

    public void setTodoid(long todoid) {
        this.todoid = todoid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
