package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Reminder currentReminder = new Reminder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListButton();
        initSettingsButton();
        initSaveButton();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReminderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

   public void checkPriorityButton() {
        RadioGroup rgPriority = findViewById(R.id.priorityRadioGroup);
        int radioId = rgPriority.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(radioId);
        currentReminder.setPriority(radioButton.getText().toString());
    }

    private void initSaveButton() {
        EditText etSubject = findViewById(R.id.editSubject);
        EditText etDescription = findViewById(R.id.editDescription);
        RadioGroup rgPriority = findViewById(R.id.priorityRadioGroup);

        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wasSuccessful;
                DataSource ds = new DataSource(MainActivity.this);
                try {
                    ds.open();
                    currentReminder.setSubject((etSubject.getText().toString()));
                    currentReminder.setDescription((etDescription.getText().toString()));
                    checkPriorityButton();
                    // current date and time
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy, HH:mm");
                    Calendar calendar = Calendar.getInstance();
                    String date = simpleDateFormat.format(calendar.getTime());
                    currentReminder.setSaveDate(date);

                    if (currentReminder.getReminderID() == -1) {
                        wasSuccessful = ds.insertReminder(currentReminder);
                        if (wasSuccessful) {
                            int newId = ds.getLastReminderID();
                            currentReminder.setReminderID(newId);
                        }
                    }
                    /*
                    else {
                        wasSuccessful = ds.updateContact(currentContact);
                    }
                     */
                    ds.close();
                    Intent intent = new Intent(MainActivity.this, ReminderListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                catch (Exception e) {
                    wasSuccessful = false;
                }
            }
        });
    }

}