package com.pomodorojo.model;

import javafx.scene.input.KeyCode;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import java.util.prefs.*;

public class ProgramSettings {

    static String systemRoot = "prog";
    static String userRoot = "user";
    static Preferences systemPreferences = Preferences.systemRoot().node(systemRoot);
    static Preferences userPreferences = Preferences.userRoot().node(userRoot);
    private static final String BACKING_STORE_AVAIL = "BackingStoreAvail";

    private ArrayList<URI> soundResolve; // the path to the sound resource
    private static final String BOOL_DOWNLOAD_WLAN = "bool_download_wlan";
    private static final String BOOL_DOWNLOAD_LTE = "bool_download_lte";
    private static final String BOOL_TIME_UNIT_ADDED = "bool_time_unit_added";

    private static final String KEYCODE_PAUSE_KEYMAP = "keycode_space_keymap";

    private boolean areOfflineSettingsChanged;

    private  boolean useProgramOffline;




    public ProgramSettings(){
        loadOfflineSettings();
        if (!backingStoreAvailable()){
            // use the offline mode with basic settings
        }
        else{
            loadSettings();
        }
    }

    public void loadOfflineSettings(){
        userPreferences.putBoolean(BOOL_DOWNLOAD_WLAN,true);
        userPreferences.putBoolean(BOOL_DOWNLOAD_LTE,false);
        userPreferences.putBoolean(BOOL_TIME_UNIT_ADDED,true);
        userPreferences.put(KEYCODE_PAUSE_KEYMAP, KeyCode.SPACE.getName());
    }

    public void loadSettings(){// smart way to load settings?

    }


    private static boolean backingStoreAvailable() {
        Preferences prefs = Preferences.userRoot().node("<temporary>");
        try {
            boolean oldValue = prefs.getBoolean(BACKING_STORE_AVAIL, false);
            prefs.putBoolean(BACKING_STORE_AVAIL, !oldValue);
            prefs.flush();
        } catch(BackingStoreException e) {
            return false;
        }
        return true;
    }
}
