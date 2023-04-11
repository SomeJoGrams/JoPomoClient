package com.pomodorojo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public final class TimeCategory extends MenuItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    private transient StringProperty categoryProperty;
    private Date dateCreated;

    public TimeCategory(String categoryName, Date dateCreated) {
        super();
//        this.categoryName = categoryName;
        this.categoryProperty = new SimpleStringProperty(categoryName);
        this.dateCreated = dateCreated;
        this.textProperty().bind(categoryProperty); // probably does not have to be a property, but depends on how it will be used
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

    /**
     * used to implement the serialization, because the class extends javafx MenuItem
     * @throws IOException
     */
    @Serial
    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException{
        out.defaultWriteObject();
        out.writeObject(categoryProperty.get());
    }
    @Serial
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        this.categoryProperty = new SimpleStringProperty((String)in.readObject());
        this.textProperty().bind(categoryProperty);

    }
    @Serial
    private void readObjectNoData()
            throws ObjectStreamException {
        this.categoryProperty = new SimpleStringProperty("Standard");
    }

}
