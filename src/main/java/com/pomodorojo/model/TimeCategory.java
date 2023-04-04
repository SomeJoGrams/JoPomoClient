package com.pomodorojo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.MenuItem;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public final class TimeCategory extends MenuItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private final StringProperty categoryProperty;
    private final Date dateCreated;

    public TimeCategory(String categoryName, Date dateCreated) {
        super();
//        this.categoryName = categoryName;
        this.categoryProperty = new SimpleStringProperty(categoryName);
        this.dateCreated = dateCreated;
        this.textProperty().bind(categoryProperty); // probably does not have to be a property, but depends how it will be used
    }


    public String getCategoryString() {
        return this.categoryProperty.get();
    }

    public Date dateCreated() {
        return dateCreated;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TimeCategory) obj;
        return Objects.equals(this.categoryProperty, that.categoryProperty) &&
                Objects.equals(this.dateCreated, that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryProperty, dateCreated);
    }

    @Override
    public String toString() {
        return "TimeCategory[" +
                "categoryProperty=" + categoryProperty + ", " +
                "dateCreated=" + dateCreated + ']';
    }


}
