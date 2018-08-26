package com.example.roomrxjava.viewmodel;


import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.roomrxjava.local.ItemEntity;
import com.example.roomrxjava.repository.LocalRepository;
import com.example.roomrxjava.repository.RemoteRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ItemListViewModel extends ViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;
    private CompositeDisposable compositeDisposable;

    @Inject
    public ItemListViewModel(LocalRepository localRepo, RemoteRepository remoteRepo,
                             CompositeDisposable disposable){
        localRepository = localRepo;
        remoteRepository = remoteRepo;
        compositeDisposable = disposable;
    }

    public Flowable<List<ItemEntity>> getItems(){
        return localRepository.getItems();
    }

    public void getRemoteItems(){
        //add observable to CompositeDisposable so that it can be dispose when ViewModel is ready to be destroyed
        //Call retrofit client on background thread and update database with response from service using Room
        compositeDisposable.add(io.reactivex.Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .flatMap(i -> remoteRepository.getItems()).subscribeOn(Schedulers.io())
                .subscribe(items -> {
                    for(ItemEntity ce : items){
                        //database update
                        localRepository.insertItem(ce);
                    }
                }, throwable -> Log.e("ItemListActivity", "exception getting coupons", throwable)));

    }

    @Override
    public void onCleared(){
        //prevents memory leaks by disposing pending observable objects
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }
}
