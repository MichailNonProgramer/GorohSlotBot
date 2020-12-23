package com.urfu.GorohSlot.bonus;

import java.util.Timer;
import java.util.TimerTask;

public class TimerBonus {

    public static void start(){
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                Bonus.giveBonus();
            }
        };
        Timer timer = new Timer("Timer");

        long delay  = 1000L;
        long period = 1000L * 60 * 60 * 12;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }
}
