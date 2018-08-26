package com.example.roomrxjava.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.roomrxjava.local.ItemDao;
import com.example.roomrxjava.local.ItemDatabase;
import com.example.roomrxjava.remote.Api;
import com.example.roomrxjava.repository.LocalRepository;
import com.example.roomrxjava.repository.LocalRepositoryImpl;
import com.example.roomrxjava.repository.RemoteRepository;
import com.example.roomrxjava.repository.RemoteRepositoryImpl;
import com.example.roomrxjava.viewmodel.ItemListViewModel;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ItemModule {
    @ItemScope
    @Provides
    public ItemDao getItemDao(ItemDatabase itemDatabase) {
        return itemDatabase.itemDao();
    }

    @ItemScope
    @Provides
    public ItemDatabase getItemDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), ItemDatabase.class,
                "items.db").build();
    }

    @ItemScope
    @Provides
    public LocalRepository getLocalRepo(ItemDao itemDao) {
        return new LocalRepositoryImpl(itemDao);
    }

    @ItemScope
    @Provides
    @ItemListActivityDisposable
    public CompositeDisposable getILACompositeDisposable() {
        return new CompositeDisposable();
    }

    @ItemScope
    @Provides
    @ItemActivityDisposable
    public CompositeDisposable getIACompositeDisposable() {
        return new CompositeDisposable();
    }

    @ItemScope
    @Provides
    public RemoteRepository getRemoteRepo(Api apiAdapter) {
        return new RemoteRepositoryImpl(apiAdapter);
    }

    @ItemScope
    @Provides
    public ItemListViewModel getItemListViewModel(
            LocalRepository localRepo, RemoteRepository remoteRepo,
            @ItemListActivityDisposable CompositeDisposable disposable) {
        return new ItemListViewModel(localRepo, remoteRepo, disposable);
    }
}
