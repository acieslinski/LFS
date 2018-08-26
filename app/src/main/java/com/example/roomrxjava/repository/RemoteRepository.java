package com.example.roomrxjava.repository;


import com.example.roomrxjava.local.ItemEntity;

import java.util.List;

import io.reactivex.Observable;

public interface RemoteRepository {

    Observable<List<ItemEntity>> getItems();
}
