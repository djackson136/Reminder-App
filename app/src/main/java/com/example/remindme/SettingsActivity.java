package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initListButton();
        initSettingsButton();
        initSettings();
        initSortByClick();
        initSortOrderClick();
    }

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ReminderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setEnabled(false);
    }

    private void initSettings() {
        String sortBy = getSharedPreferences("MyReminderPreferences",
                Context.MODE_PRIVATE).getString("sortby", "subject");
        String sortOrder = getSharedPreferences("MyReminderPreferences",
                Context.MODE_PRIVATE).getString("sortorder", "ASC");

        RadioButton rbSubject = findViewById(R.id.radioSubject);
        RadioButton rbPriority = findViewById(R.id.radioPriority);
        RadioButton rbSaveDate = findViewById(R.id.radioDate);
        if (sortBy.equalsIgnoreCase("subject")) {
            rbSubject.setChecked(true);
        } else if (sortBy.equalsIgnoreCase("priority")) {
            rbPriority.setChecked(true);
        } else {
            rbSaveDate.setChecked(true);
        }

        RadioButton rbAscending = findViewById(R.id.radioASC);
        RadioButton rbDescending = findViewById(R.id.radioDESC);
        if (sortOrder.equalsIgnoreCase("ASC")) {
            rbAscending.setChecked(true);
        } else {
            rbDescending.setChecked(true);
        }
    }

    private void initSortByClick() {
        RadioGroup rgSortBy = findViewById(R.id.sortByRadioGroup);
        rgSortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbSubject = findViewById(R.id.radioSubject);
                RadioButton rbPriority = findViewById(R.id.radioPriority);
                if (rbSubject.isChecked()) {
                    getSharedPreferences("MyReminderPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortby", "subject").apply();
                } else if (rbPriority.isChecked()) {
                    getSharedPreferences("MyReminderPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortby", "priority").apply();
                } else {
                    getSharedPreferences("MyReminderPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortby", "savedate").apply();
                }
            }
        });
    }

    private void initSortOrderClick() {
        RadioGroup rgSortOrder = findViewById(R.id.sortOrderRadioGroup);
        rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbAscending = findViewById(R.id.radioASC);
                if (rbAscending.isChecked()) {
                    getSharedPreferences("MyReminderPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "ASC").apply();
                } else {
                    getSharedPreferences("MyReminderPreferences",
                            Context.MODE_PRIVATE).edit().putString("sortorder", "DESC").apply();
                }
            }
        });
    }

}