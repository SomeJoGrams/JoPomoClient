package com.pomodorojo.model;

import java.io.Serializable;
import java.util.Objects;

public class Day implements Serializable,Comparable<Day> {

    public int day;
    public int month;
    public int year;
    public Day(int day, int month,int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public int compareTo(Day o) throws NullPointerException {
        if (o == null){
            throw new NullPointerException("compared day was null");
        }
        if (this.year < o.year){
            return -1;
        }
        else if(this.year > o.year){
            return 1;
        }
        // if not returned until here the years are the same
        if (this.month < o.month){
            return -1;
        }
        else if (this.month > o.month) {
            return 1;
        }
        // if not returned until here the months are the same
        if (this.day < o.day){
            return -1;
        }
        else if(this.day > o.day){
            return 1;
        }
        // every day,month,year attribute is the same
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day1 = (Day) o;
        return day == day1.day && month == day1.month && year == day1.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }
}
