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
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleAdapters.SingleListAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleListItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SearchByNameActivity extends AppCompatActivity {

    RecyclerView ListRecyclerView;
    TextView NoFoundShow;

    ArrayList<SingleListItem> ItemList = new ArrayList<>();
    SingleListAdapter ListAdapter;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_name);

        ListRecyclerView = findViewById(R.id.list_recycler_view);
        NoFoundShow = findViewById(R.id.not_found);
        searchView = findViewById(R.id.search_home);


        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ListRecyclerView.setLayoutManager(layoutManager);
        ListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ListAdapter = new SingleListAdapter(ItemList, new SingleListAdapter.MyAdapterListener() {
            @Override
            public void PersonListener(View v, int position) {
                Intent intent = new Intent(SearchByNameActivity.this,ViewSingle.class);

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
                    ST.ShowProgress(SearchByNameActivity.this);
                    SearchDataMethod();
                    Toast.makeText(SearchByNameActivity.this, ""+totalItemCount, Toast.LENGTH_SHORT).show();
                }

            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemList.clear();
                SearchDataMethod();
            }
        });
        SearchDataMethod();
    }

    DocumentSnapshot lastDocumentSnapshot = null;


    public void SearchDataMethod(){
        if (searchView.getQuery().toString().isEmpty()){
            ST.FillInput(SearchByNameActivity.this);
            return;
        }

        Calendar nowMin = Calendar.getInstance();
        nowMin.add(Calendar.YEAR, -(int)MinAgeD);
        Date MinDate = nowMin.getTime();

        Calendar nowMax = Calendar.getInstance();
        nowMax.add(Calendar.YEAR, -(int)MaxAgeD);
        Date MaxDate = nowMax.getTime();

        if (lastDocumentSnapshot!=null){
            if (StateSt.isEmpty()){
                if (DistrictSt.isEmpty()){
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
                else{
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
            }
            else{
                if (DistrictSt.isEmpty()){
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
                else{
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .startAfter(lastDocumentSnapshot).get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
            }
        }
        else{
            if (StateSt.isEmpty()){
                if (DistrictSt.isEmpty()){
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
                else{
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
            }
            else{
                if (DistrictSt.isEmpty()){
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
                else{
                    if (TahsilSt.isEmpty()){
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                    else{
                        if (VillageSt.isEmpty()){
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                        else{
                            if (CastSt.isEmpty()&&EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (CastSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else if (EducationSt.isEmpty()){
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                            else {
                                ST.SearchDataList
                                        .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                                        .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                                        .whereLessThanOrEqualTo(ST.DOB,MinDate)
                                        .whereEqualTo(ST.EDUCATION_STATUS,EducationSt)
                                        .whereEqualTo(ST.CAST,CastSt)
                                        .whereEqualTo(ST.VILLAGE,VillageSt)
                                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                                        .whereEqualTo(ST.STATE,StateSt)
                                        .limit(10)
                                        .get()
                                        .addOnSuccessListener(SuccessListener)
                                        .addOnFailureListener(FailureListener);
                            }
                        }
                    }
                }
            }
        }

        

        ST.SearchDataList
                .whereEqualTo(ST.MEMBER_NAME,searchView.getQuery().toString())
                .whereGreaterThanOrEqualTo(ST.DOB,MaxDate)
                .whereLessThanOrEqualTo(ST.DOB,MinDate)
                .limit(10)
                .get()
                .addOnSuccessListener(SuccessListener)
                .addOnFailureListener(FailureListener);

    }


    OnSuccessListener<QuerySnapshot> SuccessListener = new OnSuccessListener<QuerySnapshot>() {
        @Override
        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                SingleListItem item = new SingleListItem(
                        documentSnapshot.contains(ST.MEMBER_ID)?(String) documentSnapshot.get(ST.MEMBER_ID):"",
                        documentSnapshot.contains(ST.MEMBER_NAME)?(String) documentSnapshot.get(ST.MEMBER_NAME):"",
                        documentSnapshot.contains(ST.FATHER_NAME)?(String) documentSnapshot.get(ST.FATHER_NAME):"",
                        documentSnapshot.contains(ST.CAST)?(String) documentSnapshot.get(ST.CAST):"",
                        documentSnapshot.contains(ST.VILLAGE)?(String) documentSnapshot.get(ST.VILLAGE):"",
                        documentSnapshot.contains(ST.PROFILE_PIC)?(String) documentSnapshot.get(ST.PROFILE_PIC):""
                );
                ItemList.add(item);
                lastDocumentSnapshot = documentSnapshot;
            }
            if (queryDocumentSnapshots.isEmpty()){
                lastDocumentSnapshot = null;
            }
            isLoading = false;
            ListAdapter.notifyDataSetChanged();
            ST.HideProgress();
        }
    };

    OnFailureListener FailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            ST.HideProgress();
        }
    };



    boolean isLoading = false;
    int totalItemCount,lastVisibleItem;
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

        StateSt = "";
        DistrictSt = "";
        TahsilSt = "";
        VillageSt = "";
        CastSt = "";
        EducationSt = "";
        MinAgeD = 0;
        MaxAgeD = 150;

        builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View FilterView = inflater.inflate(R.layout.filter_name_search,null);

        Cast = FilterView.findViewById(R.id.filter_gotr);
        Education = FilterView.findViewById(R.id.filter_education);
        MinAge = FilterView.findViewById(R.id.filter_min_age);
        MaxAge = FilterView.findViewById(R.id.filter_max_age);
        State = FilterView.findViewById(R.id.m_state);
        District = FilterView.findViewById(R.id.m_district);
        Tahsil = FilterView.findViewById(R.id.m_tahsil);
        Village = FilterView.findViewById(R.id.m_village);

        District.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (State.getText().toString().isEmpty()){
                        ST.FillInput(SearchByNameActivity.this);
                        District.clearFocus();
                    }
                    else{
                        District.requestFocus();
                    }
                }
            }
        });
        Tahsil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (District.getText().toString().isEmpty()){
                        ST.FillInput(SearchByNameActivity.this);
                        Tahsil.clearFocus();
                    }
                    else{
                        Tahsil.requestFocus();
                    }
                }
            }
        });
        Village.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (Tahsil.getText().toString().isEmpty()){
                        ST.FillInput(SearchByNameActivity.this);
                        Village.clearFocus();
                    }
                    else{
                        Village.requestFocus();
                    }
                }
            }
        });
        builder.setView(FilterView);
        builder.show();
    }


    String StateSt,DistrictSt,TahsilSt,VillageSt;
    String CastSt,EducationSt;
    double MinAgeD,MaxAgeD;
    public void FilterNameSearch(View view){

        ItemList.clear();

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

        SearchDataMethod();

        builder.dismiss();
    }

}
