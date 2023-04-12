package com.pomodorojo.controller;

import com.pomodorojo.model.ClientType;
import com.pomodorojo.model.PomoData;
import com.pomodorojo.model.PomoHistory;
import com.pomodorojo.model.TimeCategory;


import java.io.*;
import java.nio.file.*;

public class StateController {
    private final PomoController pomoController;
    private Path programStateStorageDirectory;

    private Path programCacheDir;
    private FileSystem fileSystem;
    private WatchKey dirWatchKey;

    private boolean restoreState = false;


    private static final String statePath = "pomoState.bin";
    private static final String historyPath = "pomoHistory.bin";
    public StateController(PomoController pomoController){
        this.pomoController = pomoController;
        String homeDirectory;
        if (pomoController.getClientController().getClientType() == ClientType.PCCLIENTWINDOWS10){
            homeDirectory = System.getenv("APPDATA");
            System.out.println(homeDirectory);
        }
        else{
            homeDirectory = System.getProperty("user.home");
        }
        this.fileSystem = FileSystems.getDefault();
        this.programStateStorageDirectory = fileSystem.getPath( homeDirectory,"joPomodoro");
        System.out.println(this.programStateStorageDirectory);
        try {
            this.programStateStorageDirectory.toFile().mkdir();
        }
        catch (SecurityException e){
            // TODO implement behaviour for no available security rights
        }
        File[] existingFiles = this.programStateStorageDirectory.toFile().listFiles();
        for (File existingFile : existingFiles) {
            System.out.println(existingFile.toString());
        }
        //TODO watch for changes of the files and not creatable dirs
//        try {
//            dirWatchKey = this.programStateStorageDirectory.register(fileSystem.newWatchService(), StandardWatchEventKinds.ENTRY_MODIFY);
//            dirWatchKey.
//        }
//        catch (IOException e){
            // should not be thrown by the default provider
        this.programCacheDir = fileSystem.getPath(this.programStateStorageDirectory.toString(),"cache");
        try {
            this.programCacheDir.toFile().mkdir();
        }
        catch (SecurityException e){
            // TODO implement behaviour for no available security rights
        }

    }

    /**
     * the necessary part of the offline model gets loaded if none is found a new Model is created
     * attempts to load previous states of the PomoData application and creates it for the PomoController
     */
    public void loadState(){
        System.out.println("loading the state");
        loadProgramState();
        loadPomoHistory();
    }

    private void loadProgramState(){
        // TODO load some of the state from the online database, compare versions
        PomoData curPomoData = null;
        if (restoreState) {
            File[] cacheFiles = this.programCacheDir.toFile().listFiles();
            if (cacheFiles.length == 0) {
                // no safe state can be loaded
            } else {// attempt to de-serialize
                try {
                    FileInputStream fInStream = new FileInputStream(new File(this.programCacheDir.toString(), statePath));
                    BufferedInputStream bufferedFileOutStream = new BufferedInputStream(fInStream);
                    ObjectInputStream inputStream = new ObjectInputStream(bufferedFileOutStream);
                    curPomoData = (PomoData) inputStream.readObject();
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("could not read a state");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (curPomoData == null){
            System.out.println("creating a new state");
            curPomoData = new PomoData();
        }

        this.pomoController.setPomoData(curPomoData);
        for (TimeCategory timeCategory : curPomoData.getTimeCategories()){
            System.out.println(timeCategory.toString());
        }
        System.out.println(curPomoData.getCurrentTimeCategoryProperty().get());

    }

    private void loadPomoHistory(){
        PomoHistory curPomoHistory = null;
        if (restoreState) {
            try {
                FileInputStream fInStream = new FileInputStream(new File(this.programCacheDir.toString(), historyPath));
                BufferedInputStream bufferedFileOutStream = new BufferedInputStream(fInStream);
                ObjectInputStream inputStream = new ObjectInputStream(bufferedFileOutStream);
                curPomoHistory = (PomoHistory) inputStream.readObject();
                inputStream.close();
            } catch (IOException e) {
                System.err.println("could not read a history");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        if (curPomoHistory == null){
            curPomoHistory = new PomoHistory();
        }
        this.pomoController.setPomoHistory(curPomoHistory);
    }

    public void safeState(){
        if (!restoreState){
            return;
        }
        System.out.println("saving the state");
        safeProgramState();
        safePomoHistory();

    }

    private void safeProgramState(){
        try {
            FileOutputStream fOutStream = new FileOutputStream(new File(this.programCacheDir.toString(),statePath));
            BufferedOutputStream bufferedFileOutStream = new BufferedOutputStream(fOutStream);
            ObjectOutputStream outputStream = new ObjectOutputStream(bufferedFileOutStream);
            outputStream.writeObject(this.pomoController.getPomoData());
            outputStream.close();
        }
        catch (IOException e){
            System.err.println("could not safe the state"); // TODO proper error handling
        }
    }

    private void safePomoHistory(){
        try {
            FileOutputStream fOutStream = new FileOutputStream(new File(this.programCacheDir.toString(),historyPath));
            BufferedOutputStream bufferedFileOutStream = new BufferedOutputStream(fOutStream);
            ObjectOutputStream outputStream = new ObjectOutputStream(bufferedFileOutStream);
            outputStream.writeObject(this.pomoController.getPomoHistory());
            outputStream.close();
        }
        catch (IOException e){
            System.err.println("could not safe the state"); // TODO proper error handling
        }
    }


}
