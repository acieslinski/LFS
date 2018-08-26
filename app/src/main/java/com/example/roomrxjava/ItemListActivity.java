package com.example.roomrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.roomrxjava.di.ItemListActivityDisposable;
import com.example.roomrxjava.viewmodel.ItemListViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ItemListActivity extends AppCompatActivity {

    @Inject
    @ItemListActivityDisposable
    CompositeDisposable compositeDisposable;

    @Inject
    ItemListViewModel itemListViewModel;

    private ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Application.getComponent(getApplicationContext()).inject(this);

        //call retrofit service to get latest data and update database
        //runs in the background thread
        itemListViewModel.getRemoteItems();

        //listview to show list of data items from database
        itemListView = findViewById(R.id.lv);
    }

    @Override
    protected void onStart() {
        super.onStart();

        compositeDisposable.add(itemListViewModel.getItems()
                .flatMap(itemEntities -> io.reactivex.Observable.fromIterable(itemEntities)
                        .flatMap(itemEntity -> io.reactivex.Observable.just(itemEntity.getName()))
                        .toList()
                        .toFlowable())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    if (items != null) {
                        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1, items);

                        itemListView.setAdapter(itemsAdapter);
                    }
                }, throwable -> Log.e("ItemListActivity", "exception getting coupons")));
    }

    @Override
    protected void onDestroy() {
        //dispose subscriptions
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }

        super.onDestroy();
    }
}
