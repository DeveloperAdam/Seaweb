package techease.com.seaweb.Activities.Activities;

import java.util.Calendar;

public interface CalendarListener {

    void onFirstDateSelected(Calendar startDate);
    void onDateRangeSelected(Calendar startDate, Calendar endDate);
}
