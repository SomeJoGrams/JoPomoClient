module com.pomodorojo.view {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.desktop;
    requires java.prefs;

    opens com.pomodorojo.view to javafx.fxml;
    exports com.pomodorojo.view;
}