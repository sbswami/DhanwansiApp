package com.sanshy.dhanawanshi.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleAdapters.SingleListAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleListItem;

import java.util.ArrayList;

public class FindByLocationActivity extends AppCompatActivity {

    RecyclerView ListRecyclerView;
    Button NoFoundShow;

    ArrayList<SingleListItem> ItemList = new ArrayList<>();
    SingleListAdapter ListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_location);

        ListRecyclerView = findViewById(R.id.list_recycler_view);
        NoFoundShow = findViewById(R.id.not_found);


        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ListRecyclerView.setLayoutManager(layoutManager);
        ListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ListAdapter = new SingleListAdapter(ItemList, new SingleListAdapter.MyAdapterListener() {
            @Override
            public void ProductListener(View v, int position) {
                Intent intent = new Intent(FindByLocationActivity.this,ViewSingle.class);

                intent.putExtra(ST.MEMBER_ID,ItemList.get(position).getId());

                startActivity(intent);
            }
        });
        ListRecyclerView.setAdapter(ListAdapter);


        ListRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                if (!isLoading&&totalItemCount<=(lastVisibleItem+2)){
                    isLoading = true;
                    addProductList();
                    Toast.makeText(FindByLocationActivity.this, ""+totalItemCount, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    boolean isLoading = false;
    int totalItemCount,lastVisibleItem;

    private void addProductList() {
        SingleListItem item = new SingleListItem(
                "A",
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

        ListRecyclerView.setVisibility(View.VISIBLE);
        isLoading = false;
        ListAdapter.notifyDataSetChanged();
    }


/*
Ganv
Gotr
Age

Education
 */


    AutoCompleteTextView State,District,Tahsil,Village;

    AutoCompleteTextView Cast,Education;
    EditText MinAge,MaxAge;
    AlertDialog builder;
    public void FilterBt(View view){
        builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View FilterView = inflater.inflate(R.layout.filter_find_location,null);

        Cast = FilterView.findViewById(R.id.filter_gotr);
        Education = FilterView.findViewById(R.id.filter_education);
        MinAge = FilterView.findViewById(R.id.filter_min_age);
        MaxAge = FilterView.findViewById(R.id.filter_max_age);
        State = FilterView.findViewById(R.id.m_state);
        District = FilterView.findViewById(R.id.m_district);
        Tahsil = FilterView.findViewById(R.id.m_tahsil);
        Village = FilterView.findViewById(R.id.m_village);

        District.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (State.getText().toString().isEmpty()){

                }
            }
        });
        Tahsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (State.getText().toString().isEmpty()){

                }
                else if (District.getText().toString().isEmpty()){

                }
            }
        });
        Village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (State.getText().toString().isEmpty()){

                }
                else if (District.getText().toString().isEmpty()){

                }
                else if (Village.getText().toString().isEmpty()){

                }
            }
        });

        builder.setView(FilterView);
        builder.show();
    }


    String StateSt,DistrictSt,TahsilSt,VillageSt;
    String CastSt,EducationSt;
    double MinAgeD,MaxAgeD;
    public void FilterFindLocation(View view){

        addProductList();
        boolean c,l,e,a;

        CastSt = Cast.getText().toString();
        StateSt = State.getText().toString();
        DistrictSt = District.getText().toString();
        TahsilSt = Tahsil.getText().toString();
        VillageSt = Village.getText().toString();
        EducationSt = Education.getText().toString();



        a = MinAge.getText().toString().isEmpty()&&MaxAge.getText().toString().isEmpty();

        MinAgeD = MinAge.getText().toString().isEmpty()?0:Double.parseDouble(MinAge.getText().toString());
        MaxAgeD = MaxAge.getText().toString().isEmpty()?110:Double.parseDouble(MaxAge.getText().toString());


        c = CastSt.isEmpty();
        l = StateSt.isEmpty()&&DistrictSt.isEmpty()&&TahsilSt.isEmpty()&&VillageSt.isEmpty();
        e = EducationSt.isEmpty();


        if (a){
            if (c){
                if (l&&e){

                }
                else if (l){

                }
                else if (e){

                }
                else{

                }
            }
            else{
                if (l&&e){

                }
                else if (l){

                }
                else if (e){

                }
                else{

                }
            }
        }
        else{
            if (c){
                if (l&&e){

                }
                else if (l){

                }
                else if (e){

                }
                else{

                }
            }
            else{
                if (l&&e){

                }
                else if (l){

                }
                else if (e){

                }
                else{

                }
            }
        }

        builder.dismiss();

    }
}
