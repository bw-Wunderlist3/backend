package com.wunderlist.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "states")
public class State extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statusid;

    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "state", allowSetters = true)
    private Set<Itemstate> items = new HashSet<>();

    public State() {}

    public State(String type, Set<Itemstate> items) {
        this.type = type;
        this.items = items;
    }

    public long getStatusid() {
        return statusid;
    }

    public void setStatusid(long statusid) {
        this.statusid = statusid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Itemstate> getItems() {
        return items;
    }

    public void setItems(Set<Itemstate> items) {
        this.items = items;
    }
}
