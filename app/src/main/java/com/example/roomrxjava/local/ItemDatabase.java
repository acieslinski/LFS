package com.example.roomrxjava.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ItemEntity.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
}
