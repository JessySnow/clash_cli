package org.jessysnow.ccli.component.handler;

import org.jessysnow.ccli.component.handler.StatelessHandler;

import java.nio.ByteBuffer;

/**
 * Remove http header from http response
 */
public class HttpHeaderHandler extends StatelessHandler<ByteBuffer> {

    private static final byte NEWLINE = '\n';
    private static final byte ENTER = '\r';
    private boolean headerTag = true;

    @Override
    public ByteBuffer handle(ByteBuffer byteBuffer) {
        // skip header
        byte preVal1 = -1;
        byte preVal2 = -1;
        while (byteBuffer.hasRemaining()){
            byte nowVal1 = byteBuffer.get();
            if(byteBuffer.hasRemaining()){
                byte nowVal2 = byteBuffer.get();
                if((ENTER == nowVal1 && NEWLINE == nowVal2) && ((ENTER == preVal1 && NEWLINE == preVal2) || !headerTag)){
                    if(headerTag){
                        while(byteBuffer.hasRemaining()){
                            nowVal1 = byteBuffer.get();
                            if(byteBuffer.hasRemaining()){
                                nowVal2 = byteBuffer.get();
                                if(ENTER == nowVal1 && NEWLINE == nowVal2){
                                    break;
                                }
                            }
                        }
                    }
                    headerTag = false;
                    break;
                }
                preVal2 = nowVal2;
                preVal1 = nowVal1;
            }
        }

        return byteBuffer;
    }
}
