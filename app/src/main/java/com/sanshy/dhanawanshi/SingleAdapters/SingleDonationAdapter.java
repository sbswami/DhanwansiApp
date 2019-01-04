package com.sanshy.dhanawanshi.SingleAdapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.SingleItems.SingleDonationItem;

import java.util.ArrayList;

public class SingleDonationAdapter extends ArrayAdapter<SingleDonationItem> {
    ArrayList<SingleDonationItem> MyList = new ArrayList<>();
    Activity context;

    public SingleDonationAdapter(@NonNull Activity context, ArrayList<SingleDonationItem> MyList) {
        super(context, R.layout.single_partner_item, MyList);

        this.context = context;
        this.MyList = MyList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.single_donar_list, null, true);

        SingleDonationItem list = MyList.get(position);



        TextView Name = rowView.findViewById(R.id.single_name);
        TextView FatherName = rowView.findViewById(R.id.pita_ka_naam);
        TextView Cast = rowView.findViewById(R.id.gotr);
        TextView Village = rowView.findViewById(R.id.niwasi);
        TextView DonateMoney = rowView.findViewById(R.id.donation_value);

        ImageView Photo = rowView.findViewById(R.id.single_member_photo);

        try{
            Glide.with(context)
                    .load(list.getPhotoURL())
                    .into(Photo);
        }catch (Exception e){}

        Name.setText(list.getName());
        FatherName.setText(list.getFatherName());
        Cast.setText(list.getCast());
        Village.setText(list.getVillage());
        DonateMoney.setText(String.valueOf(list.getDonationMoney()));

        return rowView;
    }
}
