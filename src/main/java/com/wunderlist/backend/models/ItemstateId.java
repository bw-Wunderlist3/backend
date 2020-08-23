package com.wunderlist.backend.models;

import java.io.Serializable;

public class ItemstateId implements Serializable {
    private long item;

    private long state;

    public ItemstateId() {}

    // No other constructor needed

    public long getItem() {
        return item;
    }

    public void setItem(long item) {
        this.item = item;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ItemstateId that = (ItemstateId) o;

        return item == that.item && state == that.state;
    }

    @Override
    public int hashCode() {
        return 37;
    }
}
