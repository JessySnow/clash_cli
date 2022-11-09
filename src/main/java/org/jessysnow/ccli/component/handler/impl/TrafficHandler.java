package org.jessysnow.ccli.component.handler.impl;

import org.jessysnow.ccli.component.handler.AbstractHandler;

import java.nio.ByteBuffer;

/**
 * Statistics traffic information
 * sample :
 * Up: 1024 KB/s, Down: 10455 KB/s, MaxUp: 10344KB/s, MaxDown: 23123KB/s, Total-Up: 10343 KB, Total-Down: 43278 KB.
 */
public class TrafficHandler extends AbstractHandler<ByteBuffer> {
    private int currentUp;
    private int currentDown;
    private long totalUp;
    private long totalDown;
    private int maxUp;
    private int maxDown;
    private static byte upLimit = '0'; // 48
    private static byte downLimit = '9'; // 57

    public TrafficHandler() {
        super();
        this.currentDown = 0;
        this.currentUp = 0;
        this.totalDown = 0L;
        this.totalUp = 0L;
        this.maxDown = 0;
        this.maxUp = 0;
    }

    @Override
    public ByteBuffer handle() {
        throw new UnsupportedOperationException("TrafficHandler doesn't support this method!");
    }

    @Override
    public ByteBuffer handle(ByteBuffer byteBuffer) {
        this.currentUp = handleUp();
        this.currentDown = handleDown();
        this.totalUp += currentUp;
        this.totalDown += currentDown;
        this.maxUp = Math.max(this.maxUp, currentUp);
        this.maxDown = Math.max(this.maxDown, currentDown);
        String output = "Up: " + currentUp + " KB/s," +
                " Down: " + currentDown + " KB/s, " +
                "MaxUp: " + maxUp + " KB/s, " +
                "MaxDown: " + maxDown + " KB/s, " +
                "Total-Up: " + totalUp + " KB, " +
                "Total-Down: " + totalDown + " KB. ";
        return ByteBuffer.wrap(output.getBytes());
    }

    private Integer handleUp(){
        if(content == null || !content.hasRemaining()){
            return 0;
        }
        byte readEd = 0;
        while (content.hasRemaining() && ((readEd = content.get()) > upLimit || readEd < downLimit));

        StringBuilder number = new StringBuilder();
        number.append((char) readEd);
        while (content.hasRemaining() && !((readEd = content.get()) > upLimit || readEd < downLimit)){
            number.append((char) readEd);
        }
        return Integer.parseInt(number.toString());
    }

    private Integer handleDown(){
        return handleUp();
    }
}
