package com.sanshy.dhanawanshi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.SingleAdapters.SingleNotificationAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleNotificationItem;

import java.util.ArrayList;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<SingleNotificationItem> notificationItemsList = new ArrayList<>();
    SingleNotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        listView = findViewById(R.id.list_view);

        adapter = new SingleNotificationAdapter(this, notificationItemsList);
        listView.setAdapter(adapter);


        addData();
    }

    private void addData() {
        SingleNotificationItem item = new SingleNotificationItem(
                "aa",
                "जरुरी सुचना",
                "जल्दी से जल्दी आप सभी को यह एप्लीकेशन भेजे ताकि समाज को जोड़ सकें|",
                new Date()
        );
        notificationItemsList.add(item);
        notificationItemsList.add(item);
        notificationItemsList.add(item);
        notificationItemsList.add(item);
        notificationItemsList.add(item);
        notificationItemsList.add(item);

        adapter.notifyDataSetChanged();
    }
}
