package com.wunderlist.backend.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "itemstates")
public class Itemstate extends Auditable implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "itemid")
    private Item item;

    @Id
    @ManyToOne
    @JoinColumn(name = "statusid")
    private State state;

    public Itemstate() {}

    public Itemstate(Item item, State state) {
        this.item = item;
        this.state = state;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Itemstate)) return false;

        Itemstate that = (Itemstate) o;

        return ((item == null) ? 0 : item.getItemid()) == ((that.item == null) ? 0 : that.item.getItemid()) &&
                ((state == null) ? 0 : state.getStatusid()) == ((that.state == null) ? 0 : that.state.getStatusid());
    }

    @Override
    public int hashCode() {
        // return Objects.hash(item.getItemid(), state.getStatusid());
        return 37;
    }
}
