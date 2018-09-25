package com.honhai.foxconn.tankcrash;

public class GameData {

    private GameData(){
        map = Map.create();
        for (int i = 0 ; i < playAmount ; i++){
            players[i] = new Player(i);
        }
    }


    public static GameData getInstance() {
        return instance;
    }

    private static GameData instance = new GameData() ;
    private int playAmount = 2;
    private int myOrder = 0;
    private Player[] players = new Player[4];
    private Player player = new Player(0);
    public Map map;

    public float[] getTankSite(){
        float[] floats = new float[playAmount-1];
        for (int i = 0 ; i < playAmount ; i++){
            floats[i*2] = players[i].getSite()[0];
            floats[i*2+1] = players[i].getSite()[1];
        }
        return floats;
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
