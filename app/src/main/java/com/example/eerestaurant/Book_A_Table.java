package com.example.eerestaurant;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class Book_A_Table extends Fragment {

    EditText mName, mUsername, mNumberOfPeople, mPhoneNumber;
    Button dateButton, timeButton, mSubmit, mTable1, mTable2, mTable3, mTable4, mTable5, mTable6;
    TextView dateTextView, timeTextView;
    public View generalView;
    public String tableID;
    DBHelper db;
    private String username;

    //Listing the Shared preferences so that the username can be retrieved, and with that information we can pass it to the DBHelper and get information where the username matches. I.e. can get everything
    //associated with that username.
    public static final String SHARED_PREFS = "sharedprefs";
    public static final String USERNAME = "username";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.book_a_table_layout, container, false);
        generalView = view;
        //getData is a method so store the data in the username variable
        getData();

        //Declaring variables
        db = new DBHelper(generalView.getContext());
        Cursor cursor = db.specUserData(username);
        mName = view.findViewById(R.id.name);
        mUsername = view.findViewById(R.id.username);
        mNumberOfPeople = view.findViewById(R.id.numberOfPeople);
        mPhoneNumber = view.findViewById(R.id.phoneNumber);
        mSubmit = view.findViewById(R.id.submit);
        dateButton = view.findViewById(R.id.dateButton);
        timeButton = view.findViewById(R.id.timeButton);
        mTable1 = view.findViewById(R.id.button1);
        mTable2 = view.findViewById(R.id.button2);
        mTable3 = view.findViewById(R.id.button3);
        mTable4 = view.findViewById(R.id.button4);
        mTable5 = view.findViewById(R.id.button5);
        mTable6 = view.findViewById(R.id.button6);
        dateTextView = view.findViewById(R.id.dateText);
        timeTextView = view.findViewById(R.id.timeText);

        //This is a method to fill the fields of Username & Full name directly according to the logged in user by the use of cursors.
        while (cursor.moveToNext()){

            mName.setText(cursor.getString(2));
            mUsername.setText(cursor.getString(1));

        }

        //onClickListeners for both the Date and Time buttons. Methods are called for both of them: "handleDateButton()" & "handleTimeButton()".
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDateButton();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTimeButton();
            }
        });

        //This section will have onClickListeners for each table. if a table is clicked a Toast will show the user the table chosen and change the value of tableID to the name of the table.
        //This means that if he clicks multiple times, the variable keeps overwriting the information within that variable to the most recent one.

        mTable1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableID = mTable1.getText().toString();
                Toast.makeText(generalView.getContext(), "Table 1 Chosen", Toast.LENGTH_SHORT).show();
            }
        });

        mTable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableID = mTable2.getText().toString();
                Toast.makeText(generalView.getContext(), "Table 2 Chosen", Toast.LENGTH_SHORT).show();
            }
        });

        mTable3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableID = mTable3.getText().toString();
                Toast.makeText(generalView.getContext(), "Table 3 Chosen", Toast.LENGTH_SHORT).show();
            }
        });

        mTable4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableID = mTable4.getText().toString();
                Toast.makeText(generalView.getContext(), "Table 4 Chosen", Toast.LENGTH_SHORT).show();
            }
        });

        mTable5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableID = mTable5.getText().toString();
                Toast.makeText(generalView.getContext(), "Table 5 Chosen", Toast.LENGTH_SHORT).show();
            }
        });

        mTable6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableID = mTable6.getText().toString();
                Toast.makeText(generalView.getContext(), "Table 6 Chosen", Toast.LENGTH_SHORT).show();
            }
        });

        //onClickListener of the Submit button, this is where we gather all the information and insert it into the database.
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tabID = tableID;
                String username = mUsername.getText().toString();
                String name = mName.getText().toString();
                String nuOfPeople = mNumberOfPeople.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();
                String date = dateTextView.getText().toString();
                String time = timeTextView.getText().toString();

                //Calling the method add_Booking and giving the information as parameters.
                add_Booking(tabID, username, name, nuOfPeople, phoneNumber, date, time);
            }
        });

        return view;
    }

    //If all goes well the value should never be -1, if it is, then the booking is not stored and an error is displayed. If addBooking gives a number higher than 1 (Depends on how many entries there are)
    //there will be a "Booked!" Toast and the entry will be in the database. This is important to gather information about the booking in the profile fragment.
    public void add_Booking(String tabID,String username,String name,String nuOfPeople,String phoneNumber,String date,String time) {
        if (tabID == null || username == "" || name == "" || nuOfPeople == "" || phoneNumber == "" || date == "Date Selected" || time == "Time Selected") {
            Toast.makeText(generalView.getContext(), "Check for Empty Fields", Toast.LENGTH_SHORT).show();
        } else {
            long value = db.addBooking(tabID, username, name, nuOfPeople, phoneNumber, date, time);
            if (value > 0) {
                Toast.makeText(generalView.getContext(), "Booked!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(generalView.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

        //This is so that the user can choose a time without entering the data himself. This will result in the same layout and is easier to read from the database.
        private void handleTimeButton(){

            Calendar calendar = Calendar.getInstance();
            int HOUR = calendar.get(Calendar.HOUR);
            int MINUTE = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(generalView.getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                    String timeString = hourOfDay + ":" + minute;
                    //Changing the TextView under it to be displayed as so: ##:##
                    timeTextView.setText(timeString);
                }
            }, HOUR, MINUTE, true);

            timePickerDialog.show();

        }

        //This is so that the user can choose a date without entering the data himself. This will result in the same layout and is easier to read from the database.
        private void handleDateButton() {

            Calendar calendar = Calendar.getInstance();

            int YEAR = calendar.get(Calendar.YEAR);
            int MONTH = calendar.get(Calendar.MONTH);
            int DATE =  calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(generalView.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dateString = dayOfMonth + "/" + month + "/" + year;
                    //Changing the TextView under it to be displayed as so: day/month/year
                    dateTextView.setText(dateString);
                }
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        }

        //same method as others that use the shared preferences, this is to get the username that was stored when logged in, and retrieve data according to that
    public void getData(){
        SharedPreferences sharedPreferences = generalView.getContext().getSharedPreferences(SHARED_PREFS, generalView.getContext().MODE_PRIVATE);
        username = sharedPreferences.getString(USERNAME, "");
    }

}


