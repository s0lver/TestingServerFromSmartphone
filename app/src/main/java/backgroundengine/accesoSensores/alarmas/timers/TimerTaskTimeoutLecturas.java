package backgroundengine.accesoSensores.alarmas.timers;

import backgroundengine.servicios.calendarizables.CalendarizableBase;

import java.util.TimerTask;

public class TimerTaskTimeoutLecturas extends TimerTask
{
    private CalendarizableBase calendarizable;

    public TimerTaskTimeoutLecturas(CalendarizableBase calendarizable) {
        this.calendarizable = calendarizable;
    }

    @Override
    public void run() {
        calendarizable.timeoutLecturas();
    }
}