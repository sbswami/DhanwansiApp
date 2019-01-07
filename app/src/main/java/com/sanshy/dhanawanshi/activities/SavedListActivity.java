package com.sanshy.dhanawanshi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.SingleAdapters.SingleListAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleListItem;

import java.util.ArrayList;

public class SavedListActivity extends AppCompatActivity {
    RecyclerView ListRecyclerView;

    ArrayList<SingleListItem> ItemList = new ArrayList<>();
    SingleListAdapter ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);

        Intent intent = getIntent();
        if (intent!=null){

        }

        ListRecyclerView = findViewById(R.id.list_recycler_view);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ListRecyclerView.setLayoutManager(layoutManager);
        ListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ListAdapter = new SingleListAdapter(ItemList, new SingleListAdapter.MyAdapterListener() {
            @Override
            public void PersonListener(View v, int position) {
                Intent intent = new Intent(SavedListActivity.this,ViewSingle.class);

                startActivity(intent);
            }
        });
        ListRecyclerView.setAdapter(ListAdapter);


        addProductList();

    }

    private void addProductList() {
        SingleListItem item = new SingleListItem(
                "",
                "संजय स्वामी",
                "सुरेन्द्र स्वामी",
                "सरेवा",
                "आडसर",
                ""
        );
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);

        ListAdapter.notifyDataSetChanged();

    }
}
