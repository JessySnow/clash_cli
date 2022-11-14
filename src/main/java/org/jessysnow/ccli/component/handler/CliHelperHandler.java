package org.jessysnow.ccli.component.handler;

import org.jessysnow.ccli.component.handler.StatelessHandler;

import java.nio.ByteBuffer;

/**
 * Remove '\r\n\n' sequence behind http response
 */
public class CliHelperHandler extends StatelessHandler<ByteBuffer> {
    @Override
    public ByteBuffer handle(ByteBuffer byteBuffer) {
        int originLimit = byteBuffer.limit();
        if(originLimit >= 3){
            byteBuffer.limit(originLimit - 3);
        }
        return byteBuffer;
    }
}
