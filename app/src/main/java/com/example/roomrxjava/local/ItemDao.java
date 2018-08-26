package com.example.roomrxjava.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM ItemEntity")
    Flowable<List<ItemEntity>> getItems();

    @Query("SELECT * FROM ItemEntity WHERE id = :id")
    Single<ItemEntity> getItemById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ItemEntity item);
}
