package com.sanshy.dhanawanshi.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleAdapters.SingleListAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleListItem;

import java.util.ArrayList;

public class SimpleListActivity extends AppCompatActivity {

    RecyclerView ListRecyclerView;

    ArrayList<SingleListItem> ItemList = new ArrayList<>();
    SingleListAdapter ListAdapter;

    String CastSt = "",VillageSt = "", TahsilSt  = "",DistrictSt = "",StateSt = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        Intent intent = getIntent();
        if (intent!=null){
            CastSt = intent.getStringExtra(ST.CAST)!=null?intent.getStringExtra(ST.CAST):"";
            VillageSt = intent.getStringExtra(ST.VILLAGE)!=null?intent.getStringExtra(ST.VILLAGE):"";
            TahsilSt = intent.getStringExtra(ST.TAHSIL)!=null?intent.getStringExtra(ST.TAHSIL):"";
            DistrictSt = intent.getStringExtra(ST.DISTRICT)!=null?intent.getStringExtra(ST.DISTRICT):"";
            StateSt = intent.getStringExtra(ST.STATE)!=null?intent.getStringExtra(ST.STATE):"";
        }

        ListRecyclerView = findViewById(R.id.list_recycler_view);


        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ListRecyclerView.setLayoutManager(layoutManager);
        ListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ListAdapter = new SingleListAdapter(ItemList, new SingleListAdapter.MyAdapterListener() {
            @Override
            public void PersonListener(View v, int position) {
                Intent intent = new Intent(SimpleListActivity.this,ViewSingle.class);
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
                    SearchDataMethod();
                    Toast.makeText(SimpleListActivity.this, ""+totalItemCount, Toast.LENGTH_SHORT).show();
                }

            }
        });
        SearchDataMethod();

    }
    DocumentSnapshot lastDocumentSnapshot = null;
    boolean isLastDoc = false;
    boolean isLoading = false;
    int totalItemCount,lastVisibleItem;

    private void SearchDataMethod() {
        if (lastDocumentSnapshot!=null){
            ST.ShowProgress(this);
            if (!CastSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.CAST,CastSt)
                        .limit(10)
                        .startAfter(lastDocumentSnapshot).get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!VillageSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.VILLAGE,VillageSt)
                        .limit(10)
                        .startAfter(lastDocumentSnapshot).get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!TahsilSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                        .limit(10)
                        .startAfter(lastDocumentSnapshot).get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!DistrictSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                        .limit(10)
                        .startAfter(lastDocumentSnapshot).get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!StateSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.STATE,StateSt)
                        .limit(10)
                        .startAfter(lastDocumentSnapshot).get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
        }
        else if (!isLastDoc){
            ST.ShowProgress(this);
            if (!CastSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.CAST,CastSt)
                        .limit(10)
                        .get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!VillageSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.VILLAGE,VillageSt)
                        .limit(10)
                        .get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!TahsilSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.TAHSIL,TahsilSt)
                        .limit(10)
                        .get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!DistrictSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.DISTRICT,DistrictSt)
                        .limit(10)
                        .get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
            else if (!StateSt.isEmpty()){
                ST.CompleteDataList
                        .whereEqualTo(ST.STATE,StateSt)
                        .limit(10)
                        .get()
                        .addOnSuccessListener(SuccessListener)
                        .addOnFailureListener(FailureListener);
            }
        }
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
                isLastDoc = false;
            }
            if (queryDocumentSnapshots.isEmpty()){
                lastDocumentSnapshot = null;
                isLastDoc = true;
            }
            ListRecyclerView.setVisibility(View.VISIBLE);
            isLoading = false;
            ListAdapter.notifyDataSetChanged();
            ST.HideProgress();
        }
    };

    OnFailureListener FailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            ST.HideProgress();
            ST.ShowDialog(SimpleListActivity.this,e.toString());
        }
    };

}
