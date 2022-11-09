package org.jessysnow.ccli.component.handler.impl;

import org.jessysnow.ccli.component.handler.AbstractHandler;

import java.nio.ByteBuffer;

/**
 * Remove '\r\n\n' sequence behind http response
 */
public class CliHelperHandler extends AbstractHandler<ByteBuffer> {
    @Override
    public ByteBuffer handle(ByteBuffer byteBuffer) {
        int originLimit = byteBuffer.limit();
        if(originLimit >= 3){
            byteBuffer.limit(originLimit - 3);
        }
        return byteBuffer;
    }

    @Override
    public ByteBuffer handle() {
        throw new UnsupportedOperationException("HttpHeaderHandler doesn't support this method!");
    }
}
