package com.example.stonksorstinks.models;

import java.util.ArrayList;
import java.util.Map;

public class Room {
    int roomID, wockRate, hdfcRate, tataRate, ongcRate, relRate, infoRate;
    ArrayList<String> playerLogs;
    Map<String, Player> players;
    int round, maxPlayers, noOfPlayers;
    Boolean isFull, isLocked;
    ArrayList<Cards> cards;

    public Room() {

    }

    public Room(int roomID, Map<String, Player> players, Boolean isFull, Boolean isLocked) {
        this.roomID = roomID;
        this.players = players;
        this.round = 1;
        this.maxPlayers = 4;
        this.isFull = isFull;
        this.isLocked = isLocked;
        this.noOfPlayers = 1;
        this.wockRate = 20;
        this.hdfcRate = 30;
        this.tataRate = 40;
        this.ongcRate = 50;
        this.relRate = 70;
        this.infoRate = 80;
        this.playerLogs = new ArrayList<>();
        playerLogs.add("");
        this.cards = new ArrayList<>();
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }

    public int getWockRate() {
        return wockRate;
    }

    public void setWockRate(int wockRate) {
        this.wockRate = wockRate;
    }

    public int getHdfcRate() {
        return hdfcRate;
    }

    public void setHdfcRate(int hdfcRate) {
        this.hdfcRate = hdfcRate;
    }

    public int getTataRate() {
        return tataRate;
    }

    public void setTataRate(int tataRate) {
        this.tataRate = tataRate;
    }

    public int getOngcRate() {
        return ongcRate;
    }

    public void setOngcRate(int ongcRate) {
        this.ongcRate = ongcRate;
    }

    public int getRelRate() {
        return relRate;
    }

    public void setRelRate(int relRate) {
        this.relRate = relRate;
    }

    public int getInfoRate() {
        return infoRate;
    }

    public void setInfoRate(int infoRate) {
        this.infoRate = infoRate;
    }

    public ArrayList<String> getPlayerLogs() {
        return playerLogs;
    }

    public void setPlayerLogs(ArrayList<String> playerLogs) {
        this.playerLogs = playerLogs;
    }

    public ArrayList<Cards> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Cards> cards) {
        this.cards = cards;
    }

}
