package com.honhai.foxconn.tankcrash;

import com.honhai.foxconn.tankcrash.Tank.Prototype.TankPrototype;
import com.honhai.foxconn.tankcrash.Tank.Tank.HeavyTank;
import com.honhai.foxconn.tankcrash.Tank.Tank.HeightTank;
import com.honhai.foxconn.tankcrash.Tank.Tank.LightTank;

public class Player {

    public Player(int order){
        this.order = order;
    }

    private int order;
    private TankPrototype tank;
    private float[] site = new float[2];
    private boolean isAlive = true;

    public int getOrder() {
        return order;
    }

    public float[] getSite() {
        return site;
    }

    public void setSite(float x , float y){
        site[0] = x;
        site[1] = y;
    }

    public TankPrototype getTank() {
        return tank;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setTank(TankPrototype tank) {
        this.tank = tank;
    }

    public void goUp(){
        site[1]++;
    }

    public void goDown(){
        site[1]--;
    }

    public void goLeft(){
        site[0]++;
    }

    public void goRight(){
        site[0]--;
    }
}
