package com.example.roomrxjava.di;

import com.example.roomrxjava.ItemListActivity;

import javax.inject.Singleton;

import dagger.Component;

@ItemScope
@Singleton
@Component(modules = {AppModule.class, ItemModule.class})
public interface AppComponent {
    void inject(ItemListActivity itemListActivity);
}
