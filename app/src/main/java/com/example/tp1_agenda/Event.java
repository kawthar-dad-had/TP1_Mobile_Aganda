package com.example.tp1_agenda;

// Event.java
public class Event {
    private long dateInMillis;
    private String eventName;

    public Event(long dateInMillis, String eventName) {
        this.dateInMillis = dateInMillis;
        this.eventName = eventName;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public String getEventName() {
        return eventName;
    }
}

