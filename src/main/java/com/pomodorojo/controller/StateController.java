package com.pomodorojo.controller;

import com.pomodorojo.model.ClientType;
import com.pomodorojo.model.PomoData;


import java.io.*;
import java.nio.file.*;

public class StateController {
    private final PomoController pomoController;
    private Path programStateStorageDirectory;

    private Path programCacheDir;
    private FileSystem fileSystem;
    private WatchKey dirWatchKey;
    public StateController(PomoController pomoController){
        this.pomoController = pomoController;
        String homeDirectory;
        if (pomoController.getPomoData().getClientType() == ClientType.PCCLIENTWINDOWS10){
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
        for (int i = 0; i < existingFiles.length; i++) {
            System.out.println(existingFiles[i].toString());
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
        // TODO load some of the state from the online database, compare versions
        File[] cacheFiles = this.programCacheDir.toFile().listFiles();
        PomoData curPomoData = null;
        if (cacheFiles.length == 0){
            // no safe state can be loaded
        }
        else{// attempt to de-serialize
            try {
                FileInputStream fInStream = new FileInputStream("pomoState.bin");
                BufferedInputStream bufferedFileOutStream = new BufferedInputStream(fInStream);
                ObjectInputStream inputStream = new ObjectInputStream(bufferedFileOutStream);
                curPomoData = (PomoData) inputStream.readObject();
                inputStream.close()
            }
            catch (IOException e){
                System.err.println("could not read a state");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        if (curPomoData == null){
            curPomoData = new PomoData();
        }
        this.pomoController.setPomoData(curPomoData);
    }

    public void safeState(){
        try {
            FileOutputStream fOutStream = new FileOutputStream("pomoState.bin");
            BufferedOutputStream bufferedFileOutStream = new BufferedOutputStream(fOutStream);
            ObjectOutputStream outputStream = new ObjectOutputStream(bufferedFileOutStream);
            outputStream.writeObject(this.pomoController.getPomoData());
            outputStream.close();
        }
        catch (IOException e){
            System.err.println("could not safe the state"); // TODO proper error handling

        }


    }


}
