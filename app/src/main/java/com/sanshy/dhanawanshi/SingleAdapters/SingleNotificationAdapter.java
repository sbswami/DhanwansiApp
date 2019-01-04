package com.sanshy.dhanawanshi.SingleAdapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleItems.SingleNotificationItem;

import java.util.ArrayList;

public class SingleNotificationAdapter extends ArrayAdapter<SingleNotificationItem> {
    ArrayList<SingleNotificationItem> MyList = new ArrayList<>();
    Activity context;

    public SingleNotificationAdapter(@NonNull Activity context, ArrayList<SingleNotificationItem> MyList) {
        super(context, R.layout.single_partner_item, MyList);

        this.context = context;
        this.MyList = MyList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_notification, null, true);

        SingleNotificationItem list = MyList.get(position);



        TextView Title = rowView.findViewById(R.id.notification_title);
        TextView Body = rowView.findViewById(R.id.notification_body);
        TextView DateTime = rowView.findViewById(R.id.notification_date_time);

        Title.setText(list.getNotificationTitle());
        Body.setText(list.getNotificationBody());
        DateTime.setText(ST.DateTimeString(list.getNotificationDate()));
        return rowView;
    }
}
