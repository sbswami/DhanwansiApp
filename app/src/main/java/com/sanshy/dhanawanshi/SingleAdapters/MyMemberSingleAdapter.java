package com.sanshy.dhanawanshi.SingleAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.SingleItems.MyMemberSingleItem;

import java.util.ArrayList;

public class MyMemberSingleAdapter extends RecyclerView.Adapter<MyMemberSingleAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<MyMemberSingleItem> ProductList = new ArrayList<>();

    public MyMemberSingleAdapter.MyAdapterListener onClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_member_single_item, viewGroup, false);
        mContext = viewGroup.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        MyMemberSingleItem SingleItem = ProductList.get(i);
        myViewHolder.Name.setText(SingleItem.getName());
        myViewHolder.FatherName.setText(String.valueOf(SingleItem.getFatherName()));
        myViewHolder.Cast.setText(String.valueOf(SingleItem.getCast()));
        myViewHolder.Location.setText(SingleItem.getLocation());
        myViewHolder.EditingKey.setText(SingleItem.getEditingKey());
        try{
            Glide.with(mContext)
                    .load(SingleItem.getPhotoURL())
                    .into(myViewHolder.Pic);
        }catch (Exception ex){

        }

    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView Pic;
        TextView Name,FatherName,Cast,Location,EditingKey;
        CardView SingleCard;

        public MyViewHolder(@NonNull View myListView) {
            super(myListView);

            Pic = myListView.findViewById(R.id.single_member_photo);
            Name = myListView.findViewById(R.id.single_name);
            FatherName = myListView.findViewById(R.id.pita_ka_naam);
            Cast = myListView.findViewById(R.id.gotr);
            Location = myListView.findViewById(R.id.niwasi);
            EditingKey = myListView.findViewById(R.id.editing_key);
            SingleCard = myListView.findViewById(R.id.single_card_view);
            SingleCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.PersonListener(v, getAdapterPosition());
                }
            });
        }
    }


    public interface MyAdapterListener {
        void PersonListener(View v, int position);
    }

    public MyMemberSingleAdapter(ArrayList<MyMemberSingleItem> productList, MyAdapterListener onClickListener){
        this.ProductList = productList;
        this.onClickListener = onClickListener;
    }



}
