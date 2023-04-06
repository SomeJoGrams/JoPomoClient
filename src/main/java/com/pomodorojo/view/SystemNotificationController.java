package com.pomodorojo.view;


import org.controlsfx.control.Notifications;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.util.Duration;
public class SystemNotificationController {

        private TrayIcon trayIcon;

        public SystemNotificationController(){
            trayIcon = null;
        }
        public static void displayNotification(String text){
            Notifications.create()
                    .title(text)
                    .text("Pomodoro")
                    .hideAfter(Duration.seconds((5)))
                    .darkStyle()
                    .showConfirm();

        }

        public void addSystemTraySymbol(){
            if (SystemTray.isSupported()) {
                // get the SystemTray instance
                SystemTray tray = SystemTray.getSystemTray();
                // load an image
                // TODO get system icon size
                Image image = Toolkit.getDefaultToolkit().getImage(PomoApplication.class.getResource("TimerSymbol16x16.png"));
                // create a action listener to listen for default action executed on the tray icon
                ActionListener listener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // execute default action of the application
                        // ...
                    }
                };
                // create a popup menu
                PopupMenu popup = new PopupMenu();
                // create menu item for the default action
                MenuItem defaultItem = new MenuItem("Menu");
                defaultItem.addActionListener(listener);
                popup.add(defaultItem);
                /// ... add other items
                // construct a TrayIcon
                trayIcon = new TrayIcon(image, "JoPomodoro", popup);
                // set the TrayIcon properties
                trayIcon.addActionListener(listener);
                // ...
                // add the tray image
                try {
                    tray.add(trayIcon);
                } catch (AWTException e) {
                    System.err.println(e);
                }
                // ...
            } else {
                // disable tray option in your application or
                // perform other actions
            }
            // the application state has changed - update the image
//            if (trayIcon != null) {
//                trayIcon.setImage(updatedImage);
//            }
        }
}
