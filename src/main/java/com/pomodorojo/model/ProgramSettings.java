package com.pomodorojo.model;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class ProgramSettings {
    private ArrayList<URI> soundResolve; // the path to the sound resource

    private boolean downloadWLAN;

    private boolean downloadLTE;

    private boolean areOfflineSettingsChanged;

    private boolean timeUnitVersionAdded;

    private  boolean useProgramOffline;



    public ProgramSettings(){
        loadSettings();
    }

    public void loadSettings(){// smart way to load settings?

    }
}
