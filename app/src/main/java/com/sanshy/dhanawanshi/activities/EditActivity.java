package com.sanshy.dhanawanshi.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleAdapters.SingleChildListAdapter;
import com.sanshy.dhanawanshi.SingleAdapters.SinglePartnerListAdapter;
import com.sanshy.dhanawanshi.SingleAdapters.SuggestionAdapter;
import com.sanshy.dhanawanshi.SingleAdapters.SuggetionItem;
import com.sanshy.dhanawanshi.SingleItems.SingleChildListItem;
import com.sanshy.dhanawanshi.SingleItems.SinglePartnerListItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    public static final String CHILD_ID = "ChildId";
    public static final String CHILD_NAME = "ChildName";
    public static final String CHILD_VILLAGE = "ChildVillage";
    public static final String CHILD_IS_MALE = "ChildIsMale";
    public static final String PAST_PARTNER_ID = "PastPartnerId";
    public static final String PAST_PARTNER_NAME = "PastPartnerName";
    public static final String PAST_PARTNER_CAST = "PastPartnerCast";
    public static final String PAST_PARTNER_VILLAGE = "PastPartnerVillage";
    public static final String PAST_PARTNER_MARRIAGE_DATE = "PastPartnerMarriageDate";
    ImageView ProfilePicture;
    TextView ShowBirthDate,MEducation,MWork,ShowDeathDate,MarryDateShow;
    EditText MName,MMobile1,MMobile2;
    AutoCompleteTextView MState,MDistrict,MTahsil,MVillage,MCast,MFatherVillage,MFatherName,MMotherVillage,MMotherCast,MMotherName;
    RadioGroup MGender, MarriageStatus,MLife;
    RadioButton MMale,MFemale,MAlive,MDead;
    RadioButton MSingle,MMarried,MDivorced,MWidow,MMarriedAfterWidow,MMarriedAfterDivorced;
    CardView CurrentPartnerCard;
    LinearLayout DeadContainer;
    Button PastPartnerAdd;


    AutoCompleteTextView MCurrentPartnerName,MCurrentPartnerVillage,MCurrentPartnerCast;

    ListView MPastPartnerList,MCurrentChildList;

    SinglePartnerListAdapter PartnerAdapter;
    ArrayList<SinglePartnerListItem> PartnerList = new ArrayList<>();

    SingleChildListAdapter ChildAdapter;
    ArrayList<SingleChildListItem> ChildList = new ArrayList<>();

    SuggestionAdapter FatherSuggetionAdapter,MotherSuggetionAdapter,CurrentPartnerSuggetionAdapter,ChildSuggetionAdapter,PartnerSuggetionAdapter;
    ArrayList<SuggetionItem> FatherSuggetionList = new ArrayList<>();
    boolean isFatherSuggested = false;
    ArrayList<SuggetionItem> MotherSuggetionList = new ArrayList<>();
    boolean isMotherSuggested = false;
    ArrayList<SuggetionItem> ChildSuggetionList = new ArrayList<>();
    boolean isCurrentPartnerSuggested = false;
    ArrayList<SuggetionItem> CurrentPartnerSuggetionList = new ArrayList<>();
    ArrayList<SuggetionItem> PartnerSuggetionList = new ArrayList<>();
    String Mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        Mid = intent.getStringExtra(ST.MEMBER_ID);

        ProfilePicture = findViewById(R.id.profile_image);
        ShowBirthDate = findViewById(R.id.show_date);
        MarryDateShow = findViewById(R.id.sadi_ki_tithi_show);
        ShowDeathDate = findViewById(R.id.show_death_date);
        MName = findViewById(R.id.m_name);
        MMobile1 = findViewById(R.id.m_mobile_1);
        MMobile2 = findViewById(R.id.m_mobile_2);
        PastPartnerAdd = findViewById(R.id.m_past_partner_add);
        MEducation = findViewById(R.id.m_education);
        MWork = findViewById(R.id.m_work);
        MState = findViewById(R.id.m_state);
        MDistrict = findViewById(R.id.m_district);
        MAlive = findViewById(R.id.m_alive);
        MDead = findViewById(R.id.m_dead);
        MTahsil = findViewById(R.id.m_tahsil);
        MVillage = findViewById(R.id.m_village);
        MCast = findViewById(R.id.m_cast);
        MFatherName = findViewById(R.id.m_father_name);
        MFatherVillage = findViewById(R.id.m_father_village);
        MMotherVillage = findViewById(R.id.m_mother_village);
        MMotherCast = findViewById(R.id.m_mother_cast);
        MMotherName = findViewById(R.id.m_mother_name);
        MGender = findViewById(R.id.m_gender);
        MLife = findViewById(R.id.m_life);
        MarriageStatus = findViewById(R.id.m_marriage_status);
        MMale = findViewById(R.id.m_male);
        MFemale = findViewById(R.id.m_female);
        MSingle = findViewById(R.id.m_single);
        MMarried = findViewById(R.id.m_married);
        MDivorced = findViewById(R.id.m_divorced);
        MWidow = findViewById(R.id.m_widow);
        MMarriedAfterDivorced = findViewById(R.id.m_second_married_divorced);
        MMarriedAfterWidow = findViewById(R.id.m_second_married_widow);
        CurrentPartnerCard = findViewById(R.id.m_current_partner);
        MCurrentPartnerName = findViewById(R.id.m_current_partner_name);
        MCurrentPartnerCast = findViewById(R.id.m_current_partner_cast);
        MCurrentPartnerVillage = findViewById(R.id.m_current_partner_village);
        DeadContainer = findViewById(R.id.dead_container);
        MPastPartnerList = findViewById(R.id.m_past_wife_list);
        MCurrentChildList = findViewById(R.id.m_current_beta_beti_list);

        MGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (MName.getText().toString().isEmpty()){
                    ST.FillAboveInput(EditActivity.this);
                }
            }
        });
        MMobile1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (EducationStatusSt.isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MMobile1.clearFocus();
                    }else{
                        MMobile1.requestFocus();
                    }
                }
            }
        });
        MCast.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MName.getText().toString().isEmpty()||MMobile1.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MCast.clearFocus();
                    }
                    else{
                        MCast.requestFocus();
                    }
                }
            }
        });
        MState.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (WorkSt.isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MState.clearFocus();
                    }
                    else{
                        MState.requestFocus();
                    }
                }
            }
        });
        MDistrict.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MState.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MDistrict.clearFocus();
                    }
                    else{
                        MDistrict.requestFocus();
                    }
                }
            }
        });
        MTahsil.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MDistrict.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MTahsil.clearFocus();
                    }
                    else{
                        MTahsil.requestFocus();
                    }
                }
            }
        });
        MVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MTahsil.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MVillage.clearFocus();
                    }
                    else{
                        MVillage.requestFocus();
                    }
                }
            }
        });

        MFatherName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MFatherVillage.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MFatherName.clearFocus();
                    }
                    else{
                        MFatherName.requestFocus();
                    }
                }
            }
        });
        MMotherCast.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MFatherName.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MMotherCast.clearFocus();
                    }
                    else{
                        MMotherCast.requestFocus();
                    }
                }
            }
        });
        MMotherName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MMotherVillage.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MMotherName.clearFocus();
                    }
                    else{
                        MMotherName.requestFocus();
                    }
                }
            }
        });
        MCurrentPartnerCast.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MCast.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MCurrentPartnerCast.clearFocus();
                    }
                    else{
                        MCurrentPartnerCast.requestFocus();
                    }
                }
            }
        });
        MCurrentPartnerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MCurrentPartnerVillage.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MCurrentPartnerName.clearFocus();
                    }
                    else{
                        MCurrentPartnerName.requestFocus();
                    }
                }
            }
        });


        PartnerAdapter = new SinglePartnerListAdapter(this,PartnerList);
        MPastPartnerList.setAdapter(PartnerAdapter);

        ChildAdapter = new SingleChildListAdapter(this,ChildList);
        MCurrentChildList.setAdapter(ChildAdapter);

        ChildSuggetionAdapter = new SuggestionAdapter(EditActivity.this,ChildSuggetionList);
        
        FatherSuggetionAdapter = new SuggestionAdapter(EditActivity.this,FatherSuggetionList);
        MFatherName.setAdapter(FatherSuggetionAdapter);

        MFatherName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FatherIdSt = FatherSuggetionList.get(position).getId();
                isFatherSuggested = true;
            }
        });
        MMotherName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MotherIdSt = MotherSuggetionList.get(position).getId();
                isMotherSuggested = true;
            }
        });
        MCurrentPartnerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CurrentPartnerIdSt = CurrentPartnerSuggetionList.get(position).getId();
                isCurrentPartnerSuggested = true;
            }
        });
        MFatherVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MVillage.getText().toString().isEmpty()||(MLife.getCheckedRadioButtonId()==R.id.m_dead&&ShowDeathDate.getText().toString().equals(getString(R.string.no_date_selected)))){
                        ST.FillAboveInput(EditActivity.this);
                        MFatherVillage.clearFocus();
                    }
                    else{
                        MFatherVillage.requestFocus();
                    }
                }
                else{
                    if (MVillage.getText().toString().isEmpty()||(MLife.getCheckedRadioButtonId()==R.id.m_dead&&ShowDeathDate.getText().toString().equals(getString(R.string.no_date_selected)))){
                        return;
                    }
                    ST.ShowProgress(EditActivity.this);
                    ST.SuggetionList
                            .whereEqualTo(ST.VILLAGE,MFatherVillage.getText().toString())
                            .whereEqualTo(ST.CAST,MCast.getText().toString())
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            FatherSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    FatherSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ST.ID).toString(),
                                            documentSnapshot.get(ST.NAME).toString(),
                                            documentSnapshot.get(ST.VILLAGE).toString(),
                                            documentSnapshot.get(ST.CAST).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_VALUE).toString()
                                    ));
                                }catch (NullPointerException ex){}
                            }
                            //TODO Remove It
                            FatherSuggetionList.add(new SuggetionItem(
                                    "AAA",
                                    "समय",
                                    "सेरुणा",
                                    "आडसर",
                                    "पिता का नाम",
                                    "Space"
                            ));
                            FatherSuggetionAdapter.notifyDataSetChanged();
                            
                            ST.HideProgress();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ST.ShowDialog(EditActivity.this,e.toString());
                            ST.HideProgress();
                        }
                    });
                }
            }
        });
        MotherSuggetionAdapter = new SuggestionAdapter(EditActivity.this,MotherSuggetionList);
        MMotherName.setAdapter(MotherSuggetionAdapter);
        PartnerSuggetionAdapter = new SuggestionAdapter(EditActivity.this,PartnerSuggetionList);
        MMotherVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MMotherCast.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MMotherVillage.clearFocus();
                    }
                    else{
                        MMotherVillage.requestFocus();
                    }
                }
                else{
                    if (MMotherCast.getText().toString().isEmpty()){
                        return;
                    }
                    ST.ShowProgress(EditActivity.this);
                    ST.SuggetionList
                            .whereEqualTo(ST.VILLAGE,MMotherVillage.getText().toString())
                            .whereEqualTo(ST.CAST,MMotherCast.getText().toString())
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            MotherSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    MotherSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ST.ID).toString(),
                                            documentSnapshot.get(ST.NAME).toString(),
                                            documentSnapshot.get(ST.VILLAGE).toString(),
                                            documentSnapshot.get(ST.CAST).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_VALUE).toString()
                                    ));
                                }catch (NullPointerException ex){}
                            }
                            MotherSuggetionAdapter.notifyDataSetChanged();

                            ST.HideProgress();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ST.ShowDialog(EditActivity.this,e.toString());
                            ST.HideProgress();
                        }
                    });
                }
            }
        });
        CurrentPartnerSuggetionAdapter = new SuggestionAdapter(EditActivity.this,CurrentPartnerSuggetionList);
        MCurrentPartnerName.setAdapter(CurrentPartnerSuggetionAdapter);
        MCurrentPartnerVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (MCurrentPartnerCast.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        MCurrentPartnerVillage.clearFocus();
                    }
                    else{
                        MCurrentPartnerVillage.requestFocus();
                    }
                }else{
                    if (MCurrentPartnerCast.getText().toString().isEmpty()){
                        return;
                    }
                    ST.ShowProgress(EditActivity.this);
                    ST.SuggetionList
                            .whereEqualTo(ST.VILLAGE,MCurrentPartnerVillage.getText().toString())
                            .whereEqualTo(ST.CAST,MCurrentPartnerCast.getText().toString())
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            CurrentPartnerSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    CurrentPartnerSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ST.ID).toString(),
                                            documentSnapshot.get(ST.NAME).toString(),
                                            documentSnapshot.get(ST.VILLAGE).toString(),
                                            documentSnapshot.get(ST.CAST).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_VALUE).toString()
                                    ));
                                }catch (NullPointerException ex){}
                            }
                            CurrentPartnerSuggetionAdapter.notifyDataSetChanged();

                            ST.HideProgress();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ST.ShowDialog(EditActivity.this,e.toString());
                            ST.HideProgress();
                        }
                    });
                }
            }
        });

        MLife.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.m_alive:
                        DeadContainer.setVisibility(View.GONE);
                        break;
                    case R.id.m_dead:
                        DeadContainer.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }
        });

        MState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ST.ShowProgress(EditActivity.this);
                ST.DistrictList(MState.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ST.HideProgress();
                        if (documentSnapshot.exists()){
                            DistrictList = (ArrayList<String>) documentSnapshot.get(ST.MY_LIST);
                            ST.setSnipper(EditActivity.this,MDistrict,DistrictList);
                        }
                    }
                });
            }
        });

        MDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ST.ShowProgress(EditActivity.this);
                ST.TahsilList(MState.getText().toString(),MDistrict.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ST.HideProgress();
                        if (documentSnapshot.exists()){
                            TahsilList = (ArrayList<String>) documentSnapshot.get(ST.MY_LIST);
                            ST.setSnipper(EditActivity.this,MTahsil,TahsilList);
                        }
                    }
                });
            }
        });

        MTahsil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ST.ShowProgress(EditActivity.this);
                ST.VillageList(MState.getText().toString(),MDistrict.getText().toString(), MTahsil.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ST.HideProgress();
                        if (documentSnapshot.exists()){
                            VillageList = (ArrayList<String>) documentSnapshot.get(ST.MY_LIST);
                            ST.setSnipper(EditActivity.this,MVillage,VillageList);
                        }
                    }
                });
            }
        });
        MarriageStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (MMotherName.getText().toString().isEmpty()){
                    ST.FillAboveInput(EditActivity.this);
                    MarriageStatus.clearFocus();
                    return;
                }
                switch (checkedId){
                    case R.id.m_single:
                        CurrentPartnerCard.setVisibility(View.GONE);
                        PastPartnerAdd.setVisibility(View.GONE);
                        MPastPartnerList.setVisibility(View.GONE);
                        break;
                    case R.id.m_married:
                        CurrentPartnerCard.setVisibility(View.VISIBLE);
                        PastPartnerAdd.setVisibility(View.GONE);
                        MPastPartnerList.setVisibility(View.GONE);
                        break;
                    case R.id.m_divorced:
                        CurrentPartnerCard.setVisibility(View.GONE);
                        PastPartnerAdd.setVisibility(View.VISIBLE);
                        MPastPartnerList.setVisibility(View.VISIBLE);
                        break;
                    case R.id.m_widow:
                        CurrentPartnerCard.setVisibility(View.GONE);
                        PastPartnerAdd.setVisibility(View.VISIBLE);
                        MPastPartnerList.setVisibility(View.VISIBLE);
                        break;
                    case R.id.m_second_married_widow:
                        CurrentPartnerCard.setVisibility(View.VISIBLE);
                        PastPartnerAdd.setVisibility(View.VISIBLE);
                        MPastPartnerList.setVisibility(View.VISIBLE);
                        break;
                    case R.id.m_second_married_divorced:
                        CurrentPartnerCard.setVisibility(View.VISIBLE);
                        PastPartnerAdd.setVisibility(View.VISIBLE);
                        MPastPartnerList.setVisibility(View.VISIBLE);
                        break;
                    default:
                        CurrentPartnerCard.setVisibility(View.GONE);
                        PastPartnerAdd.setVisibility(View.GONE);
                        MPastPartnerList.setVisibility(View.GONE);
                }
            }
        });


        ST.setListViewHeightBasedOnChildren(MCurrentChildList);
    }

    ArrayList<String> StateList = new ArrayList<>();
    ArrayList<String> DistrictList = new ArrayList<>();
    ArrayList<String> TahsilList = new ArrayList<>();
    ArrayList<String> VillageList = new ArrayList<>();

    ArrayList<String> CastList = new ArrayList<>();
    ArrayList<String> VillagesL = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        ST.CompleteDataSingle(Mid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FillPreData(documentSnapshot);
                }else{
                    ST.ProblemCause(EditActivity.this);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ST.ProblemCause(EditActivity.this);
            }
        });
        ST.StateList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    StateList = (ArrayList<String>) documentSnapshot.get(ST.MY_LIST);
                    ST.setSnipper(EditActivity.this,MState,StateList);
                }
            }
        });
        ST.CastList.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                CastList.clear();
                if (documentSnapshot.exists()){
                    CastList = (ArrayList<String>) documentSnapshot.get(ST.MY_LIST);
                    ST.setSnipper(EditActivity.this,MCast,CastList);
                    ST.setSnipper(EditActivity.this,MMotherCast,CastList);
                    ST.setSnipper(EditActivity.this,MCurrentPartnerCast, CastList);
                }
            }
        });
        ST.Villages.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                VillagesL.clear();
                if (documentSnapshot.exists()){
                    VillagesL = (ArrayList<String>) documentSnapshot.get(ST.MY_LIST);
                    ST.setSnipper(EditActivity.this,MCurrentPartnerVillage,VillagesL);
                    ST.setSnipper(EditActivity.this,MFatherVillage,VillagesL);
                    ST.setSnipper(EditActivity.this,MMotherVillage,VillagesL);
                }
            }
        });

    }

    private void FillPreData(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.contains(ST.PROFILE_PIC)){
            try{
                Glide.with(EditActivity.this)
                        .load((String)documentSnapshot.get(ST.PROFILE_PIC))
                        .into(ProfilePicture);
            }catch (Exception e){
                Toast.makeText(this, getString(R.string.photo_not_there), Toast.LENGTH_SHORT).show();
            }
        }
        if (documentSnapshot.contains(ST.MEMBER_NAME)){
            MName.setText(documentSnapshot.get(ST.MEMBER_NAME).toString());
        }
        if (documentSnapshot.contains(ST.EDUCATION_STATUS)){
            MEducation.setText(documentSnapshot.get(ST.EDUCATION_STATUS).toString());
        }
        if (documentSnapshot.contains(ST.IS_MALE)){
            if ((boolean) documentSnapshot.get(ST.IS_MALE)){
                MMale.setChecked(true);
            }else{
                MFemale.setChecked(true);
            }
        }
        if (documentSnapshot.contains(ST.DOB)){
            ShowBirthDate.setText(ST.DateToString((Date) documentSnapshot.get(ST.DOB)));
            isBirthDate = true;
        }
        if (documentSnapshot.contains(ST.IS_ALIVE)){
            if ((boolean) documentSnapshot.get(ST.IS_ALIVE)){
                MAlive.setChecked(true);
            }
            else{
                MDead.setChecked(true);
            }
        }
        if (documentSnapshot.contains(ST.DOD)){
            ShowDeathDate.setText((String)documentSnapshot.get(ST.DOD));
            isDeathDate = true;
        }
        if (documentSnapshot.contains(ST.STATE)){
            MState.setText((String)documentSnapshot.get(ST.STATE));
        }
        if (documentSnapshot.contains(ST.DISTRICT)){
            MDistrict.setText((String) documentSnapshot.get(ST.DISTRICT));
        }
        if (documentSnapshot.contains(ST.TAHSIL)){
            MTahsil.setText((String) documentSnapshot.get(ST.TAHSIL));
        }
        if (documentSnapshot.contains(ST.VILLAGE)){
            MVillage.setText((String) documentSnapshot.get(ST.VILLAGE));
        }
        if (documentSnapshot.contains(ST.PRIMARY_MOBILE_NO)){
            MMobile1.setText((String) documentSnapshot.get(ST.PRIMARY_MOBILE_NO));
        }
        if (documentSnapshot.contains(ST.SECONDARY_MOBILE_NO)){
            MMobile2.setText((String) documentSnapshot.get(ST.SECONDARY_MOBILE_NO));
        }
        if (documentSnapshot.contains(ST.CAST)){
            MCast.setText((String) documentSnapshot.get(ST.CAST));
        }
        if (documentSnapshot.contains(ST.WORK)){
            ArrayList<String> WorkList = (ArrayList<String>) documentSnapshot.get(ST.WORK);

            String list = "";
            for (int i = 0; i < WorkList.size(); i++){

                list = list + WorkList.get(i) + ", ";

                MWork.setText(list);
            }
        }
        if (documentSnapshot.contains(ST.FATHER_VILLAGE)){
            MFatherVillage.setText((String) documentSnapshot.get(ST.FATHER_VILLAGE));
        }
        if (documentSnapshot.contains(ST.FATHER_NAME)){
            MFatherName.setText((String) documentSnapshot.get(ST.FATHER_NAME));
        }
        if (documentSnapshot.contains(ST.FATHER_ID)){
            FatherIdSt = (String) documentSnapshot.get(ST.FATHER_ID);
            isFatherSuggested = true;
        }
        if (documentSnapshot.contains(ST.MOTHER_VILLAGE)){
            MMotherVillage.setText((String) documentSnapshot.get(ST.MOTHER_VILLAGE));
        }
        if (documentSnapshot.contains(ST.MOTHER_CAST)){
            MMotherCast.setText((String) documentSnapshot.get(ST.MOTHER_CAST));
        }
        if (documentSnapshot.contains(ST.MOTHER_NAME)){
            MMotherName.setText((String) documentSnapshot.get(ST.MOTHER_NAME));
        }
        if (documentSnapshot.contains(ST.MOTHER_ID)){
            MotherIdSt = (String) documentSnapshot.get(ST.MOTHER_ID);
            isMotherSuggested = true;
        }
        if (documentSnapshot.contains(ST.IS_SINGLE)){
            if ((boolean) documentSnapshot.get(ST.IS_SINGLE))
                MSingle.setChecked(true);
        }
        if (documentSnapshot.contains(ST.IS_MARRIED)){
            if ((boolean) documentSnapshot.get(ST.IS_MARRIED))
            {
                MMarried.setChecked(true);
                CurrentPartnerCard.setVisibility(View.VISIBLE);
            }
        }
        if (documentSnapshot.contains(ST.IS_DIVORCED)){
            if ((boolean) documentSnapshot.get(ST.IS_DIVORCED))
            {
                MDivorced.setChecked(true);
                PastPartnerAdd.setVisibility(View.VISIBLE);
                MPastPartnerList.setVisibility(View.VISIBLE);
            }
        }
        if (documentSnapshot.contains(ST.IS_WIDOW)){
            if ((boolean) documentSnapshot.get(ST.IS_WIDOW))
            {
                MWidow.setChecked(true);
                PastPartnerAdd.setVisibility(View.VISIBLE);
                MPastPartnerList.setVisibility(View.VISIBLE);
            }
        }
        if (documentSnapshot.contains(ST.IS_MARRIED_AFTER_PARTNER_DEATH)){
            if ((boolean) documentSnapshot.get(ST.IS_MARRIED_AFTER_PARTNER_DEATH))
            {
                MMarriedAfterWidow.setChecked(true);
                CurrentPartnerCard.setVisibility(View.VISIBLE);
                PastPartnerAdd.setVisibility(View.VISIBLE);
                MPastPartnerList.setVisibility(View.VISIBLE);
            }
        }
        if (documentSnapshot.contains(ST.IS_MARRIED_AFTER_DIVORCED_WITH_PARTNER)){
            if ((boolean) documentSnapshot.get(ST.IS_MARRIED_AFTER_DIVORCED_WITH_PARTNER))
            {
                MMarriedAfterDivorced.setChecked(true);
                CurrentPartnerCard.setVisibility(View.VISIBLE);
                PastPartnerAdd.setVisibility(View.VISIBLE);
                MPastPartnerList.setVisibility(View.VISIBLE);
            }
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_VILLAGE)){
            MCurrentPartnerVillage.setText((String) documentSnapshot.get(ST.CURRENT_PARTNER_VILLAGE));
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_CAST)){
            MCurrentPartnerCast.setText((String) documentSnapshot.get(ST.CURRENT_PARTNER_CAST));
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_NAME)){
            MCurrentPartnerName.setText((String) documentSnapshot.get(ST.CURRENT_PARTNER_NAME));
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_MARRIAGE_DATE)){
            MarryDateShow.setText(ST.DateToString((Date) documentSnapshot.get(ST.CURRENT_PARTNER_MARRIAGE_DATE)));
            isCurrentMarryDate = true;
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_ID)){
            CurrentPartnerIdSt = (String) documentSnapshot.get(ST.CURRENT_PARTNER_ID);
            isCurrentPartnerSuggested = true;
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_CHILD_LIST)){
            ArrayList<Map<String,Object>> CloudCurrentPartnerChildList = (ArrayList<Map<String, Object>>) documentSnapshot.get(ST.CURRENT_PARTNER_CHILD_LIST);

            ChildList.clear();
            for (int i = 0; i < CloudCurrentPartnerChildList.size(); i++){
                SingleChildListItem item = new SingleChildListItem(
                        (String)CloudCurrentPartnerChildList.get(i).get(CHILD_ID),
                        (String)CloudCurrentPartnerChildList.get(i).get(CHILD_NAME),
                        (String)CloudCurrentPartnerChildList.get(i).get(CHILD_VILLAGE),
                        (boolean)CloudCurrentPartnerChildList.get(i).get(CHILD_IS_MALE)
                );
                ChildList.add(item);
            }
            ChildAdapter.notifyDataSetChanged();
            ST.setListViewHeightBasedOnChildren(MCurrentChildList);
            ChildAdapter.notifyDataSetChanged();
        }
        if (documentSnapshot.contains(ST.PAST_PARTNER_LIST)){
            ArrayList<Map<String, Object>> CloudPastPartnerList = (ArrayList<Map<String, Object>>) documentSnapshot.get(ST.PAST_PARTNER_LIST);



            PartnerList.clear();
            for (int i = 0; i < CloudPastPartnerList.size(); i++){
                ArrayList<SingleChildListItem> PastPartnerChildList = new ArrayList<>();
                try{
                    ArrayList<Map<String,Object>> CloudCurrentPartnerChildList = (ArrayList<Map<String, Object>>) CloudPastPartnerList.get(i).get("PastPartnerChildList");

                    for (int j = 0; j < CloudCurrentPartnerChildList.size(); j++){
                        SingleChildListItem iitem = new SingleChildListItem(
                                (String) CloudCurrentPartnerChildList.get(j).get(CHILD_ID),
                                (String) CloudCurrentPartnerChildList.get(j).get(CHILD_NAME),
                                (String) CloudCurrentPartnerChildList.get(j).get(CHILD_VILLAGE),
                                (boolean) CloudCurrentPartnerChildList.get(j).get(CHILD_IS_MALE)
                        );
                        PastPartnerChildList.add(iitem);
                    }

                }catch (Exception e){}

                SinglePartnerListItem item = new SinglePartnerListItem(
                        (String) CloudPastPartnerList.get(i).get(PAST_PARTNER_ID),
                        (String) CloudPastPartnerList.get(i).get(PAST_PARTNER_NAME),
                        (String) CloudPastPartnerList.get(i).get(PAST_PARTNER_CAST),
                        (String) CloudPastPartnerList.get(i).get(PAST_PARTNER_VILLAGE),
                        (Date) CloudPastPartnerList.get(i).get(PAST_PARTNER_MARRIAGE_DATE),
                        PastPartnerChildList
                );
                PartnerList.add(item);
            }
            PartnerAdapter.notifyDataSetChanged();
            ST.setListViewHeightBasedOnPartner(MPastPartnerList);
            PartnerAdapter.notifyDataSetChanged();

        }
        if (documentSnapshot.contains(ST.FIRST_RELATION_KEY)){
            FirstRelationKey = (String) documentSnapshot.get(ST.FIRST_RELATION_KEY);
        }
        if (documentSnapshot.contains(ST.FIRST_RELATION_VALUE)){
            FirstRelationValue = (String) documentSnapshot.get(ST.FIRST_RELATION_VALUE);
        }
        if (documentSnapshot.contains(ST.FIRST_RELATION_ID)){
            FirstRelationId = (String) documentSnapshot.get(ST.FIRST_RELATION_ID);
        }
        if (documentSnapshot.contains(ST.EDITORS_LIST)){
            ArrayList<String> editorList = (ArrayList<String>) documentSnapshot.get(ST.EDITORS_LIST);
            if (!editorList.contains(ST.mUid)){
                EditorsList.add(ST.mUid);
            }
        }
        if (documentSnapshot.contains(ST.EDITING_KEY)){

        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_UID)){

        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_ID)){

        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_NAME)){

        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_MOBILE_NUMBER)){

        }
    }

    ArrayList<String> EditorsList = new ArrayList<>();

    private ArrayList<SuggetionItem> populateCustomerData(ArrayList<SuggetionItem> SuggetionList) {
        SuggetionList.add(new SuggetionItem(
                "AAA",
                "समय",
                "सेरुणा",
                "आडसर",
                "पिता का नाम",
                "Space"
        ));
        SuggetionList.add(new SuggetionItem(
                "AAA",
                "समय",
                "सेरुणा",
                "आडसर",
                "पिता का नाम",
                "Space"
        ));
        SuggetionList.add(new SuggetionItem(
                "AAA",
                "समय",
                "सेरुणा",
                "आडसर",
                "पिता का नाम",
                "Space"
        ));
        SuggetionList.add(new SuggetionItem(
                "AAA",
                "समय",
                "सेरुणा",
                "आडसर",
                "पिता का नाम",
                "Space"
        ));
        SuggetionList.add(new SuggetionItem(
                "AAA",
                "समय",
                "सेरुणा",
                "आडसर",
                "पिता का नाम",
                "Space"
        ));

        return SuggetionList;
    }

    String EducationStatusSt = "";
    public void ChooseEducation(View view){

        if (!isBirthDate||MName.getText().toString().isEmpty()|| ShowBirthDate.getText().toString().equals(getString(R.string.no_date_selected))){
            ST.FillAboveInput(EditActivity.this);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        final String[] eList = {
                "कोई नहीं",
                "स्नातक"
        };

        builder.setTitle(getString(R.string.sheksnik_yogyta))
                .setSingleChoiceItems(eList, 5, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EducationStatusSt = eList[which];
                    }
                })
                .setPositiveButton(getString(R.string.thik), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MEducation.setText(EducationStatusSt);
                    }
                });

        builder.create().show();
    }

    ArrayList<String> WorkSt = new ArrayList<>();
    public void ChooseWork(View view){
        if (MCast.getText().toString().isEmpty()){
            ST.FillAboveInput(this);
            return;
        }
        WorkSt.clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        final String[] eList = {
                "कोई नहीं",
                "बहुत कुछ",
                "सब कुछ"
        };

        builder.setTitle(getString(R.string.work))
                .setMultiChoiceItems(eList, new boolean[10], new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            WorkSt.add(eList[which]);
                        }else{
                            if (WorkSt.contains(eList[which])){
                                WorkSt.remove(eList[which]);
                            }
                        }
                    }
                })
                .setPositiveButton(getString(R.string.thik), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String list = "";
                        for (int i = 0; i < WorkSt.size(); i++){

                            list = list + WorkSt.get(i) + ", ";

                            MWork.setText(list);
                        }

                    }
                });

        builder.create().show();
    }


    boolean isBirthDate = false;
    public void ChooseDate(View view){
        if (!(MGender.getCheckedRadioButtonId()==R.id.m_male||MGender.getCheckedRadioButtonId()==R.id.m_female)||MName.getText().toString().isEmpty()){
            ST.FillAboveInput(EditActivity.this);
            return;
        }
        isBirthDate = ST.BirthDateDialog(this, ShowBirthDate);
    }
    boolean isCurrentMarryDate = false;
    public void CurrentMarryDate(View view){
        if (MCurrentPartnerName.getText().toString().isEmpty()){
            ST.FillAboveInput(this);
            return;
        }
        isCurrentMarryDate = ST.ChooseDateDialog(this,MarryDateShow);
    }

    boolean isDeathDate = false;
    public void ChooseDateOfDeath(View view){
        isDeathDate = ST.DeathDateDialog(this, ShowDeathDate);
    }


    AutoCompleteTextView childVillage, childName;
    RadioGroup childGender;
    Button AddChild;

    String ChildNameSt,ChildVillageSt,ChildGenderSt,ChildIdSt;
    boolean isChildSuggested = false;
    
    AlertDialog builder;
    public void AddCurrentPartnerChild(View view){

        isChildSuggested = false;
        ChildIdSt = "";
        ChildNameSt = "";
        ChildVillageSt = "";

        if (!isCurrentMarryDate||MarryDateShow.getText().toString().isEmpty()){
            ST.FillAboveInput(this);
            return;
        }

        int genderId = MGender.getCheckedRadioButtonId();
        switch (genderId){
            case R.id.m_male:
                isMale = true;
                break;
            case R.id.m_female:
                isMale = false;
                break;
            default:
                ST.FillInput(this);
                return;
        }
        if (!isMale){
            if (MCurrentPartnerCast.getText().toString().isEmpty()){
                ST.FillInput(this);
                return;
            }
        }

        builder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View childView = inflater.inflate(R.layout.add_child_layout,null);

        childName = childView.findViewById(R.id.m_child_name);
        childVillage = childView.findViewById(R.id.m_child_village);
        childGender = childView.findViewById(R.id.m_child_gender);
        AddChild = childView.findViewById(R.id.add_child);

        childName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (childVillage.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        childName.clearFocus();
                    }
                    else{
                        childName.requestFocus();
                    }
                }
            }
        });
        childGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (childName.getText().toString().isEmpty()){
                    ST.FillAboveInput(EditActivity.this);
                    if (childVillage.getText().toString().isEmpty()){
                        childVillage.requestFocus();
                    }else{
                        childName.requestFocus();
                    }
                }
            }
        });

        childName.setAdapter(ChildSuggetionAdapter);
        childName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isChildSuggested = true;
                ChildIdSt = ChildSuggetionList.get(position).getId();
            }
        });
        childVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ST.ShowProgress(EditActivity.this);
                    String ChildCast = isMale?MCast.getText().toString():MCurrentPartnerCast.getText().toString();
                    ST.SuggetionList
                            .whereEqualTo(ST.VILLAGE,childVillage.getText().toString())
                            .whereEqualTo(ST.CAST,ChildCast)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ChildSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    ChildSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ST.ID).toString(),
                                            documentSnapshot.get(ST.NAME).toString(),
                                            documentSnapshot.get(ST.VILLAGE).toString(),
                                            documentSnapshot.get(ST.CAST).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_VALUE).toString()
                                    ));
                                }catch (NullPointerException ex){}
                            }
                            ChildSuggetionAdapter.notifyDataSetChanged();

                            ST.HideProgress();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ST.ShowDialog(EditActivity.this,e.toString());
                            ST.HideProgress();
                        }
                    });
                }
            }
        });
        ST.setSnipper(EditActivity.this,childVillage,VillagesL);
        AddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChildNameSt = childName.getText().toString();
                ChildVillageSt = childVillage.getText().toString();
                if (ChildNameSt.isEmpty()){
                    ST.FillInput(EditActivity.this);
                    return;
                }
                if (ChildVillageSt.isEmpty()){
                    ST.FillInput(EditActivity.this);
                    return;
                }
                if (ChildIdSt.isEmpty()||isChildSuggested){
                    ChildIdSt = ST.GenerateId(new Date(),ChildNameSt,ChildVillageSt);
                }
                boolean isMale;
                switch (childGender.getCheckedRadioButtonId()){
                    case R.id.m_child_male:
                        ChildGenderSt = getString(R.string.purush);
                        isMale = true;
                        break;
                    case R.id.m_child_female:
                        ChildGenderSt = getString(R.string.mahila);
                        isMale = false;
                        break;
                    default:
                        ST.FillInput(EditActivity.this);
                        return;

                }
                SingleChildListItem item = new SingleChildListItem(ChildIdSt,ChildNameSt,ChildVillageSt,isMale);
                ChildList.add(item);
                ST.setListViewHeightBasedOnChildren(MCurrentChildList);
                ChildAdapter.notifyDataSetChanged();

                builder.dismiss();

            }
        });

        builder.setView(childView);
        builder.show();

    }

    AutoCompleteTextView PartnerName,PartnerVillage,PartnerCast;
    TextView DialogDate;
    ListView ChildListInPartner;

    String PartnerVillageSt,PartnerCastSt,PartnerNameSt,PartnerIdSt;
    boolean isPartnerSuggested = false;

    Button AddChildPartner,AddPartner;

    AlertDialog builder2;
    public void AddPartner(View view){
        isPartnerSuggested = false;
        PartnerIdSt = "";
        PartnerNameSt = "";
        PartnerVillageSt = "";
        PartnerCastSt = "";

        if (((MarriageStatus.getCheckedRadioButtonId()==R.id.m_second_married_divorced)
                ||(MarriageStatus.getCheckedRadioButtonId()==R.id.m_second_married_widow))
                &&(!isCurrentMarryDate||MarryDateShow.getText().toString().isEmpty())
                ){
            ST.FillAboveInput(this);
            return;
        }
        isSadiDate = false;
        builder2 = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View partnerView = inflater.inflate(R.layout.add_partner_view,null);

        PartnerVillage = partnerView.findViewById(R.id.m_current_partner_village);
        PartnerCast = partnerView.findViewById(R.id.m_current_partner_cast);
        PartnerName = partnerView.findViewById(R.id.m_current_partner_name);
        DialogDate = partnerView.findViewById(R.id.sadi_ki_tithi_show);
        ChildListInPartner = partnerView.findViewById(R.id.m_current_beta_beti_list);

        ST.setSnipper(EditActivity.this,PartnerVillage,VillagesL);
        ST.setSnipper(EditActivity.this,PartnerCast,CastList);
        AddChildPartner = partnerView.findViewById(R.id.add_partner_child);
        AddPartner = partnerView.findViewById(R.id.add_partner);

        PartnerCast.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (PartnerVillage.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        PartnerCast.clearFocus();
                    }
                    else {
                        PartnerCast.requestFocus();
                    }
                }
            }
        });
        PartnerName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (PartnerCast.getText().toString().isEmpty()){
                        ST.FillAboveInput(EditActivity.this);
                        PartnerName.clearFocus();
                    }
                    else{
                        PartnerName.requestFocus();
                    }
                }
            }
        });
        
        final SingleChildListAdapter PartnerChildAdapter;
        final ArrayList<SingleChildListItem> PartnerChildList = new ArrayList<>();

        PartnerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PartnerIdSt = PartnerSuggetionList.get(position).getId();
                isPartnerSuggested = true;
            }
        });

        PartnerVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ST.ShowProgress(EditActivity.this);
                    String ChildCast = PartnerCast.getText().toString();
                    ST.SuggetionList
                            .whereEqualTo(ST.VILLAGE,PartnerVillage.getText().toString())
                            .whereEqualTo(ST.CAST,ChildCast)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            PartnerSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    PartnerSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ST.ID).toString(),
                                            documentSnapshot.get(ST.NAME).toString(),
                                            documentSnapshot.get(ST.VILLAGE).toString(),
                                            documentSnapshot.get(ST.CAST).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(ST.FIRST_RELATION_VALUE).toString()
                                    ));
                                }catch (NullPointerException ex){}
                            }
                            PartnerSuggetionAdapter = new SuggestionAdapter(EditActivity.this,PartnerSuggetionList);
                            PartnerName.setAdapter(PartnerSuggetionAdapter);
                            PartnerSuggetionAdapter.notifyDataSetChanged();

                            ST.HideProgress();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ST.ShowDialog(EditActivity.this,e.toString());
                            ST.HideProgress();
                        }
                    });
                }
            }
        });

        PartnerChildAdapter = new SingleChildListAdapter(this,PartnerChildList);
        ChildListInPartner.setAdapter(PartnerChildAdapter);

        AddPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PartnerVillageSt = PartnerVillage.getText().toString();
                PartnerCastSt = PartnerCast.getText().toString();
                PartnerNameSt = PartnerName.getText().toString();

                if (PartnerVillageSt.isEmpty()){
                    ST.FillInput(EditActivity.this);
                    return;
                }
                if (PartnerCastSt.isEmpty()){
                    ST.FillInput(EditActivity.this);
                    return;
                }
                if (PartnerNameSt.isEmpty()){
                    ST.FillInput(EditActivity.this);
                    return;
                }

                if (!isSadiDate||DialogDate.getText().toString().equals(getString(R.string.no_date_selected)))
                    return;

                if (PartnerIdSt.isEmpty()){
                    PartnerIdSt = ST.GenerateId(new Date(),PartnerNameSt,PartnerVillageSt);
                }
                SinglePartnerListItem item = new SinglePartnerListItem(
                        "aaa",
                        PartnerNameSt,
                        PartnerCastSt,
                        PartnerVillageSt,
                        ST.staticDate,
                        PartnerChildList

                );


                PartnerList.add(item);
                ST.setListViewHeightBasedOnPartner(MPastPartnerList);
                PartnerAdapter.notifyDataSetChanged();

                builder2.dismiss();
            }
        });
        final AutoCompleteTextView[] childVillage2 = new AutoCompleteTextView[1];
        final AutoCompleteTextView[] childName2 = new AutoCompleteTextView[1];
        final RadioGroup[] childGender2 = new RadioGroup[1];
        final Button[] AddChild2 = new Button[1];

        final AlertDialog[] builder3 = new AlertDialog[1];
        AddChildPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildIdSt = "";
                ChildNameSt = "";
                ChildVillageSt = "";
                isChildSuggested = false;

                if (!isSadiDate||DialogDate.getText().toString().equals(getString(R.string.no_date_selected))){
                    ST.FillAboveInput(EditActivity.this);
                    return;
                }
                int genderId = MGender.getCheckedRadioButtonId();
                switch (genderId){
                    case R.id.m_male:
                        isMale = true;
                        break;
                    case R.id.m_female:
                        isMale = false;
                        break;
                    default:
                        ST.FillInput(EditActivity.this);
                        return;
                }
                if (!isMale){
                    if (PartnerCast.getText().toString().isEmpty()){
                        ST.FillInput(EditActivity.this);
                        return;
                    }
                }
                builder3[0] = new AlertDialog.Builder(EditActivity.this).create();
                LayoutInflater inflater = (LayoutInflater) EditActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                View childView = inflater.inflate(R.layout.add_child_layout,null);

                childName2[0] = childView.findViewById(R.id.m_child_name);
                childVillage2[0] = childView.findViewById(R.id.m_child_village);
                childGender2[0] = childView.findViewById(R.id.m_child_gender);
                AddChild2[0] = childView.findViewById(R.id.add_child);
                
                childName2[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){
                            if (childVillage2[0].getText().toString().isEmpty()){
                                ST.FillAboveInput(EditActivity.this);
                                childName2[0].clearFocus();
                            }
                            else{
                                childName2[0].requestFocus();
                            }
                        }
                    }
                });
                childGender2[0].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (childName2[0].getText().toString().isEmpty()){
                            ST.FillAboveInput(EditActivity.this);
                            if (childVillage2[0].getText().toString().isEmpty()){
                                childVillage2[0].requestFocus();
                            }else{
                                childName2[0].requestFocus();
                            }
                        }
                    }
                });

                childName2[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ChildIdSt = ChildSuggetionList.get(position).getId();
                        isChildSuggested = true;
                    }
                });

                childName2[0].setAdapter(ChildSuggetionAdapter);
                childVillage2[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus){
                            ST.ShowProgress(EditActivity.this);
                            String ChildCast = isMale?MCast.getText().toString():MCurrentPartnerCast.getText().toString();
                            ST.SuggetionList
                                    .whereEqualTo(ST.VILLAGE,childVillage2[0].getText().toString())
                                    .whereEqualTo(ST.CAST,ChildCast)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    ChildSuggetionList.clear();
                                    for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                        try{
                                            ChildSuggetionList.add(new SuggetionItem(
                                                    documentSnapshot.get(ST.ID).toString(),
                                                    documentSnapshot.get(ST.NAME).toString(),
                                                    documentSnapshot.get(ST.VILLAGE).toString(),
                                                    documentSnapshot.get(ST.CAST).toString(),
                                                    documentSnapshot.get(ST.FIRST_RELATION_KEY).toString(),
                                                    documentSnapshot.get(ST.FIRST_RELATION_VALUE).toString()
                                            ));
                                        }catch (NullPointerException ex){}
                                    }
                                    ChildSuggetionAdapter.notifyDataSetChanged();

                                    ST.HideProgress();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    ST.ShowDialog(EditActivity.this,e.toString());
                                    ST.HideProgress();
                                }
                            });
                        }
                    }
                });

                ST.setSnipper(EditActivity.this,childVillage2[0],VillagesL);
                AddChild2[0].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChildNameSt = childName2[0].getText().toString();
                        ChildVillageSt = childVillage2[0].getText().toString();
                        if (childName2[0].getText().toString().isEmpty()){
                            ST.FillInput(EditActivity.this);
                            return;
                        }
                        if (childVillage2[0].getText().toString().isEmpty()){
                            ST.FillInput(EditActivity.this);
                            return;
                        }
                        if (ChildIdSt.isEmpty()){
                            ChildIdSt = ST.GenerateId(new Date(),ChildNameSt,ChildVillageSt);
                        }
                        boolean isMale;
                        switch (childGender2[0].getCheckedRadioButtonId()){
                            case R.id.m_child_male:
                                ChildGenderSt = getString(R.string.purush);
                                isMale = true;
                                break;
                            case R.id.m_child_female:
                                ChildGenderSt = getString(R.string.mahila);
                                isMale = false;
                                break;
                            default:
                                ST.FillInput(EditActivity.this);
                                return;


                        }
                        SingleChildListItem item = new SingleChildListItem(ChildIdSt,ChildNameSt,ChildVillageSt,isMale);
                        PartnerChildList.add(item);
                        ST.setListViewHeightBasedOnChildren(ChildListInPartner);
                        PartnerChildAdapter.notifyDataSetChanged();

                        builder3[0].dismiss();

                    }
                });

                builder3[0].setView(childView);
                builder3[0].show();
            }
        });

        builder2.setView(partnerView);
        builder2.show();
    }

    boolean isSadiDate = false;
    public void ShadiDate(View view){
        if (PartnerName.getText().toString().isEmpty()){
            ST.FillAboveInput(EditActivity.this);
            return;
        }
        isSadiDate = ST.ChooseDateDialog(this,DialogDate);
    }


    //Upload Data

    String MStateSt;
    String MCastSt;

    String PhotoURLSt,MemberNameSt,MDistrictSt,MTahsilSt,MVillageSt,PrimaryMobileSt,SecondaryMobileSt;
    String FatherVillageSt,FatherNameSt,FatherIdSt,MotherVilageSt,MotherCastSt,MotherNameSt,MotherIdSt;
    String CurrentPartnerVillageSt,CurrentPartnerNameSt,CurrentPartnerCastSt,CurrentPartnerIdSt;
    String FirstRelationKey,FirstRelationValue,FirstRelationId;
    boolean isAlive = true,isMale,
            isSingle = false,
            isMarried = false,
            isDivorced = false,
            isWidow = false,
            isMarriedAfterPartnerDeath = false,
            isMarriedAfterDivorcedWithPartner = false;


    public void SetPicture(View view){

    }

    public void SaveBt(View view){
        MStateSt = MState.getText().toString();
        MCastSt = MCast.getText().toString();

        MemberNameSt = MName.getText().toString();
        MDistrictSt = MDistrict.getText().toString();
        MTahsilSt = MTahsil.getText().toString();
        MVillageSt = MVillage.getText().toString();
        PrimaryMobileSt = MMobile1.getText().toString();
        SecondaryMobileSt = MMobile2.getText().toString();
        FatherVillageSt = MFatherVillage.getText().toString();
        FatherNameSt = MFatherName.getText().toString();
        MotherVilageSt = MMotherVillage.getText().toString();
        MotherCastSt = MMotherCast.getText().toString();
        MotherNameSt = MMotherName.getText().toString();
        CurrentPartnerVillageSt = MCurrentPartnerVillage.getText().toString();
        CurrentPartnerNameSt = MCurrentPartnerName.getText().toString();
        CurrentPartnerCastSt = MCurrentPartnerCast.getText().toString();

        int lifeId = MLife.getCheckedRadioButtonId();
        switch (lifeId){
            case R.id.m_alive:
                isAlive = true;
                break;
            case R.id.m_dead:
                isAlive = false;
                break;
            default:
                isAlive = true;
        }

        int genderId = MGender.getCheckedRadioButtonId();
        switch (genderId){
            case R.id.m_male:
                isMale = true;
                break;
            case R.id.m_female:
                isMale = false;
                break;
            default:

        }

        int MarriageSId = MarriageStatus.getCheckedRadioButtonId();
        switch (MarriageSId){
            case R.id.m_single:
                isSingle = true;
                break;
            case R.id.m_married:
                isMarried = true;
                break;
            case R.id.m_divorced:
                isDivorced = true;
                break;
            case R.id.m_widow:
                isWidow = true;
                break;
            case R.id.m_second_married_widow:
                isMarriedAfterPartnerDeath = true;
                break;
            case R.id.m_second_married_divorced:
                isMarriedAfterDivorcedWithPartner = true;
                break;

            default:
        }

        if (FatherIdSt.isEmpty()||(!isFatherSuggested)){
            FatherIdSt = ST.GenerateId(new Date(),FatherNameSt,FatherVillageSt);
        }
        if (MotherIdSt.isEmpty()||(!isMotherSuggested)){
            MotherIdSt = ST.GenerateId(new Date(),MotherNameSt,MotherVilageSt);
        }
        if ((isMarriedAfterDivorcedWithPartner||isMarried||isMarriedAfterPartnerDeath)&&CurrentPartnerIdSt.isEmpty()||isCurrentPartnerSuggested){
            CurrentPartnerIdSt = ST.GenerateId(new Date(),CurrentPartnerNameSt,CurrentPartnerVillageSt);
        }

        if (!StateList.contains(MStateSt)){
            StateList.add(MStateSt);
            Map<String, Object> map = new HashMap<>();
            map.put(ST.MY_LIST, StateList);
            ST.StateList.update(map);
        }
        if (!DistrictList.contains(MDistrictSt)){
            DistrictList.add(MDistrictSt);
            Map<String, Object> map = new HashMap<>();
            map.put(ST.MY_LIST, DistrictList);
            ST.DistrictList(MStateSt).update(map);
        }
        if (!TahsilList.contains(MTahsilSt)){
            TahsilList.add(MTahsilSt);
            Map<String, Object> map = new HashMap<>();
            map.put(ST.MY_LIST, TahsilList);
            ST.TahsilList(MStateSt, MDistrictSt).update(map);
        }
        if (!VillageList.contains(MVillageSt)){
            VillageList.add(MVillageSt);
            Map<String, Object> map = new HashMap<>();
            map.put(ST.MY_LIST, VillageList);
            ST.VillageList(MStateSt,MDistrictSt,MTahsilSt).update(map);
        }
        if (!VillagesL.contains(MVillageSt)){
            VillagesL.add(MVillageSt);
        }
        if (!VillagesL.contains(MotherVilageSt)){
            VillagesL.add(MotherVilageSt);
        }
        if (!VillagesL.contains(FatherVillageSt)){
            VillagesL.add(FatherVillageSt);
        }
        if (!VillagesL.contains(CurrentPartnerVillageSt)){
            VillagesL.add(CurrentPartnerVillageSt);
        }
        Map<String, Object> VillageMap = new HashMap<>();
        VillageMap.put(ST.MY_LIST, VillagesL);
        ST.Villages.update(VillageMap);

        if (!CastList.contains(MCastSt)){
            CastList.add(MCastSt);
            Map<String, Object> castMap = new HashMap<>();
            castMap.put(ST.MY_LIST,CastList);
            ST.CastList.update(castMap);
        }

        Map<String, Object> MemberCompleteDataMap = new HashMap<>();

        MemberCompleteDataMap.put(ST.PROFILE_PIC,PhotoURLSt);
        MemberCompleteDataMap.put(ST.MEMBER_NAME,MemberNameSt);
        MemberCompleteDataMap.put(ST.EDUCATION_STATUS,EducationStatusSt);
        MemberCompleteDataMap.put(ST.IS_MALE,isMale);
        MemberCompleteDataMap.put(ST.DOB,ST.birthDate);
        MemberCompleteDataMap.put(ST.IS_ALIVE,isAlive);
        if (!isAlive)
        MemberCompleteDataMap.put(ST.DOD,ST.deathDate);
        MemberCompleteDataMap.put(ST.STATE,MStateSt);
        MemberCompleteDataMap.put(ST.DISTRICT,MDistrictSt);
        MemberCompleteDataMap.put(ST.TAHSIL,MTahsilSt);
        MemberCompleteDataMap.put(ST.VILLAGE,MVillageSt);
        MemberCompleteDataMap.put(ST.PRIMARY_MOBILE_NO,PrimaryMobileSt);
        MemberCompleteDataMap.put(ST.SECONDARY_MOBILE_NO,SecondaryMobileSt);
        MemberCompleteDataMap.put(ST.CAST,MCastSt);
        MemberCompleteDataMap.put(ST.WORK,WorkSt);
        MemberCompleteDataMap.put(ST.FATHER_ID,FatherIdSt);
        MemberCompleteDataMap.put(ST.FATHER_NAME,FatherNameSt);
        MemberCompleteDataMap.put(ST.FATHER_VILLAGE,FatherVillageSt);
        MemberCompleteDataMap.put(ST.MOTHER_VILLAGE,MotherVilageSt);
        MemberCompleteDataMap.put(ST.MOTHER_NAME,MotherNameSt);
        MemberCompleteDataMap.put(ST.MOTHER_CAST,MotherCastSt);
        MemberCompleteDataMap.put(ST.MOTHER_ID,MotherIdSt);
        MemberCompleteDataMap.put(ST.IS_SINGLE,isSingle);
        MemberCompleteDataMap.put(ST.IS_DIVORCED,isDivorced);
        MemberCompleteDataMap.put(ST.IS_WIDOW,isWidow);
        MemberCompleteDataMap.put(ST.IS_MARRIED_AFTER_PARTNER_DEATH,isMarriedAfterPartnerDeath);
        MemberCompleteDataMap.put(ST.IS_MARRIED_AFTER_DIVORCED_WITH_PARTNER,isMarriedAfterDivorcedWithPartner);
        MemberCompleteDataMap.put(ST.CURRENT_PARTNER_VILLAGE,CurrentPartnerVillageSt);
        MemberCompleteDataMap.put(ST.CURRENT_PARTNER_CAST,CurrentPartnerCastSt);
        MemberCompleteDataMap.put(ST.CURRENT_PARTNER_NAME,CurrentPartnerNameSt);
        MemberCompleteDataMap.put(ST.CURRENT_PARTNER_ID,CurrentPartnerIdSt);
        MemberCompleteDataMap.put(ST.CURRENT_PARTNER_CHILD_LIST,ChildList);
        MemberCompleteDataMap.put(ST.PAST_PARTNER_LIST,PartnerList);
//        MemberCompleteDataMap.put(ST.FIRST_RELATION_KEY,);
//        MemberCompleteDataMap.put(ST.FIRST_RELATION_VALUE,);
//        MemberCompleteDataMap.put(ST.FIRST_RELATION_ID,);
        MemberCompleteDataMap.put(ST.EDITORS_LIST,EditorsList);
//        MemberCompleteDataMap.put(ST.LAST_EDITED_BY_ID,);
        MemberCompleteDataMap.put(ST.LAST_EDITED_BY_UID,ST.mUid);
//        MemberCompleteDataMap.put(ST.LAST_EDITED_BY_NAME,);
        MemberCompleteDataMap.put(ST.LAST_EDITED_BY_MOBILE_NUMBER,Objects.requireNonNull(ST.currentUser.getPhoneNumber()));
/*
All Data
 */
        ST.CompleteDataSingle(Mid).update(MemberCompleteDataMap);
/*
SearchData
 */

        Map<String,Object> SearchMap = new HashMap<>();

        SearchMap.put(ST.MEMBER_ID,Mid);
        SearchMap.put(ST.MEMBER_NAME,MemberNameSt);
        SearchMap.put(ST.FATHER_NAME,FatherNameSt);
        SearchMap.put(ST.CAST,MCastSt);
        SearchMap.put(ST.VILLAGE,MVillageSt);
        SearchMap.put(ST.TAHSIL,MTahsilSt);
        SearchMap.put(ST.DISTRICT,MDistrictSt);
        SearchMap.put(ST.STATE,MStateSt);
        SearchMap.put(ST.EDUCATION_STATUS,EducationStatusSt);
        SearchMap.put(ST.WORK,WorkSt);
        SearchMap.put(ST.DOB,ST.birthDate);

        ST.SearchDataSingle(Mid).update(SearchMap);

        ST.MyMemberSingle(Mid).update(SearchMap);

        Map<String,Object> SuggationMap = new HashMap<>();
        SuggationMap.put(ST.MEMBER_ID,Mid);
        SuggationMap.put(ST.MEMBER_NAME,MemberNameSt);
        SuggationMap.put(ST.VILLAGE,MVillageSt);
        SuggationMap.put(ST.CAST,MCastSt);
        ST.SuggetionSingle(Mid).update(SuggationMap);


        ST.CompleteDataSingle(FatherIdSt).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                }
                else{
                    Map<String, Object> DataMap = new HashMap<>();

                    DataMap.put(ST.MEMBER_ID,FatherIdSt);
                    DataMap.put(ST.MEMBER_NAME,FatherNameSt);
                    DataMap.put(ST.CAST,MCastSt);
                    DataMap.put(ST.VILLAGE,FatherVillageSt);
                    DataMap.put(ST.IS_MALE,true);
                    DataMap.put(ST.IS_SINGLE,false);
                    DataMap.put(ST.IS_MARRIED,true);
                    DataMap.put(ST.IS_DIVORCED,false);
                    DataMap.put(ST.IS_WIDOW,false);
                    DataMap.put(ST.IS_MARRIED_AFTER_DIVORCED_WITH_PARTNER,false);
                    DataMap.put(ST.IS_MARRIED_AFTER_PARTNER_DEATH,false);
                    DataMap.put(ST.CURRENT_PARTNER_ID,MotherIdSt);
                    DataMap.put(ST.CURRENT_PARTNER_NAME,MotherNameSt);
                    DataMap.put(ST.CURRENT_PARTNER_VILLAGE,MotherVilageSt);
                    DataMap.put(ST.CURRENT_PARTNER_CAST,MotherCastSt);
                    ArrayList<SingleChildListItem> ChildListParents = new ArrayList<>();
                    SingleChildListItem item = new SingleChildListItem(
                            Mid,
                            MemberNameSt,
                            MVillageSt,
                            isMale
                    );
                    ChildListParents.add(item);
                    DataMap.put(ST.CURRENT_PARTNER_CHILD_LIST,ChildListParents);
                    DataMap.put(ST.FIRST_RELATION_ID,Mid);
                    DataMap.put(ST.FIRST_RELATION_KEY,getString(R.string.bche_ka_naam));
                    DataMap.put(ST.FIRST_RELATION_VALUE,MemberNameSt);
                    DataMap.put(ST.LAST_EDITED_BY_MOBILE_NUMBER,Objects.requireNonNull(ST.currentUser.getPhoneNumber()));
                    DataMap.put(ST.LAST_EDITED_BY_UID,ST.mUid);

                    ArrayList<String> EditorKey = new ArrayList<>();
                    EditorKey.add(ST.mUid);
                    DataMap.put(ST.EDITORS_LIST,EditorKey);

                    ST.CompleteDataSingle(FatherIdSt).set(DataMap);
                    Map<String, Object> SearchData = new HashMap<>();

                    SearchData.put(ST.MEMBER_ID,FatherIdSt);
                    SearchData.put(ST.MEMBER_NAME,FatherNameSt);
                    SearchData.put(ST.CAST,MCastSt);
                    SearchData.put(ST.VILLAGE,FatherVillageSt);


                }
            }
        });



    }

    public void CancelBt(View view){
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ST.setListViewHeightBasedOnChildren(MCurrentChildList);
    }
}
