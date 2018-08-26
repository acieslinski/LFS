package com.example.roomrxjava.remote;

import com.example.roomrxjava.local.ItemEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {
    @GET("users")
    Observable<List<ItemEntity>> getItems();
}
