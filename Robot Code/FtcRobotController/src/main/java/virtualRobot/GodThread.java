package virtualRobot;

import java.util.ArrayList;

/**
 * Created by shant on 1/5/2016.
 */
public abstract class GodThread implements Runnable {
    private Thread innerThread;
    private boolean isInnerThreadRunning;
    protected ArrayList<Thread> children;
    protected boolean shitWentWrong;

    public GodThread () {
        innerThread = new Thread(new InnerThread());
        children = new ArrayList<Thread>();
    }

    private class InnerThread implements Runnable {
        public void run() {
            try {
                realRun();
            }
            catch (InterruptedException e) {
                return;
            }
            finally {
                isInnerThreadRunning = false;
            }
        }


    }

    public void run() {
        innerThread.start();
        isInnerThreadRunning = true;
        while (isInnerThreadRunning) {
            approve();
            try {
                Thread.sleep(5);
            }
            catch (InterruptedException e) {
                innerThread.interrupt();
            }
        }
    }



    private void approve () {
        synchronized (this) {
            notifyAll();
        }
    }

    public void requestApproval () throws InterruptedException{
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                shitWentWrong = true;
            }
        }
    }

    public void kill() {
        for (Thread thread: children) {
            if (thread.isAlive())
                thread.interrupt();
        }

    }


    public abstract void realRun() throws InterruptedException;
}
