package org.jessysnow.ccli.component.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;


public class TrafficStream extends FilterOutputStream {
    private int backSpaceCount;
    private final int outPutThreshold;
    private  int dotCount;

    public TrafficStream(OutputStream out) {
        super(out);
        if(!out.equals(System.out)){
            throw new RuntimeException("Only accept System.out.");
        }
        this.backSpaceCount = 0;
        this.outPutThreshold = 500;
        this.dotCount = 0;
    }

    /**
     * @see org.jessysnow.ccli.component.handler.impl.TrafficHandler;
     * @param b   {@inheritDoc}
     */
    // ignore some traffic info
    @Override
    public void write(int b) throws IOException {
        if(this.dotCount == 0){
            out.write(b);
            out.flush();
            this.backSpaceCount ++;
        }
        if((byte)b == '.'){
            this.dotCount = (dotCount + 1) % outPutThreshold;
            if(dotCount == 0){
                this.reFlush();
            }
        }
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
