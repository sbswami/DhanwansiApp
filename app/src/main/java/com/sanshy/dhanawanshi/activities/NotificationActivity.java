package com.sanshy.dhanawanshi.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
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
        ST.ShowProgress(this);
        ST.NotificationList
                .orderBy(ST.DATE, Query.Direction.DESCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    SingleNotificationItem item = new SingleNotificationItem(
                            documentSnapshot.contains(ST.ID)?documentSnapshot.get(ST.ID).toString():"000",
                            documentSnapshot.contains(ST.NOTIFICATION_TITLE)?documentSnapshot.get(ST.NOTIFICATION_TITLE).toString():"000",
                            documentSnapshot.contains(ST.NOTIFICATION_BODY)?documentSnapshot.get(ST.NOTIFICATION_BODY).toString():"000",
                            documentSnapshot.contains(ST.DATE)?(Date)documentSnapshot.get(ST.DATE):new Date()
                    );
                    notificationItemsList.add(item);
                }

                ST.HideProgress();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
