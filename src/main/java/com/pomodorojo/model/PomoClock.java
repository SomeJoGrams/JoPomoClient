package com.pomodorojo.model;

import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;

public class PomoClock extends Clock implements Serializable {


    @Override
    public ZoneId getZone() {
        return TimeZone.getDefault().toZoneId();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return Clock.system(zone);
    }

    @Override
    public Instant instant() {
        return Instant.now();
    }
}
