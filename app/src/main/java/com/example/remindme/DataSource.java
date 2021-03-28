package com.example.remindme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataSource {
        private SQLiteDatabase database;
        private DBHelper dbHelper;

        public DataSource(Context context) {
            dbHelper = new DBHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public boolean insertReminder(Reminder r) {
            boolean didSucceed = false;
            try {
                ContentValues initialValues = new ContentValues();

                initialValues.put("subject", r.getSubject());
                initialValues.put("description", r.getDescription());
                initialValues.put("priority", r.getPriority());
                initialValues.put("savedate", r.getSaveDate());
                didSucceed = database.insert("reminders", null, initialValues) > 0;
            } catch (Exception e) {
                // Do nothing - will return false if there is an exception
            }
            return didSucceed;
        }
        /*
        public boolean updateReminder(Reminder r) {
            boolean didSucceed = false;
            try {
                Long rowId = (long) r.getReminderID();
                ContentValues updateValues = new ContentValues();

                updateValues.put("name", r.getSubject());
                updateValues.put("type", r.getDescription());
                updateValues.put("date", r.getSaveDate());

                didSucceed = database.update("reminders", updateValues, "_id =" + rowId, null) > 0;
            }
            catch (Exception e) {
                // Do nothing - will return false if there is an exception
            }
            return didSucceed;
        }
         */


        public int getLastReminderID() {
            int lastId;
            try {
                String query = "SELECT MAX(_id) FROM reminders";
                Cursor cursor = database.rawQuery(query, null);

                cursor.moveToFirst();
                lastId = cursor.getInt(0);
                cursor.close();
            }
            catch (Exception e) {
                lastId = -1;
            }
            return lastId;
        }

        public ArrayList<Reminder> getReminders(String sortField, String sortOrder) {
            ArrayList<Reminder> reminders = new ArrayList<Reminder>();
            try {
                String query = "SELECT * FROM reminders ORDER BY " + sortField + " " + sortOrder;
                Cursor cursor = database.rawQuery(query, null);

                Reminder newReminder;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    newReminder = new Reminder();
                    newReminder.setReminderID(cursor.getInt(0));
                    newReminder.setSubject(cursor.getString(1));
                    newReminder.setDescription(cursor.getString(2));
                    newReminder.setPriority(cursor.getString(3));
                    newReminder.setSaveDate(cursor.getString(4));

                    reminders.add(newReminder);
                    cursor.moveToNext();
                }
                cursor.close();
            }
            catch (Exception e) {
                reminders = new ArrayList<Reminder>();
            }
            return reminders;
        }
        /*
        public Reminder getSpecificReminder(int reminderId) {
            Reminder reminder = new Reminder();
            String query = "SELECT * FROM reminders WHERE _id =" + reminderId;
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                reminder.setReminderID(cursor.getInt(0));
                reminder.setSubject(cursor.getString(1));
                reminder.setDescription(cursor.getString(2));
                reminder.setSaveDate(cursor.getString(3));

                cursor.close();
            }
            return reminder;
        }
         */

        public boolean deleteReminder (int reminderId) {
            boolean didDelete = false;
            try {
                didDelete = database.delete("reminders", "_id =" + reminderId, null) > 0;
            }
            catch (Exception e) {
            }
            return didDelete;
        }



}
