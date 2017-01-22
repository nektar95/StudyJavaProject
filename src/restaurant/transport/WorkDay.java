package restaurant.transport;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class WorkDay implements Serializable {
    private DayOfWeek dayOfWeek;
    private LocalTime startHour;
    private LocalTime endHour;

    public WorkDay(DayOfWeek dayOfWeek, LocalTime startHour, LocalTime endHour) {
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return dayOfWeek + ": "+startHour.getHour() + " - " + endHour.getHour();
    }
}
