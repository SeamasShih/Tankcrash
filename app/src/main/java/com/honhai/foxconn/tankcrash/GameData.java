package com.honhai.foxconn.tankcrash;

import com.honhai.foxconn.tankcrash.Tank.Prototype.TankPrototype;

public class GameData {

    private GameData(){}

    public static GameData getInstance() {
        return instance;
    }

    private static GameData instance = new GameData() ;
    private int playAmount = 0;
    private int myOrder = 0;
    private Player[] players = new Player[4];
    private Player player = new Player(0);

    public Player getMyself() {
        return player;
    }

}
