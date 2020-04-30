package com.example.mysportclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mysportclub.data.ClubOlympusContract;

public class AddMemberActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportEditText;
    private Spinner genderSpinner;
    private int gender = 0;

    private ArrayAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        firstNameEditText = findViewById(R.id.edit_f_name);
        lastNameEditText = findViewById(R.id.edit_l_name);
        sportEditText = findViewById(R.id.edit_sport);
        genderSpinner = findViewById(R.id.spinner);



        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(spinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedGender = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectedGender)) {

                    switch (selectedGender) {

                        case "Male" : gender = ClubOlympusContract.MemberEntry.GENDER_MALE; break;
                        case "Female" : gender = ClubOlympusContract.MemberEntry.GENDER_FEMALE; break;
                        default: gender = ClubOlympusContract.MemberEntry.GENDER_UNKNOWN;  break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                gender = 0;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {

            case R.id.save_member:

                insertMember();
                return true;
            case R.id.delete_member: return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertMember() {

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = sportEditText.getText().toString().trim();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_FIRST_NAME, firstName);
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_LAST_NAME, lastName);
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_SPORT, sport);
        contentValues.put(ClubOlympusContract.MemberEntry.COLUMN_GENDER, gender);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(ClubOlympusContract.MemberEntry.CONTENT_URI, contentValues);

        if (uri == null) {

            Toast.makeText(this, "Something bad happen OwO", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "DATA saved", Toast.LENGTH_LONG).show();
        }

    }
}
