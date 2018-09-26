package com.honhai.foxconn.tankcrash;

public class GameData {

    private GameData(){
        map = Map.create();
        for (int i = 0; i < playerAmount; i++){
            players[i] = new Player(i);
        }
    }


    public static GameData getInstance() {
        return instance;
    }

    private static GameData instance = new GameData() ;
    private int playerAmount = 0;
    private int myOrder = 0;
    private Player[] players;
    public Map map;

    public int getPlayerAmount() {
        return playerAmount;
    }

    public float[] getTankSite(){
        float[] floats = new float[playerAmount -1];
        for (int i = 0; i < playerAmount; i++){
            floats[i*2] = players[i].getSite()[0];
            floats[i*2+1] = players[i].getSite()[1];
        }
        return floats;
    }

    public void setTankSite(int order , int x , int y){
        players[order].setSite(x,y);
    }

    public void setMyOrder(int myOrder) {
        this.myOrder = myOrder;
    }

    public int getMyOrder() {
        return myOrder;
    }

    public Player[] getPlayers(){
        return players;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
        players = new Player[playerAmount];
        for (int i = 0 ; i < playerAmount ; i++){
            players[i] = new Player(i);
        }
    }

    public Player getPlayer(int order){
        if (order > playerAmount + 1)
            return null;
        return players[order];
    }

    public Player getMyself() {
        return players[myOrder];
    }

    public float[] getMySite(){
        return getMyself().getSite();
    }

    public static class Map{
        public static Map create(){
            Map mMap = new Map();
            mMap.width = 100;
            mMap.height = 100;
            mMap.randomMap();
            return mMap;
        }

        public int width;
        public int height;
        public int[][] maps;

        public void randomMap(){
            if (width != 0 && height != 0){
                maps = new int[width][height];
                //todo
            }
        }
    }
}
