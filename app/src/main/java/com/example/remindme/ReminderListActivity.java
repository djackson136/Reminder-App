package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;
import java.util.ArrayList;

public class ReminderListActivity extends AppCompatActivity {
    ArrayList<Reminder> reminders;
    Adapter reminderAdapter;
    RecyclerView reminderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        initListButton();
        initSettingsButton();
        initAddReminderButton();
        initDeleteSwitch();
    }

    @Override
    public void onResume() {
        super.onResume();
        String sortBy = getSharedPreferences("ReminderPreferences", Context.MODE_PRIVATE).getString("sortfield", "datesaved");
        String sortOrder = getSharedPreferences("ReminderPreferences", Context.MODE_PRIVATE).getString("sortorder", "ASC");
        DataSource ds = new DataSource(this);
        try {
            ds.open();
            reminders = ds.getReminders(sortBy, sortOrder);
            ds.close();
            if (reminders.size() > 0) {
                reminderList = findViewById(R.id.rvReminders);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                reminderList.setLayoutManager(layoutManager);
                reminderAdapter = new Adapter(reminders, ReminderListActivity.this);
                reminderList.setAdapter(reminderAdapter);
                reminderAdapter.setOnItemClickListener(onItemClickListener);

                // Divider
                RecyclerView.ItemDecoration divider = new DividerItemDecoration(ReminderListActivity.this, DividerItemDecoration.VERTICAL);
                reminderList.addItemDecoration(divider);
            }
            else {
                Intent intent = new Intent(ReminderListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving contacts", Toast.LENGTH_LONG).show();
        }
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder)v.getTag();
            int position = viewHolder.getAdapterPosition();
            int reminderId = reminders.get(position).getReminderID();
            Intent intent = new Intent(ReminderListActivity.this, MainActivity.class);
            intent.putExtra("reminderID", reminderId);
            startActivity(intent);
        }
    };

    private void initListButton() {
        ImageButton ibList = findViewById(R.id.imageButtonList);
        ibList.setEnabled(false);
    }

    private void initSettingsButton() {
        ImageButton ibSettings = findViewById(R.id.imageButtonSettings);
        ibSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderListActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initAddReminderButton() {
        Button newReminder = findViewById(R.id.buttonAddReminder);
        newReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReminderListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initDeleteSwitch() {
        Switch s = findViewById(R.id.switchDelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean status = buttonView.isChecked();
                reminderAdapter.setDelete(status);
                reminderAdapter.notifyDataSetChanged();
            }
        });
    }

}