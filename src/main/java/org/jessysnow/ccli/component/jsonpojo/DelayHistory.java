package org.jessysnow.ccli.component.jsonpojo;

import java.util.Date;

public class DelayHistory {
    private Date time;
    private int delay;



    public DelayHistory(Date time, int delay) {
        this.time = time;
        this.delay = delay;
    }

    public DelayHistory() {
    }
}
