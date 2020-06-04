package com.example.eerestaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Profile extends Fragment {

    //Declaring variables and the Shared Preferences, this is where the preferences are use the most.

    private TextView textView;
    private TextView textView1;
    private ImageView imageView;
    private TextView textView2;
    public View generalView;
    private BookingsAdapter mAdapter;
    DBHelper db;

    private String username;

    public static final String SHARED_PREFS = "sharedprefs";
    public static final String USERNAME = "username";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.profile_layout, container, false);
       generalView = view;
       getData();

       //Creating an instance of DBHelper and Cursor.
       db = new DBHelper(generalView.getContext());
       Cursor cursor = db.specUserData(username);
       Cursor BookingCursor = db.specBookingData(username);

       //To have a RecyclerView you have to have an adapter, so an instance mAdapter of type BookingsAdapter is created.
       RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
       recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
       mAdapter = new BookingsAdapter(getContext(), getAllItems());
       recyclerView.setAdapter(mAdapter);

       textView = (TextView) view.findViewById(R.id.usernameid);
       textView1 = (TextView) view.findViewById(R.id.fullnameid);
       imageView = (ImageView) view.findViewById(R.id.profileimage);
       textView2 = (TextView) view.findViewById(R.id.bookingTitle);

       byte[] image = null;

       //Getting data based off of the username and using the cursor to get the information, textView is the Username while textView1 is the Full Name of the user.
       while (cursor.moveToNext()){
               textView.setText(cursor.getString(1));
               textView1.setText(cursor.getString(2));

               //Getting the Bitmap Image from the database and displaying it as well.
           imageView.setImageBitmap(Utils.getImage(cursor.getBlob(5)));

       }

       return view;
    }

    //getting all the data with the query "username" and showing them. This utilises the specBookingData from DBHelper
    private Cursor getAllItems() { return db.specBookingData(username); }

    //Same method as all other activities that make the use of preferences, getting the data and putting the data found in the username variable.
    public void getData(){
        SharedPreferences sharedPreferences = generalView.getContext().getSharedPreferences(SHARED_PREFS, generalView.getContext().MODE_PRIVATE);
        username = sharedPreferences.getString(USERNAME, "");
    }

}


