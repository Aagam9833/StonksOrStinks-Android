package com.example.stonksorstinks.models;

public class Player {
    int cash, wockHold, hdfcHold, tataHold, ongcHold, relHold, infoHold;
    String username;
    Boolean isHost;
    Boolean ready;

    public Player() {

    }

    public Player(String username, Boolean isHost) {
        this.username = username;
        this.cash = 500000;
        this.wockHold = 0;
        this.hdfcHold = 0;
        this.tataHold = 0;
        this.ongcHold = 0;
        this.relHold = 0;
        this.infoHold = 0;
        this.isHost = isHost;
        this.ready = false;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getWockHold() {
        return wockHold;
    }

    public void setWockHold(int wockHold) {
        this.wockHold = wockHold;
    }

    public int getHdfcHold() {
        return hdfcHold;
    }

    public void setHdfcHold(int hdfcHold) {
        this.hdfcHold = hdfcHold;
    }

    public int getTataHold() {
        return tataHold;
    }

    public void setTataHold(int tataHold) {
        this.tataHold = tataHold;
    }

    public int getOngcHold() {
        return ongcHold;
    }

    public void setOngcHold(int ongcHold) {
        this.ongcHold = ongcHold;
    }

    public int getRelHold() {
        return relHold;
    }

    public void setRelHold(int relHold) {
        this.relHold = relHold;
    }

    public int getInfoHold() {
        return infoHold;
    }

    public void setInfoHold(int infoHold) {
        this.infoHold = infoHold;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getHost() {
        return isHost;
    }

    public void setHost(Boolean host) {
        isHost = host;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

}
