package com.wunderlist.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemid;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Date duedate;

    // I'm not sure if I want this to be non-nullable, or simply default to "One-time"
    private String frequency;

    // Always defaults to whichever integer represents Pending
    // So as I'm building out the repositories and services, I'm noticing the boolean and int below are not needed for this project
    // But if feels like too much work to remove them from this entire project...
    @Transient
    public boolean hasvalueforstatus = false;
    private int status;

    @ManyToOne
    @JoinColumn(name = "todoid", nullable = false)
    @JsonIgnoreProperties(value = "items", allowSetters = true)
    private Todolist todolist;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "item", allowSetters = true)
    private Set<Itemstate> states = new HashSet<>();

    public Item() {}

    public Item(String name, String description, Date duedate, String frequency) {
        this.name = name;
        this.description = description;
        this.duedate = duedate;
        this.frequency = frequency;
    }

    // Ughhh... removing int status from entire project
    public Item(String name, String description, Date duedate, String frequency, Todolist todolist) {
        this.name = name;
        this.description = description;
        this.duedate = duedate;
        this.frequency = frequency;
        //this.status = status;
        this.todolist = todolist;
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        hasvalueforstatus = true;
        this.status = status;
    }

    public Todolist getTodolist() {
        return todolist;
    }

    public void setTodolist(Todolist todolist) {
        this.todolist = todolist;
    }

    public Set<Itemstate> getStates() {
        return states;
    }

    public void setStates(Set<Itemstate> states) {
        this.states = states;
    }
}
