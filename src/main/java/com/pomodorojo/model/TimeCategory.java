package com.pomodorojo.model;

import java.io.Serializable;
import java.util.Date;

public record TimeCategory(String categoryName,Date dateCreated) implements Serializable {


    public String getCategoryString(){
        return this.categoryName;
    }

}
