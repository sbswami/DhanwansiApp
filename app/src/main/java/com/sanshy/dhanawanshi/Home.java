package com.sanshy.dhanawanshi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanshy.dhanawanshi.activities.AboutUsActivity;
import com.sanshy.dhanawanshi.activities.DonatorListActivity;
import com.sanshy.dhanawanshi.activities.FindByLocationActivity;
import com.sanshy.dhanawanshi.activities.MyMemberActivity;
import com.sanshy.dhanawanshi.activities.NotificationActivity;
import com.sanshy.dhanawanshi.activities.SavedListActivity;
import com.sanshy.dhanawanshi.activities.SearchByNameActivity;
import com.sanshy.dhanawanshi.activities.SupportAndComplainActivity;
import com.sanshy.dhanawanshi.activities.WelcomeActivity;

public class Home extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (ST.currentUser==null){
            startActivity(new Intent(this, WelcomeActivity.class));
            this.finish();
        }else{
            ST.MyMembersList = ST.db.collection(ST.mUid).document(ST.MY_MEMBER_LIST).collection(ST.MY_MEMBER_LIST);
            ST.SavedList = ST.db.collection(ST.mUid).document(ST.SAVED_LIST).collection(ST.SAVED_LIST);
            ST.SearchSuggetion = ST.db.collection(ST.mUid).document(ST.SEARCH_DATA_LIST);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void SearchByName(View view){
        startActivity(new Intent(this,SearchByNameActivity.class));
    }

    public void FindByLocation(View view){
        startActivity(new Intent(this,FindByLocationActivity.class));
    }

    public void DonationList(View view){
        startActivity(new Intent(this,DonatorListActivity.class));
    }

    public void NotificationS(View view){
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void SavedList(View view){
        startActivity(new Intent(this,SavedListActivity.class));
    }

    public void MyMembers(View view){
        startActivity(new Intent(this,MyMemberActivity.class));
    }

    public void SupportAndComplaint(View view){
        startActivity(new Intent(this,SupportAndComplainActivity.class));
    }

    public void ShareApp(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.sanshy.dhanawanshi");
        startActivity(Intent.createChooser(intent, getString(R.string.share)));
    }
    public void AboutUs(View view){
        startActivity(new Intent(this,AboutUsActivity.class));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_menu:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,AboutUsActivity.class));
                break;
            case R.id.contact_us_menu:
                Toast.makeText(this, "Contact Us", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,SupportAndComplainActivity.class));
                break;
            case R.id.share_app:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.sanshy.dhanawanshi");
                startActivity(Intent.createChooser(intent, getString(R.string.share)));
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
