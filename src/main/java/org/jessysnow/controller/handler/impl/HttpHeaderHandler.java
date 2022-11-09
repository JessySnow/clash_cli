package org.jessysnow.controller.handler.impl;

import org.jessysnow.controller.handler.AbstractHandler;

import java.nio.ByteBuffer;

public class HttpHeaderHandler extends AbstractHandler<ByteBuffer> {

    private static final byte NEWLINE = '\n';
    private static final byte ENTER = '\r';

    @Override
    public ByteBuffer handle() {
        return null;
    }
}
