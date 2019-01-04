package com.sanshy.dhanawanshi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.SingleAdapters.SingleDonationAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleDonationItem;

import java.util.ArrayList;

public class DonatorListActivity extends AppCompatActivity {


    ListView listView;
    ArrayList<SingleDonationItem> ItemList = new ArrayList<>();
    SingleDonationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donator_list);

        listView = findViewById(R.id.list_view_donation);

        adapter = new SingleDonationAdapter(this,ItemList);
        listView.setAdapter(adapter);

        addData();
    }

    private void addData() {
        SingleDonationItem item = new SingleDonationItem(
                "AA",
                "गोपालदास ",
                "",
                "किशनदास",
                "सारण",
                "बालोतरा",
                500
                );
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);
        ItemList.add(item);

        adapter.notifyDataSetChanged();
    }

    public void DonateUs(View view){

    }
}
