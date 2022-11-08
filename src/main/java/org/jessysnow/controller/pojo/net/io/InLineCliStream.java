package org.jessysnow.controller.pojo.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class InLineCliStream extends FilterOutputStream {
    public InLineCliStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
