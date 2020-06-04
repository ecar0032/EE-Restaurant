package com.example.eerestaurant;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

//Adapter and Cursor creator for the layout bookingslayout.

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.BookingHolder> {
    private Context mContext;
    private Cursor mCursor;

    public BookingsAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    public class BookingHolder extends RecyclerView.ViewHolder{
        public TextView fullnameText;
        public TextView usernameText;
        public TextView peopleText;
        public TextView phoneText;
        public TextView dateText;
        public TextView timeText;

        public BookingHolder(@NonNull View itemView) {
            super(itemView);

            fullnameText = itemView.findViewById(R.id.fullnametxt);
            usernameText = itemView.findViewById(R.id.usernametxt);
            peopleText = itemView.findViewById(R.id.peopletxt);
            phoneText = itemView.findViewById(R.id.phonetxt);
            dateText = itemView.findViewById(R.id.datetxt);
            timeText = itemView.findViewById(R.id.timetxt);

        }
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.bookingslayout, parent, false);
        return new BookingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String username = mCursor.getString(2);
        String name = mCursor.getString(3);
        String numberOfPeople = mCursor.getString(4);
        String phoneNumber = mCursor.getString(5);
        String date = mCursor.getString(6);
        String time = mCursor.getString(7);

        holder.fullnameText.setText(name);
        holder.usernameText.setText(username);
        holder.peopleText.setText(numberOfPeople);
        holder.phoneText.setText(phoneNumber);
        holder.dateText.setText(date);
        holder.timeText.setText(time);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}
