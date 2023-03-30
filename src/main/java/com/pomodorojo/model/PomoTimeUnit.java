package com.pomodorojo.model;

import java.io.Serializable;

public class PomoTimeUnit implements Serializable {

        private PomoCalendar pomoCalendar;

        private TimeDescription timeDescription;
        private int curMaxTime;

        public PomoTimeUnit(int curMaxTime){
            this.curMaxTime = curMaxTime;
        }

}
