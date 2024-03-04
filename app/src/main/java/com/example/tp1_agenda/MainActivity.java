package com.example.tp1_agenda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText eventEditText;
    private Button addEventButton;
    private TextView eventTextView;
    private TextView allEventsTextView;

    private HashMap<Long, String> eventsMap;
    private List<Event> allEventsList;
    private long selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        eventEditText = findViewById(R.id.eventEditText);
        addEventButton = findViewById(R.id.addEventButton);
        eventTextView = findViewById(R.id.eventTextView);
        allEventsTextView = findViewById(R.id.allEventsTextView);


        eventsMap = new HashMap<>();
        allEventsList = new ArrayList<>();


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = getDateInMillis(year, month, dayOfMonth);
                Event event = findEventByDate(selectedDate);
                if (event != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = sdf.format(new Date(event.getDateInMillis()));
                    eventTextView.setText("Événement pour le " + formattedDate + " : " + event.getEventName());
                } else {
                    eventTextView.setText("Pas d'événement pour la date sélectionnée");
                }
            }
        });
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventName = eventEditText.getText().toString().trim();

                if (!eventName.isEmpty()) {
                    Event newEvent = new Event(selectedDate, eventName);
                    eventsMap.put(selectedDate, String.valueOf(newEvent));
                    allEventsList.add(newEvent);
                    eventEditText.setText("");
                    eventTextView.setText("Événement ajouté pour le " + getFormattedDate(selectedDate));
                    updateAllEventsTextView();
                } else {
                    eventTextView.setText("Veuillez entrer un événement");
                }
            }
        });
        updateAllEventsTextView();

    }

    private void updateAllEventsTextView() {
        StringBuilder allEventsText = new StringBuilder("Tous les événements :\n");
        for (Event event : allEventsList) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = sdf.format(event.getDateInMillis());
            allEventsText.append("- ").append(formattedDate).append(": ").append(event.getEventName()).append("\n");
        }
        allEventsTextView.setText(allEventsText.toString());
    }

    private long getDateInMillis(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTimeInMillis();
    }

    private Event findEventByDate(long dateInMillis) {
        for (Event event : allEventsList) {
            if (event.getDateInMillis() == dateInMillis) {
                return event;
            }
        }
        return null;
    }

    private String getFormattedDate(long dateInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(new Date(dateInMillis));
    }

}