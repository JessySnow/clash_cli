package org.jessysnow.ccli.component.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;


public class TrafficStream extends FilterOutputStream {
    private int backSpaceCount;
    private int reFlushThreshold;
    private int outPutCount;

    public TrafficStream(OutputStream out) {
        super(out);
        if(!out.equals(System.out)){
            throw new RuntimeException("Only accept System.out.");
        }
        this.backSpaceCount = 0;
        this.outPutCount = 0;
        this.reFlushThreshold = 4;
    }

    @Override
    public void write(int b) throws IOException {
        if(outPutCount >= reFlushThreshold){
            reFlush();
            outPutCount = 0;
        }
        byte byt = (byte) b;
        if('}' == byt){
            outPutCount += 1;
        }
        out.write(byt);
        out.flush();
        this.backSpaceCount += 1;
    }

    @Override
    public void write(byte[] b) throws IOException {;}

    @Override
    public void write(byte[] b, int off, int len) throws IOException {;}

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    private void reFlush(){
        byte[] backSpaces = new byte[backSpaceCount];
        byte BACKSPACE = '\b';
        Arrays.fill(backSpaces, BACKSPACE);
        this.backSpaceCount = 0;
        try {
            out.write(backSpaces);
            out.flush();
        } catch (IOException ignored) {}
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Don't close System.out");
    }
}
