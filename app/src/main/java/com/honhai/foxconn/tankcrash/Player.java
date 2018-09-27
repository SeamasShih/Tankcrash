package com.honhai.foxconn.tankcrash;

import com.honhai.foxconn.tankcrash.tankdrawable.prototype.TankPrototype;
import com.honhai.foxconn.tankcrash.tankdrawable.tank.HeavyTank;
import com.honhai.foxconn.tankcrash.tankdrawable.tank.HeightTank;
import com.honhai.foxconn.tankcrash.tankdrawable.tank.LightTank;

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

    public void setTank(int tank) {
        switch (tank) {
            case 0:
                this.tank = new LightTank();
                break;
            case 1:
                this.tank = new HeavyTank();
                break;
            case 2:
                this.tank = new HeightTank();
                break;
        }
    }

    public void goUp(){
        site[1]--;
    }

    public void goDown(){
        site[1]++;
    }

    public void goLeft(){
        site[0]--;
    }

    public void goRight(){
        site[0]++;
    }
}
