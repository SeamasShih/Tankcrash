package com.honhai.foxconn.tankcrash;

import android.util.Log;

import java.util.ArrayList;

public class GameData {

    private GameData() {

        map = new MapData[10][10];

        for (int i = 0 ; i < map.length ; i++){
            for (int j = 0 ; j < map[0].length ; j++) {
                if ((i+1)%2 == 0 || (j+1)%2 == 0)
                    map[j][i] = MapData.TEST_ROAD;
                else
                    map[j][i] = MapData.TEST_PILLAR;
            }
        }

    }


    public static GameData getInstance() {
        return instance;
    }

    private static GameData instance = new GameData();
    private int playerAmount = 0;
    private int myOrder = -1;
    private Player[] players;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private MapData[][] map;
    private int mapX , mapY;

    public void setMap(MapData[][] map) {
        this.map = map;
        mapX = map[0].length;
        mapY = map.length;
    }

    public MapData getMap(int x , int y){
        if (x >= map[0].length || x < 0 || y >= map.length || y < 0)
            return null;
        return map[y][x];
    }

    public MapData[][] getMap(){
        return map;
    }

    public boolean addBullet(int order , float x , float y){
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getOrder() == order) {
                return false;
            }
        }
        bullets.add(new Bullet(order,x,y));
        return true;
    }

    public void removeBullet(int order) {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getOrder() == order) {
                bullets.remove(i);
                break;
            }
        }
    }

    public void shoot(int order,float x,float y){
        addBullet(order,x,y);
    }

    public ArrayList<Bullet> getBullet(){
        return bullets;
    }

    public int getPlayerAmount() {
        return playerAmount;
    }

    public float[] getTankSite() {
        float[] floats = new float[playerAmount - 1];
        for (int i = 0; i < playerAmount; i++) {
            floats[i * 2] = players[i].getSite()[0];
            floats[i * 2 + 1] = players[i].getSite()[1];
        }
        return floats;
    }

    public void setTankSite(int order, int x, int y) {
        players[order].setSite(x, y);
    }

    public void setTankSite(int order, float x, float y) {
        players[order].setSite(x, y);
    }

    public void setMyOrder(int myOrder) {
        this.myOrder = myOrder;
    }

    public int getMyOrder() {
        return myOrder;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
        players = new Player[playerAmount];
        for (int i = 0; i < playerAmount; i++) {
            players[i] = new Player(i);
        }
    }

    public Player getPlayer(int order) {
        if (order > playerAmount + 1)
            return null;
        return players[order];
    }

    public Player getMyself() {
        return players[myOrder];
    }

    public float[] getMySite() {
        return getMyself().getSite();
    }

    public String getMySiteString() {
        float[] f = getMyself().getSite();
        return " " + f[0] + " " + f[1];
    }
}
