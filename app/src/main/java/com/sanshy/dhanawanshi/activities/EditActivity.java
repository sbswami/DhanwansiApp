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
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String VILLAGE = "Village";
    public static final String CAST = "Cast";
    public static final String FIRST_RELATION_KEY = "FirstRelationKey";
    public static final String FIRST_RELATION_VALUE = "FirstRelationValue";
    ImageView ProfilePicture;
    TextView ShowDate,MEducation,MWork,ShowDeathDate,MarryDateShow;
    EditText MName,MMobile1,MMobile2;
    AutoCompleteTextView MState,MDistrict,MTahsil,MVillage,MCast,MFatherVillage,MFatherName,MMotherVillage,MMotherCast,MMotherName;
    RadioGroup MGender, MarriageStatus,MLife;
    RadioButton MMale,MFemale;
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

    SuggestionAdapter FatherSuggetionAdapter,MotherSuggetionAdapter,CurrentPartnerSuggetionAdapter,ChildSuggetionAdapter;
    ArrayList<SuggetionItem> FatherSuggetionList = new ArrayList<>();
    ArrayList<SuggetionItem> MotherSuggetionList = new ArrayList<>();
    ArrayList<SuggetionItem> ChildSuggetionList = new ArrayList<>();
    ArrayList<SuggetionItem> CurrentPartnerSuggetionList = new ArrayList<>();

    String Mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        Mid = intent.getStringExtra(ST.MEMBER_ID);

        ProfilePicture = findViewById(R.id.profile_image);
        ShowDate = findViewById(R.id.show_date);
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

        PartnerAdapter = new SinglePartnerListAdapter(this,PartnerList);
        MPastPartnerList.setAdapter(PartnerAdapter);

        ChildAdapter = new SingleChildListAdapter(this,ChildList);
        MCurrentChildList.setAdapter(ChildAdapter);

        ChildSuggetionAdapter = new SuggestionAdapter(EditActivity.this,ChildSuggetionList);
        
        FatherSuggetionAdapter = new SuggestionAdapter(EditActivity.this,FatherSuggetionList);
        MFatherName.setAdapter(FatherSuggetionAdapter);
        MFatherVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ST.ShowProgress(EditActivity.this);
                    ST.SuggetionList
                            .whereEqualTo(VILLAGE,MFatherVillage.getText().toString())
                            .whereEqualTo(CAST,MCast.getText().toString())
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            FatherSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    FatherSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ID).toString(),
                                            documentSnapshot.get(NAME).toString(),
                                            documentSnapshot.get(VILLAGE).toString(),
                                            documentSnapshot.get(CAST).toString(),
                                            documentSnapshot.get(FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(FIRST_RELATION_VALUE).toString()
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
        MMotherVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ST.ShowProgress(EditActivity.this);
                    ST.SuggetionList
                            .whereEqualTo(VILLAGE,MMotherVillage.getText().toString())
                            .whereEqualTo(CAST,MMotherCast.getText().toString())
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            MotherSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    MotherSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ID).toString(),
                                            documentSnapshot.get(NAME).toString(),
                                            documentSnapshot.get(VILLAGE).toString(),
                                            documentSnapshot.get(CAST).toString(),
                                            documentSnapshot.get(FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(FIRST_RELATION_VALUE).toString()
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
                if (!hasFocus){
                    ST.ShowProgress(EditActivity.this);
                    ST.SuggetionList
                            .whereEqualTo(VILLAGE,MCurrentPartnerVillage.getText().toString())
                            .whereEqualTo(CAST,MCurrentPartnerCast.getText().toString())
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            CurrentPartnerSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    CurrentPartnerSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ID).toString(),
                                            documentSnapshot.get(NAME).toString(),
                                            documentSnapshot.get(VILLAGE).toString(),
                                            documentSnapshot.get(CAST).toString(),
                                            documentSnapshot.get(FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(FIRST_RELATION_VALUE).toString()
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
                ST.DistricList(MState.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

    String EducationStatusSt;
    public void ChooseEducation(View view){

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


    boolean isDate = false;
    public void ChooseDate(View view){
        isDate = ST.ChooseDateDialog(this,ShowDate);
    }
    boolean isCurrentMarryDate = false;
    public void CurrentMarryDate(View view){
        isCurrentMarryDate = ST.ChooseDateDialog(this,ShowDate);
    }

    boolean isDeathDate = false;
    public void ChooseDateOfDeath(View view){
        isDeathDate = ST.ChooseDateDialog(this, ShowDeathDate);
    }


    AutoCompleteTextView childVillage, childName;
    RadioGroup childGender;
    Button AddChild;

    String ChildNameSt,ChildVillageSt,ChildGenderSt;

    
    AlertDialog builder;
    public void AddCurrentPartnerChild(View view){
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
        View childView = inflater.inflate(R.layout.add_child_layout,null);

        childName = childView.findViewById(R.id.m_child_name);
        childVillage = childView.findViewById(R.id.m_child_village);
        childGender = childView.findViewById(R.id.m_child_gender);
        AddChild = childView.findViewById(R.id.add_child);

        childName.setAdapter(ChildSuggetionAdapter);
        childVillage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ST.ShowProgress(EditActivity.this);
                    String ChildCast = isMale?MCast.getText().toString():MCurrentPartnerCast.getText().toString();
                    ST.SuggetionList
                            .whereEqualTo(VILLAGE,childVillage.getText().toString())
                            .whereEqualTo(CAST,ChildCast)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ChildSuggetionList.clear();
                            for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                try{
                                    ChildSuggetionList.add(new SuggetionItem(
                                            documentSnapshot.get(ID).toString(),
                                            documentSnapshot.get(NAME).toString(),
                                            documentSnapshot.get(VILLAGE).toString(),
                                            documentSnapshot.get(CAST).toString(),
                                            documentSnapshot.get(FIRST_RELATION_KEY).toString(),
                                            documentSnapshot.get(FIRST_RELATION_VALUE).toString()
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
                SingleChildListItem item = new SingleChildListItem("AAA",ChildNameSt,ChildVillageSt,isMale);
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


    Button AddChildPartner,AddPartner;

    AlertDialog builder2;
    public void AddPartner(View view){
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

        final SingleChildListAdapter PartnerChildAdapter;
        final ArrayList<SingleChildListItem> PartnerChildList = new ArrayList<>();


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

                if (!isSadiDate)
                    return;

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

                childName2[0].setAdapter(ChildSuggetionAdapter);
                childVillage2[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus){
                            ST.ShowProgress(EditActivity.this);
                            String ChildCast = isMale?MCast.getText().toString():MCurrentPartnerCast.getText().toString();
                            ST.SuggetionList
                                    .whereEqualTo(VILLAGE,childVillage2[0].getText().toString())
                                    .whereEqualTo(CAST,ChildCast)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    ChildSuggetionList.clear();
                                    for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                                        try{
                                            ChildSuggetionList.add(new SuggetionItem(
                                                    documentSnapshot.get(ID).toString(),
                                                    documentSnapshot.get(NAME).toString(),
                                                    documentSnapshot.get(VILLAGE).toString(),
                                                    documentSnapshot.get(CAST).toString(),
                                                    documentSnapshot.get(FIRST_RELATION_KEY).toString(),
                                                    documentSnapshot.get(FIRST_RELATION_VALUE).toString()
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
                        SingleChildListItem item = new SingleChildListItem("AAA",ChildNameSt,ChildVillageSt,isMale);
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
        isSadiDate = ST.ChooseDateDialog(this,DialogDate);
    }

    String PartnerVillageSt,PartnerCastSt,PartnerNameSt;

    //Upload Data

    String MStateSt;
    String MCastSt;

    String PhotoURLSt,MemberNameSt,MDistrictSt,MTahsilSt,MVillageSt,PrimaryMobileSt,SecondaryMobileSt;
    String FatherVillageSt,FatherNameSt,FatherIdSt,MotherVilageSt,MotherCastSt,MotherNameSt,MotherIdSt;
    String CurrentPartnerVillageSt,CurrentPartnerNameSt,CurrentPartnerCastSt,CurrentPartnerIdSt;
    String FirstRelationName,FirstRelationValue,FirstRelationId;
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

        if (!StateList.contains(MStateSt)){
            StateList.add(MStateSt);


        }
        if (!CastList.contains(MCastSt)){
            CastList.add(MCastSt);
            Map<String, Object> castMap = new HashMap<>();
            castMap.put(ST.MY_LIST,CastList);
            ST.CastList.update(castMap);
        }
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
