package com.qualcomm.ftcrobotcontroller.opmodes;

public class motor {
    private volatile double rawposition;
    private volatile double softposition;
    public volatile double inchestraveled;
    public motor() {

        rawposition = 0; inchestraveled = 0;
    }
    public synchronized void updatePosition(double newPos) {
        rawposition = newPos;
        softposition=newPos;
        calcInches();
    }
    public synchronized void addPosition(double addPos){
        rawposition+= addPos;
        softposition+= addPos;
        calcInches();
    }
    private void calcInches(){
        //calcinches using softpos and update
    }
    public synchronized void resetPos() {
        softposition=0;
    }
    public synchronized void resetPos(double startPos){
        softposition=startPos;
    }




}