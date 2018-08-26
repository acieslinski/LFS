package com.example.roomrxjava.repository;


import com.example.roomrxjava.local.ItemEntity;
import com.example.roomrxjava.remote.Api;

import java.util.List;

import io.reactivex.Observable;

public class RemoteRepositoryImpl implements RemoteRepository {

    private Api apiAdapter;

    public RemoteRepositoryImpl(Api cpnClient){
        apiAdapter = cpnClient;
    }

    public Observable<List<ItemEntity>> getItems(){
        return apiAdapter.getItems();
    }
}
