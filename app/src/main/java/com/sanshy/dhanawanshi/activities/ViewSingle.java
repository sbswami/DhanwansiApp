package com.sanshy.dhanawanshi.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.sanshy.dhanawanshi.R;
import com.sanshy.dhanawanshi.ST;
import com.sanshy.dhanawanshi.SingleAdapters.SingleChildListAdapter;
import com.sanshy.dhanawanshi.SingleAdapters.SinglePartnerListAdapter;
import com.sanshy.dhanawanshi.SingleItems.SingleChildListItem;
import com.sanshy.dhanawanshi.SingleItems.SinglePartnerListItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewSingle extends AppCompatActivity {

    String Mid;

    ImageView ProfilePhoto;
    TextView MName,DOB,DOD,Gender,Mobile,Cast,Work,MarriageStatus, MVillage, MTahsil,MEducation,DODName;
    TextView MDistrict, MState, MCurrentPartnerName, MCurrentPartnerVillage, MCurrentPartnerCast, CurrentMarriageDate, MFatherName, MFatherVillage, MMotherName, MMotherVillage, MMotherCast;
    TextView LastEdited;
    CardView PastPartnerCard,CurrentPartnerCard;

    ListView PastPartnersList,CurrentPartnerChildList;

    Date BirthDate = new Date();
    SinglePartnerListAdapter PastPartnerListAdapter;
    SingleChildListAdapter CurrentPartnerChildListAdapter;

    ArrayList<SinglePartnerListItem> pastPartnerList = new ArrayList<>();
    ArrayList<SingleChildListItem> childListItems = new ArrayList<>();

    ArrayList<String> editorList = new ArrayList<>();
    ArrayList<String> WorkList = new ArrayList<>();
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
        MName = findViewById(R.id.view_name);
        DOB = findViewById(R.id.view_dob);
        DOD = findViewById(R.id.view_death_date);
        MEducation = findViewById(R.id.view_education);
        DODName = findViewById(R.id.view_death_date_name);
        Gender = findViewById(R.id.view_gender);
        Mobile = findViewById(R.id.view_mobile_no);
        Cast = findViewById(R.id.view_gotr);
        Work = findViewById(R.id.view_work);
        MarriageStatus = findViewById(R.id.view_marriage_status);
        MVillage = findViewById(R.id.view_ganv);
        MTahsil = findViewById(R.id.view_tahsil);
        MDistrict = findViewById(R.id.view_jila);
        MState = findViewById(R.id.view_state);
        MCurrentPartnerName = findViewById(R.id.view_current_partner_name);
        MCurrentPartnerVillage = findViewById(R.id.view_current_partner_village);
        MCurrentPartnerCast = findViewById(R.id.view_current_partner_cast);
        CurrentMarriageDate = findViewById(R.id.view_current_partner_marry_date);
        MFatherName = findViewById(R.id.view_pita_ka_naam);
        MFatherVillage = findViewById(R.id.view_pita_ka_ganv);
        MMotherName = findViewById(R.id.view_mata_ka_naam);
        MMotherVillage = findViewById(R.id.view_mata_ka_ganv);
        MMotherCast = findViewById(R.id.view_mata_ki_gotr);
        PastPartnerCard = findViewById(R.id.view_past_partner_card);
        CurrentPartnerCard = findViewById(R.id.view_current_partner_card);
        LastEdited = findViewById(R.id.last_edited);

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


    String MStateSt;
    String MCastSt;

    String PhotoURLSt,MemberNameSt,MDistrictSt,MTahsilSt,MVillageSt,PrimaryMobileSt,SecondaryMobileSt;
    String FatherVillageSt,FatherNameSt,FatherIdSt, MotherVillageSt,MotherCastSt,MotherNameSt,MotherIdSt;
    String CurrentPartnerVillageSt,CurrentPartnerNameSt,CurrentPartnerCastSt,CurrentPartnerIdSt;
    String FirstRelationKey,FirstRelationValue,FirstRelationId;
    boolean isAlive = true,isMale,
            isSingle = false,
            isMarried = false,
            isDivorced = false,
            isWidow = false,
            isMarriedAfterPartnerDeath = false,
            isMarriedAfterDivorcedWithPartner = false;

    String EditingKey,EducationSt;

    @Override
    protected void onStart() {
        super.onStart();
        ST.ShowProgress(this);
        ST.CompleteDataSingle(Mid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    FillPreData(documentSnapshot);
                    ST.HideProgress();
                }else{
                    ST.HideProgress();
                    ST.ProblemCause(ViewSingle.this);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ST.HideProgress();
                ST.ProblemCause(ViewSingle.this);
            }
        });

    }
    private void FillPreData(DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.contains(ST.PROFILE_PIC)){
            try{
                Glide.with(ViewSingle.this)
                        .load((String)documentSnapshot.get(ST.PROFILE_PIC))
                        .into(ProfilePhoto);
            }catch (Exception e){
                Toast.makeText(this, getString(R.string.photo_not_there), Toast.LENGTH_SHORT).show();
            }
            PhotoURLSt = (String)documentSnapshot.get(ST.PROFILE_PIC);
        }
        if (documentSnapshot.contains(ST.MEMBER_NAME)){
            MemberNameSt = (String) documentSnapshot.get(ST.MEMBER_NAME);
            MName.setText(MemberNameSt);
        }
        if (documentSnapshot.contains(ST.EDUCATION_STATUS)){
            EducationSt = (String) documentSnapshot.get(ST.EDUCATION_STATUS);
            MEducation.setText(EducationSt);
        }
        if (documentSnapshot.contains(ST.IS_MALE)){
            isMale = (boolean) documentSnapshot.get(ST.IS_MALE);
            Gender.setText(isMale?getString(R.string.purush):getString(R.string.mahila));
        }
        if (documentSnapshot.contains(ST.DOB)){
            DOB.setText(ST.DateToString((Date) documentSnapshot.get(ST.DOB)));
            BirthDate = (Date) documentSnapshot.get(ST.DOB);
        }
        if (documentSnapshot.contains(ST.IS_ALIVE)){
            isAlive = (boolean) documentSnapshot.get(ST.IS_ALIVE);
            if (isAlive){
                DOD.setVisibility(View.GONE);
                DODName.setVisibility(View.GONE);
            }else{
                DOD.setVisibility(View.VISIBLE);
                DODName.setVisibility(View.VISIBLE);
            }
        }
        if (documentSnapshot.contains(ST.DOD)){
            DOD.setText(ST.DateToString((Date) documentSnapshot.get(ST.DOD)));
            DOD.setVisibility(View.VISIBLE);
            DODName.setVisibility(View.VISIBLE);
        }
        if (documentSnapshot.contains(ST.STATE)){
            MStateSt = (String)documentSnapshot.get(ST.STATE);
            MState.setText(MStateSt);
        }
        if (documentSnapshot.contains(ST.DISTRICT)){
            MDistrictSt = (String) documentSnapshot.get(ST.DISTRICT);
            MDistrict.setText(MDistrictSt);
        }
        if (documentSnapshot.contains(ST.TAHSIL)){
            MTahsilSt = (String) documentSnapshot.get(ST.TAHSIL);
            MTahsil.setText(MTahsilSt);
        }
        if (documentSnapshot.contains(ST.VILLAGE)){
            MotherVillageSt = (String) documentSnapshot.get(ST.VILLAGE);
            MMotherVillage.setText(MotherVillageSt);
        }
        if (documentSnapshot.contains(ST.PRIMARY_MOBILE_NO)){
            PrimaryMobileSt = (String) documentSnapshot.get(ST.PRIMARY_MOBILE_NO);
            Mobile.setText(PrimaryMobileSt);
        }
        if (documentSnapshot.contains(ST.SECONDARY_MOBILE_NO)){
            SecondaryMobileSt = (String) documentSnapshot.get(ST.SECONDARY_MOBILE_NO);
            Mobile.setText(Mobile.getText().toString()+"\n"+SecondaryMobileSt);
        }
        if (documentSnapshot.contains(ST.CAST)){
            MotherCastSt = (String) documentSnapshot.get(ST.CAST);
            MMotherCast.setText(MotherCastSt);
        }
        if (documentSnapshot.contains(ST.WORK)){
            WorkList = (ArrayList<String>) documentSnapshot.get(ST.WORK);

            String list = "";
            for (int i = 0; i < WorkList.size(); i++){

                list = list + WorkList.get(i) + ", ";
            }
            Work.setText(list);
        }
        if (documentSnapshot.contains(ST.FATHER_VILLAGE)){
            FatherVillageSt = (String) documentSnapshot.get(ST.FATHER_VILLAGE);
            MFatherVillage.setText(FatherVillageSt);
        }
        if (documentSnapshot.contains(ST.FATHER_NAME)){
            FatherNameSt = (String) documentSnapshot.get(ST.FATHER_NAME);
            MFatherName.setText(FatherNameSt);
        }
        if (documentSnapshot.contains(ST.FATHER_ID)){
            FatherIdSt = (String) documentSnapshot.get(ST.FATHER_ID);
        }
        if (documentSnapshot.contains(ST.MOTHER_VILLAGE)){
            MotherVillageSt = (String) documentSnapshot.get(ST.MOTHER_VILLAGE);
            MMotherVillage.setText(MotherVillageSt);
        }
        if (documentSnapshot.contains(ST.MOTHER_CAST)){
            MotherCastSt = (String) documentSnapshot.get(ST.MOTHER_CAST);
            MMotherCast.setText(MotherCastSt);
        }
        if (documentSnapshot.contains(ST.MOTHER_NAME)){
            MotherNameSt = (String) documentSnapshot.get(ST.MOTHER_NAME);
            MMotherName.setText(MotherNameSt);
        }
        if (documentSnapshot.contains(ST.MOTHER_ID)){
            MotherIdSt = (String) documentSnapshot.get(ST.MOTHER_ID);
        }
        if (documentSnapshot.contains(ST.IS_SINGLE)){
            if ((boolean) documentSnapshot.get(ST.IS_SINGLE))
                MarriageStatus.setText(getString(R.string.unmarried));
            isSingle = (boolean) documentSnapshot.get(ST.IS_SINGLE);
        }
        if (documentSnapshot.contains(ST.IS_MARRIED)){
            if ((boolean) documentSnapshot.get(ST.IS_MARRIED))
            {
                MarriageStatus.setText(getString(R.string.married));
                CurrentPartnerCard.setVisibility(View.VISIBLE);
            }
            isMarried = (boolean) documentSnapshot.get(ST.IS_MARRIED);
        }
        if (documentSnapshot.contains(ST.IS_DIVORCED)){
            if ((boolean) documentSnapshot.get(ST.IS_DIVORCED))
            {
                MarriageStatus.setText(getString(R.string.divorced));
                PastPartnerCard.setVisibility(View.VISIBLE);
            }
            isDivorced = (boolean) documentSnapshot.get(ST.IS_DIVORCED);
        }
        if (documentSnapshot.contains(ST.IS_WIDOW)){
            if ((boolean) documentSnapshot.get(ST.IS_WIDOW))
            {
                MarriageStatus.setText(getString(R.string.widow));
                PastPartnerCard.setVisibility(View.VISIBLE);
            }
            isWidow = (boolean) documentSnapshot.get(ST.IS_WIDOW);
        }
        if (documentSnapshot.contains(ST.IS_MARRIED_AFTER_PARTNER_DEATH)){
            if ((boolean) documentSnapshot.get(ST.IS_MARRIED_AFTER_PARTNER_DEATH))
            {
                CurrentPartnerCard.setVisibility(View.VISIBLE);
                MarriageStatus.setText(getString(R.string.married_after_death));
                PastPartnerCard.setVisibility(View.VISIBLE);
            }
            isMarriedAfterPartnerDeath = (boolean) documentSnapshot.get(ST.IS_MARRIED_AFTER_PARTNER_DEATH);
        }
        if (documentSnapshot.contains(ST.IS_MARRIED_AFTER_DIVORCED_WITH_PARTNER)){
            if ((boolean) documentSnapshot.get(ST.IS_MARRIED_AFTER_DIVORCED_WITH_PARTNER))
            {
                CurrentPartnerCard.setVisibility(View.VISIBLE);
                MarriageStatus.setText(getString(R.string.married_after_death));
                PastPartnerCard.setVisibility(View.VISIBLE);
            }
            isMarriedAfterDivorcedWithPartner = (boolean) documentSnapshot.get(ST.IS_MARRIED_AFTER_DIVORCED_WITH_PARTNER);
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_VILLAGE)){
            CurrentPartnerVillageSt = (String) documentSnapshot.get(ST.CURRENT_PARTNER_VILLAGE);
            MCurrentPartnerVillage.setText(CurrentPartnerVillageSt);
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_CAST)){
            CurrentPartnerCastSt = (String) documentSnapshot.get(ST.CURRENT_PARTNER_CAST);
            MCurrentPartnerCast.setText(CurrentPartnerCastSt);
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_NAME)){
            CurrentPartnerNameSt = (String) documentSnapshot.get(ST.CURRENT_PARTNER_NAME);
            MCurrentPartnerName.setText(CurrentPartnerNameSt);
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_MARRIAGE_DATE)){
            CurrentMarriageDate.setText(ST.DateToString((Date) documentSnapshot.get(ST.CURRENT_PARTNER_MARRIAGE_DATE)));
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_ID)){
            CurrentPartnerIdSt = (String) documentSnapshot.get(ST.CURRENT_PARTNER_ID);
        }
        if (documentSnapshot.contains(ST.CURRENT_PARTNER_CHILD_LIST)){
            ArrayList<Map<String,Object>> CloudCurrentPartnerChildList = (ArrayList<Map<String, Object>>) documentSnapshot.get(ST.CURRENT_PARTNER_CHILD_LIST);

            childListItems.clear();
            for (int i = 0; i < CloudCurrentPartnerChildList.size(); i++){
                SingleChildListItem item = new SingleChildListItem(
                        (String)CloudCurrentPartnerChildList.get(i).get(ST.CHILD_ID),
                        (String)CloudCurrentPartnerChildList.get(i).get(ST.CHILD_NAME),
                        (String)CloudCurrentPartnerChildList.get(i).get(ST.CHILD_VILLAGE),
                        (boolean)CloudCurrentPartnerChildList.get(i).get(ST.CHILD_IS_MALE)
                );
                childListItems.add(item);
            }
            CurrentPartnerChildListAdapter.notifyDataSetChanged();
            ST.setListViewHeightBasedOnChildren(CurrentPartnerChildList);
            CurrentPartnerChildListAdapter.notifyDataSetChanged();
        }
        if (documentSnapshot.contains(ST.PAST_PARTNER_LIST)){
            ArrayList<Map<String, Object>> CloudPastPartnerList = (ArrayList<Map<String, Object>>) documentSnapshot.get(ST.PAST_PARTNER_LIST);

            pastPartnerList.clear();
            for (int i = 0; i < CloudPastPartnerList.size(); i++){
                ArrayList<SingleChildListItem> PastPartnerChildList = new ArrayList<>();
                try{
                    ArrayList<Map<String,Object>> CloudCurrentPartnerChildList = (ArrayList<Map<String, Object>>) CloudPastPartnerList.get(i).get("PastPartnerChildList");

                    for (int j = 0; j < CloudCurrentPartnerChildList.size(); j++){
                        SingleChildListItem iitem = new SingleChildListItem(
                                (String) CloudCurrentPartnerChildList.get(j).get(ST.CHILD_ID),
                                (String) CloudCurrentPartnerChildList.get(j).get(ST.CHILD_NAME),
                                (String) CloudCurrentPartnerChildList.get(j).get(ST.CHILD_VILLAGE),
                                (boolean) CloudCurrentPartnerChildList.get(j).get(ST.CHILD_IS_MALE)
                        );
                        PastPartnerChildList.add(iitem);
                    }

                }catch (Exception e){}

                SinglePartnerListItem item = new SinglePartnerListItem(
                        (String) CloudPastPartnerList.get(i).get(ST.PAST_PARTNER_ID),
                        (String) CloudPastPartnerList.get(i).get(ST.PAST_PARTNER_NAME),
                        (String) CloudPastPartnerList.get(i).get(ST.PAST_PARTNER_CAST),
                        (String) CloudPastPartnerList.get(i).get(ST.PAST_PARTNER_VILLAGE),
                        (Date) CloudPastPartnerList.get(i).get(ST.PAST_PARTNER_MARRIAGE_DATE),
                        PastPartnerChildList
                );
                pastPartnerList.add(item);
            }
            PastPartnerListAdapter.notifyDataSetChanged();
            ST.setListViewHeightBasedOnPartner(PastPartnersList);
            PastPartnerListAdapter.notifyDataSetChanged();

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
            editorList = (ArrayList<String>) documentSnapshot.get(ST.EDITORS_LIST);
        }
        if (documentSnapshot.contains(ST.EDITING_KEY)){
            EditingKey = (String) documentSnapshot.get(ST.EDITING_KEY);
        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_UID)){

        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_ID)){

        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_NAME)){

        }
        if (documentSnapshot.contains(ST.LAST_EDITED_BY_MOBILE_NUMBER)){
            LastEdited.setText((String)documentSnapshot.get(ST.LAST_EDITED_BY_MOBILE_NUMBER));
        }
    }


    public void MemberCast(View view){
        CastSearchOpen(MCastSt);
    }
    public void VillageClick(View view){
        VillageSearchOpen(MVillageSt);
    }
    public void TahsilClick(View view){
        TahsilSearchOpen(MTahsilSt);
    }
    public void DistrictClick(View view){
        DistrictSearchOpen(MDistrictSt);
    }
    public void StateClick(View view){
        StateSearchOpen(MStateSt);
    }
    public void CPNameClick(View view){
        PersonViewOpen(CurrentPartnerIdSt);
    }
    public void CPVillageClick(View view){
        VillageSearchOpen(CurrentPartnerVillageSt);
    }
    public void CPCastClick(View view){
        CastSearchOpen(CurrentPartnerCastSt);
    }
    public void FnameClick(View view){
        PersonViewOpen(FatherIdSt);
    }
    public void FVilalgeClick(View view){
        VillageSearchOpen(FatherVillageSt);
    }
    public void MNameClick(View view){
        PersonViewOpen(MotherIdSt);
    }
    public void MCastClick(View view){
        CastSearchOpen(MotherCastSt);
    }
    public void MVillageClick(View view){
        VillageSearchOpen(MotherVillageSt);
    }

    public void PersonViewOpen(String Pid){
        Intent intent = new Intent(ViewSingle.this,ViewSingle.class);
        intent.putExtra(ST.MEMBER_ID,Pid);
        startActivity(intent);
    }
    public void CastSearchOpen(String Cast){
        Intent intent = new Intent(ViewSingle.this,ViewSingle.class);
        intent.putExtra(ST.CAST,Cast);
        startActivity(intent);
    }
    public void VillageSearchOpen(String Village){
        Intent intent = new Intent(ViewSingle.this,ViewSingle.class);
        intent.putExtra(ST.VILLAGE,Village);
        startActivity(intent);
    }
    public void TahsilSearchOpen(String Tahsil){
        Intent intent = new Intent(ViewSingle.this,ViewSingle.class);
        intent.putExtra(ST.TAHSIL,Tahsil);
        startActivity(intent);
    }
    public void DistrictSearchOpen(String District){
        Intent intent = new Intent(ViewSingle.this,ViewSingle.class);
        intent.putExtra(ST.DISTRICT,District);
        startActivity(intent);
    }
    public void StateSearchOpen(String State){
        Intent intent = new Intent(ViewSingle.this,ViewSingle.class);
        intent.putExtra(ST.STATE,State);
        startActivity(intent);
    }

    String EnteredEditingKey;
    EditText inputEditingKey;
    public void EditMember(View view){
        if (editorList.contains(ST.mUid)){
            startEditing();
            return;
        }
        EnteredEditingKey = "";
        inputEditingKey = new EditText(this);
        inputEditingKey.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.editing_key));
        builder.setView(inputEditingKey);
        builder.setPositiveButton(getString(R.string.thik), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EnteredEditingKey = inputEditingKey.getText().toString();
                if (EnteredEditingKey.equals(EditingKey)){
                    startEditing();
                }
                else{
                    ST.ShowDialog(ViewSingle.this,getString(R.string.wrong_input));
                }

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();

    }
    public void startEditing(){
        Intent intent = new Intent(ViewSingle.this,EditActivity.class);
        intent.putExtra(ST.MEMBER_ID,Mid);
        startActivity(intent);
    }
    public void SupportComplain(View view){
        startActivity(new Intent(this, SupportAndComplainActivity.class));
    }
    public void ShareIt(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,MemberNameSt+"\n"+getString(R.string.app_name)+"\n"+"http://www.dhanawanshisamaj.com/"+Mid);
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }
    public void SaveToList(View view){
        Map<String, Object> SavedMap = new HashMap<>();

        SavedMap.put(ST.MEMBER_ID,Mid);
        SavedMap.put(ST.MEMBER_NAME,MemberNameSt);
        SavedMap.put(ST.FATHER_NAME,FatherNameSt);
        SavedMap.put(ST.CAST,MCastSt);
        SavedMap.put(ST.VILLAGE,MVillageSt);
        SavedMap.put(ST.TAHSIL,MTahsilSt);
        SavedMap.put(ST.DISTRICT,MDistrictSt);
        SavedMap.put(ST.STATE,MStateSt);
        SavedMap.put(ST.EDUCATION_STATUS,EducationSt);
        SavedMap.put(ST.WORK,WorkList);
        SavedMap.put(ST.DOB,BirthDate);

        ST.SavedSingle(Mid).set(SavedMap);
        ST.ShowDialog(ViewSingle.this,getString(R.string.saved));
        
    }
}
