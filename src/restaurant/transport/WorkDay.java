package restaurant.transport;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class WorkDay implements Serializable {
    private DayOfWeek dayOfWeek;
    private Date startHour;
    private Date endHour;

    public WorkDay(DayOfWeek dayOfWeek, Date startHour, Date endHour) {
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}
