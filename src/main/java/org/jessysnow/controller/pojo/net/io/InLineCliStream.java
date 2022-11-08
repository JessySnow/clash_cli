package org.jessysnow.controller.pojo.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;


public class InLineCliStream extends FilterOutputStream {
    private int backSpaceCount;
    private byte BACKSPACE = '\b';

    public InLineCliStream(OutputStream out) {
        super(out);
        if(!out.equals(System.out)){
            throw new RuntimeException("Only accept System.out.");
        }
        this.backSpaceCount = 0;
    }

    @Override
    public void write(int b) throws IOException {
        throw new UnsupportedOperationException("Don't invoke this method in this class.");
    }

    @Override
    public void write(byte[] b) throws IOException {
        throw new UnsupportedOperationException("Don't invoke this method in this class.");
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        throw new UnsupportedOperationException("Don't invoke this method in this class.");
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    public void print(String line) {
        this.reFlush();
        line = line.replaceAll("[\r\n]", " ");
        byte[] bytes = line.getBytes();
        this.backSpaceCount += bytes.length;

        try {
            out.write(bytes);
            out.flush();
        } catch (IOException ignored) {}
    }

    private void reFlush(){
        byte[] backSpaces = new byte[backSpaceCount];
        Arrays.fill(backSpaces, BACKSPACE);
        this.backSpaceCount = 0;
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
