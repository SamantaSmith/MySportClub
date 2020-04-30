package com.example.mysportclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mysportclub.data.ClubOlympusContract.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);

        FloatingActionButton addMember = findViewById(R.id.add_member);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {

        String[] projection = {

                MemberEntry._ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };

        Cursor cursor = getContentResolver().query(MemberEntry.CONTENT_URI, projection, null, null, null);

        dataTextView.setText("All members\n\n");
        dataTextView.append(MemberEntry._ID + ' ' +
                MemberEntry.COLUMN_FIRST_NAME+ ' ' +
                        MemberEntry.COLUMN_LAST_NAME+ ' ' +
                        MemberEntry.COLUMN_GENDER+ ' ' +
                MemberEntry.COLUMN_SPORT);

        int idIndex = cursor.getColumnIndex(MemberEntry._ID);
        int idFirstName = cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME);
        int idLastName = cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME);
        int idGender = cursor.getColumnIndex(MemberEntry.COLUMN_GENDER);
        int idSport = cursor.getColumnIndex(MemberEntry.COLUMN_SPORT);

        while (cursor.moveToNext()) {

            int currentId = cursor.getInt(idIndex);
            String currentFirstName = cursor.getString(idFirstName);
            String currentLastName = cursor.getString(idLastName);
            int currentGender = cursor.getInt(idGender);
            String currentSport = cursor.getString(idSport);

            dataTextView.append("\n" + currentId + ' ' + currentFirstName + ' ' + currentLastName
             + ' ' + currentGender + ' ' + currentSport);
        }

        cursor.close();


    }
}
