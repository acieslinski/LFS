package com.example.roomrxjava.repository;

import com.example.roomrxjava.local.ItemEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LocalRepository {
    Flowable<List<ItemEntity>> getItems();
    Single<ItemEntity> getItemById(long id);
    void insertItem(ItemEntity itemEntity);
}
