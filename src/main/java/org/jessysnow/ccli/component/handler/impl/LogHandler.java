package org.jessysnow.ccli.component.handler.impl;

import org.jessysnow.ccli.component.handler.AbstractHandler;

import java.nio.ByteBuffer;

/**
 * Format clash's log
 * origin:
 *  {"type":"info","payload":"[TCP] 127.0.0.1:59656 --\u003e www.gstatic.com:443 match DomainSuffix(gstatic.com) using Bitz Net[🇹🇼 台湾-边缘访问 BGP]"}
 * sample:
 *  info: Bitz Net[🇹🇼 台湾-边缘访问 BGP] || [TCP] 127.0.0.1:59656 -->  www.gstatic.com:443
 */
public class LogHandler extends AbstractHandler<ByteBuffer> {
    private String logType;
    private String nodeInfo;
    private String connectionType;
    private String originAddress;
    private String destinationAddress;

    @Override
    public ByteBuffer handle() {
        throw new UnsupportedOperationException("LogHandler doesn't support this method!");
    }

    @Override
    public ByteBuffer handle(ByteBuffer byteBuffer) {
        return super.handle(byteBuffer);
    }
}
