package com.sanshy.dhanawanshi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ST {
    public static final String MEMBER_ID = "MemberId";
    public static final String TAHSILS = "Tahsils";
    public static final String DISTRICTS = "Districts";
    public static final String VILLAGES = "Villages";
    public static final String MY_LIST = "MyList";
    public static final String LOCATIONS = "Locations";
    public static final String CASTS = "Casts";
    public static final String SUGGETIONS = "Suggetions";
    public static final String COMPLETE_DATA_LIST = "CompleteDataList";
    public static final String SEARCH_DATA_LIST = "SearchDataList";
    public static final String MY_MEMBER_LIST = "MyMemberList";
    public static final String NOTIFICATIONS = "Notifications";
    public static final String DONATIONS = "Donations";
    public static final String SAVED_LIST = "SavedList";

    public static FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    public static String mUid = ST.currentUser!=null?ST.currentUser.getUid():null;

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static CollectionReference LocRef = db.collection(LOCATIONS);
    public static CollectionReference CastRef = db.collection(CASTS);
    public static CollectionReference SuggetionList = db.collection(SUGGETIONS);
    public static CollectionReference SearchDataList = db.collection(SEARCH_DATA_LIST);
    public static CollectionReference CompleteDataList = db.collection(COMPLETE_DATA_LIST);
    public static CollectionReference MyMembersList = db.collection(MY_MEMBER_LIST);
    public static CollectionReference SavedList = db.collection(SAVED_LIST);
    public static CollectionReference NotificationList = db.collection(NOTIFICATIONS);
    public static CollectionReference DonationList = db.collection(DONATIONS);

    public static DocumentReference SuggetionSingle(String id){
        return SuggetionList.document(id);
    }
    public static DocumentReference SearchDataSingle(String id){
        return SearchDataList.document(id);
    }
    public static DocumentReference CompleteDataSingle(String id){
        return CompleteDataList.document(id);
    }
    public static DocumentReference MyMemberSingle(String id){
        return MyMembersList.document(id);
    }
    public static DocumentReference SavedSingle(String id){
        return SavedList.document(id);
    }
    public static DocumentReference NotificationSingle(String id){
        return NotificationList.document(id);
    }
    public static DocumentReference DonationSingle(String id){
        return DonationList.document(id);
    }

    public static final String STATES = "States";
    public static final String CAST_1 = "Cast1";

    public static DocumentReference CastList = CastRef.document(CAST_1);
    public static DocumentReference Villages = LocRef.document(VILLAGES);
    public static DocumentReference StateList = LocRef.document(STATES);

    public static DocumentReference DistricList(String StateSt){
        return LocRef.document(STATES).collection(StateSt).document(DISTRICTS);
    }
    public static DocumentReference TahsilList(String StateSt,String DistrictSt){
        return LocRef.document(STATES).collection(StateSt).document(DISTRICTS).collection(DistrictSt).document(TAHSILS);
    }
    public static DocumentReference VillageList(String StateSt,String DistrictSt,String TahsilSt){
        return LocRef.document(STATES).collection(StateSt).document(DISTRICTS).collection(DistrictSt).document(TAHSILS).collection(TahsilSt).document(VILLAGES);
    }


    public static int fday = 0;
    public static int fmonth = 0;
    public static int fYear = 0;
    public static Date staticDate = new Date();
    public static boolean ChooseDateDialog(final Context context, final TextView dateShow){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        fday = dayOfMonth;
                        fmonth = monthOfYear;
                        fYear = year;

                        Date currentDate = new Date();
                        SimpleDateFormat cHour = new SimpleDateFormat("hh");
                        int hour = Integer.parseInt(cHour.format(currentDate));

                        SimpleDateFormat cMin = new SimpleDateFormat("mm");
                        int min = Integer.parseInt(cMin.format(currentDate));

                        SimpleDateFormat cSecond = new SimpleDateFormat("ss");
                        int sec = Integer.parseInt(cSecond.format(currentDate));

                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(0);
                        cal.set(fYear,fmonth++, fday, 0, 0, 0);
                        Date chosenDate = cal.getTime();

                        dateShow.setText(fday+"/"+fmonth+"/"+fYear);
                        staticDate = chosenDate;

                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();

        return true;
    }

    public static String DateToString(Date date){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        return myFormat.format(date);
    }

    public static ProgressDialog mProgress;
    public static void ShowProgress(Context context){
        mProgress = new ProgressDialog(context);
        mProgress.setMessage(context.getString(R.string.loading));
        mProgress.show();
    }
    public static void HideProgress(){
        mProgress.hide();
    }
    public static void setSnipper(Activity activity, AutoCompleteTextView ACTV, ArrayList<String> List){
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_dropdown_item,List);
        ACTV.setAdapter(Adapter);
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight() - view.getMeasuredHeight()*2/5;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public static void setListViewHeightBasedOnPartner(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight() - view.getMeasuredHeight()/3;
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    public static String DateTimeString(Date date){
        return DateFormat.getDateTimeInstance().format(date);
    }
    public static void ShowDialog(Context context,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(msg);
        builder.setPositiveButton(context.getText(R.string.thik), null);

        builder.create().show();
    }
    public static void FillInput(Context context){
        ST.ShowDialog(context,context.getString(R.string.please_fill_all_inputs));
    }
}
