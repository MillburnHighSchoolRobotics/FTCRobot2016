package com.qualcomm.ftcrobotcontroller.opmodes;

public class motor {
   private volatile double position;
   public volatile double inchestraveled;
    public motor() {

    position = 0; inchestraveled = 0;
    }
    public synchronized void updatePosition(double newPos) {
        position = newPos;
        calcInches();
    }
    public synchronized void addPosition(double addPos){
        position+= addPos;
        calcInches();
    }
    private void calcInches(){
        //calcinches using pos and update
    }



}