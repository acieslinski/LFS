package com.example.roomrxjava.repository;

import com.example.roomrxjava.local.ItemDao;
import com.example.roomrxjava.local.ItemEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalRepositoryImpl implements LocalRepository {
    private ItemDao itemDao;

    public LocalRepositoryImpl(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
    public Flowable<List<ItemEntity>> getItems() {
        return itemDao.getItems();
    }

    @Override
    public Single<ItemEntity> getItemById(long id) {
        return itemDao.getItemById(id);
    }

    public void insertItem(ItemEntity item) {
        itemDao.insertItem(item);
    }
}
