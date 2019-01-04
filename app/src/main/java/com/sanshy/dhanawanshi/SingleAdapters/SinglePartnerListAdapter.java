package com.sanshy.dhanawanshi.SingleAdapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleItems.SingleChildListItem;
import com.sanshy.dhanawanshi.SingleItems.SinglePartnerListItem;

import java.util.ArrayList;

public class SinglePartnerListAdapter extends ArrayAdapter<SinglePartnerListItem> {
    ArrayList<SinglePartnerListItem> MyList = new ArrayList<>();
    Activity context;

    ListView listView;
    SingleChildListAdapter childListAdapter;

    public SinglePartnerListAdapter(@NonNull Activity context, ArrayList<SinglePartnerListItem> MyList) {
        super(context, R.layout.single_partner_item, MyList);

        this.context = context;
        this.MyList = MyList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_partner_item, null, true);

        SinglePartnerListItem list = MyList.get(position);



        TextView Name = rowView.findViewById(R.id.jiwansathi_ka_naam);
        TextView Cast = rowView.findViewById(R.id.jiwansathi_ka_gotr);
        TextView Village = rowView.findViewById(R.id.jiwansathi_ka_ganv);
        TextView DateView = rowView.findViewById(R.id.sadi_tarikh);
        listView = rowView.findViewById(R.id.child_list);
        childListAdapter = new SingleChildListAdapter(context,list.getChildListItems());
        listView.setAdapter(childListAdapter);

        Name.setText(list.getPartnerName());
        Cast.setText(list.getPartnerCast());
        Village.setText(list.getPartnerGanv());
        DateView.setText(ST.DateToString(list.getDate()));



        return rowView;
    }
}

