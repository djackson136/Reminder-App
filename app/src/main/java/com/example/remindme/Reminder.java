package com.example.remindme;

import java.util.Calendar;

public class Reminder {
    private int reminderID;
    private String subject;
    private String description;
    private String priority;
    private String saveDate;

    public Reminder() {
        reminderID = -1;
    }

    public int getReminderID() {
        return reminderID;
    }

    public void setReminderID(int reminderID) {
        this.reminderID = reminderID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }


}
