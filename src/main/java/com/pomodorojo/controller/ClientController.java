package com.pomodorojo.controller;

import com.pomodorojo.model.ClientType;

public class ClientController {

    private PomoController pomoController;
    private final String operatingSystem = (System.getProperty("os.name")).toUpperCase();

    private ClientType clientType;
    /**
     * the client controller should always be initialized before every other controller
     * probably not really nececcary to be in an own controller at the moment
     * @param pomoController
     */
    public ClientController(PomoController pomoController){
        this.pomoController = pomoController;
        if (this.operatingSystem.contains("WIN")){
//            pomoController.getPomoData().setClientType(ClientType.PCCLIENTWINDOWS10);
            clientType = ClientType.PCCLIENTWINDOWS10;
        }
        else{

        }
    }

    public ClientType getClientType() {
        return clientType;
    }
}
