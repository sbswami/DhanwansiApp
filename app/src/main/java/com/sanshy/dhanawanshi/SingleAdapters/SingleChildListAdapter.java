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
import com.sanshy.dhanawanshi.SingleItems.SingleChildListItem;

import java.util.ArrayList;

public class SingleChildListAdapter extends ArrayAdapter<SingleChildListItem> {
    ArrayList<SingleChildListItem> MyList = new ArrayList<>();
    Activity context;

    public SingleChildListAdapter(@NonNull Activity context, ArrayList<SingleChildListItem> MyList) {
        super(context, R.layout.single_child_list_item, MyList);

        this.context = context;
        this.MyList = MyList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_child_list_item,null,true);

        SingleChildListItem list = MyList.get(position);

        TextView Name = rowView.findViewById(R.id.bche_ka_naam);
        TextView Gender = rowView.findViewById(R.id.bache_ka_ling);
        TextView Village = rowView.findViewById(R.id.bache_ka_ganv);

        Name.setText(list.getChildName());
        Gender.setText(list.isMale()?context.getString(R.string.purush):context.getString(R.string.mahila));
        Village.setText(list.getChildVillage());

        return rowView;
    }

}