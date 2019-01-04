package com.sanshy.dhanawanshi.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleAdapters.SingleChildListAdapter;
import com.sanshy.dhanawanshi.SingleAdapters.SinglePartnerListAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleChildListItem;
import com.sanshy.dhanawanshi.SingleItems.SinglePartnerListItem;

import java.util.ArrayList;

public class ViewSingle extends AppCompatActivity {

    String Mid;

    ImageView ProfilePhoto;
    TextView Name,DOB,DOD,Gender,Mobile,Cast,Work,MarriageStatus,Village,Tahsil,District,State,CPName,CPVillage,CPCast,CPMDate,FName,FVillage,MName,MVillage,MCast;
    CardView PastPartnerCard,CurrentPartnerCard;

    ListView PastPartnersList,CurrentPartnerChildList;


    SinglePartnerListAdapter PastPartnerListAdapter;
    SingleChildListAdapter CurrentPartnerChildListAdapter;

    ArrayList<SinglePartnerListItem> pastPartnerList = new ArrayList<>();
    ArrayList<SingleChildListItem> childListItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single);

        Intent intent = getIntent();
        Mid = intent.getStringExtra(ST.MEMBER_ID);

        try{
            Toast.makeText(this, ""+intent.getData().toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){}

        ProfilePhoto = findViewById(R.id.profile_image);
        Name = findViewById(R.id.view_name);
        DOB = findViewById(R.id.view_dob);
        DOD = findViewById(R.id.view_death_date);
        Gender = findViewById(R.id.view_gender);
        Mobile = findViewById(R.id.view_mobile_no);
        Cast = findViewById(R.id.view_gotr);
        Work = findViewById(R.id.view_work);
        MarriageStatus = findViewById(R.id.view_marriage_status);
        Village = findViewById(R.id.view_ganv);
        Tahsil = findViewById(R.id.view_tahsil);
        District = findViewById(R.id.view_jila);
        State = findViewById(R.id.view_state);
        CPName = findViewById(R.id.view_current_partner_name);
        CPVillage = findViewById(R.id.view_current_partner_village);
        CPCast = findViewById(R.id.view_current_partner_cast);
        CPMDate = findViewById(R.id.view_current_partner_marry_date);
        FName = findViewById(R.id.view_pita_ka_naam);
        FVillage = findViewById(R.id.view_pita_ka_ganv);
        MName = findViewById(R.id.view_mata_ka_naam);
        MVillage = findViewById(R.id.view_mata_ka_ganv);
        MCast = findViewById(R.id.view_mata_ki_gotr);
        PastPartnerCard = findViewById(R.id.view_past_partner_card);
        CurrentPartnerCard = findViewById(R.id.view_current_partner_card);

        PastPartnersList = findViewById(R.id.view_past_partner_list);
        CurrentPartnerChildList = findViewById(R.id.view_current_partner_child);

        //Initialize Adapters for List
        PastPartnerListAdapter = new SinglePartnerListAdapter(this,pastPartnerList);
        CurrentPartnerChildListAdapter = new SingleChildListAdapter(this,childListItems);

        //Set Adapters
        PastPartnersList.setAdapter(PastPartnerListAdapter);
        CurrentPartnerChildList.setAdapter(CurrentPartnerChildListAdapter);

        CurrentPartnerChildList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()){
                    case R.id.bche_ka_naam:
                        break;
                    case R.id.bache_ka_ling:
                        break;
                    case R.id.bache_ka_ganv:
                        break;
                    default:
                }
            }
        });

        PastPartnersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (view.getId()){
                    case R.id.jiwansathi_ka_naam:
                        break;
                    case R.id.jiwansathi_ka_gotr:
                        break;
                    case R.id.jiwansathi_ka_ganv:
                        break;
                    case R.id.sadi_tarikh:
                        break;
                    case R.id.child_list:
                        break;
                    default:
                }
            }
        });



    }


    public void MemberCast(View view){

    }
    public void VillageClick(View view){

    }
    public void TahsilClick(View view){

    }
    public void DistrictClick(View view){

    }
    public void StateClick(View view){

    }
    public void CPNameClick(View view){

    }
    public void CPVillageClick(View view){

    }
    public void CPCastClick(View view){

    }
    public void FnameClick(View view){

    }
    public void FVilalgeClick(View view){

    }
    public void MNameClick(View view){

    }
    public void MCastClick(View view){

    }
    public void MVillageClick(View view){

    }

    public void EditMember(View view){
        startActivity(new Intent(this,EditActivity.class));
    }
    public void SupportComplain(View view){
        startActivity(new Intent(this, SupportAndComplainActivity.class));
    }
    public void ShareIt(View view){

    }
    public void SaveToList(View view){

    }
}
