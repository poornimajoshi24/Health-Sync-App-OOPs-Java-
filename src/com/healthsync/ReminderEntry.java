package com.healthsync;
public class ReminderEntry {
    private String message;
    private String time;
    public ReminderEntry(String message, String time){ this.message=message; this.time=time; }
    public String toString(){ return message + " at " + time; }
}
