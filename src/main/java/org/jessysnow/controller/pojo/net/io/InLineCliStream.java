package org.jessysnow.controller.pojo.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;


public class InLineCliStream extends FilterOutputStream {
    private int backSpaceCount;
    private int newLineCount;
    private final int reFlushThreshold = 8;
    private final byte BACKSPACE = '\b';
    private final byte NEWLINE = '\n';
    private final byte ENTER = '\r';
    private final byte SPACE = ' ';

    public InLineCliStream(OutputStream out) {
        super(out);
        if(!out.equals(System.out)){
            throw new RuntimeException("Only accept System.out.");
        }
        this.backSpaceCount = 0;
        this.newLineCount = 0;
    }

    @Override
    public void write(int b) throws IOException {
        byte byt = (byte) b;
        if(NEWLINE == byt || ENTER == byt){
            newLineCount += 1;
            byt = SPACE;
        }
        out.write(byt);
        out.flush();
        this.backSpaceCount += 1;
        if(this.newLineCount >= reFlushThreshold){
            this.reFlush();
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.reFlush();
        for (int i = 0; i < b.length; i++) {
            if(NEWLINE == b[i] || ENTER == b[i]){
                b[i] = SPACE;
            }
        }
        out.write(b);
        out.flush();
        this.backSpaceCount += b.length;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        this.reFlush();
        int length = Math.min(off + len, b.length);
        for (int i = off; i < length; i++) {
            if(NEWLINE == b[i] || ENTER == b[i]){
                b[i] = SPACE;
            }
        }
        out.write(b, off, len);
        out.flush();
        this.backSpaceCount += b.length;
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    private void reFlush(){
        byte[] backSpaces = new byte[backSpaceCount];
        Arrays.fill(backSpaces, BACKSPACE);
        this.backSpaceCount = 0;
        this.newLineCount = 0;
        try {
            out.write(backSpaces);
            out.flush();
        } catch (IOException ignored) {}
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
