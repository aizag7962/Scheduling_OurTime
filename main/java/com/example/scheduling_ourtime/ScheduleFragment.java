package com.example.scheduling_ourtime;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RelativeLayout;


public class ScheduleFragment extends Fragment {

    private forSQLdbHandler dbHandler;
    private EditText editText;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.fragment_schedule, container, false);

        Button myButton = rootView.findViewById(R.id.buttonSave);
        editText = rootView.findViewById(R.id.editText);
        calendarView = rootView.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                ReadDatabase(view);
            }
        });

        try {
            dbHandler = new forSQLdbHandler(getContext(), "CalendarDatabase", null, 1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar (Date TEXT, Event TEXT)");
        } catch (Exception e) {
            e.printStackTrace();
        }


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("Date", selectedDate);
                contentValues.put("Event", editText.getText().toString());
                sqLiteDatabase.insert("EventCalendar", null, contentValues);
            }
        });
        return rootView;
    }

    public void ReadDatabase(View view) {
        String query = "Select Event from EventCalendar where Date =" + selectedDate;
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            editText.setText(cursor.getString(0));
        } catch (Exception e) {
            e.printStackTrace();
            editText.setText("");
        }
    }
}



