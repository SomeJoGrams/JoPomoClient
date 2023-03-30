package com.pomodorojo.model;

import java.io.Serializable;

public class PomoTimeUnit implements Serializable {

        private PomoCalendar pomoCalendar;

        private TimeDescription timeDescription;
        private int curMaxTime;
        private String category;

        public PomoTimeUnit(int curMaxTime,String category){

            this.curMaxTime = curMaxTime;
            this.category = category;
            this.timeDescription = null;
        }
        public PomoTimeUnit(int curMaxTime,String category,TimeDescription descObj){
            this.curMaxTime = curMaxTime;
            this.category = category;
            this.timeDescription = descObj;
        }


}
