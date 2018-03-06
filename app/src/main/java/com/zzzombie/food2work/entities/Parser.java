package com.zzzombie.food2work.entities;

import org.json.JSONException;

public interface Parser<T> {
    T parse(String s) throws JSONException;
}
