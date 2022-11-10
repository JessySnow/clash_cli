package org.jessysnow.ccli.component.handler.impl;

import org.jessysnow.ccli.component.handler.AbstractHandler;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


// FIXME UTF-16 Encoding issue
/**
 * Format clash's log
 * origin:
 *  {"type":"info","payload":"[TCP] 127.0.0.1:59656 --\u003e www.gstatic.com:443 match DomainSuffix(gstatic.com) using Bitz Net[🇹🇼 台湾-边缘访问 BGP]"}
 *  1. logType 2. connectionType 3. originAddress 4. destinationAddress 5. nodeInfo
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
        char aChar = byteBuffer.getChar();
        handleLogType(byteBuffer);
        handleConnectionType(byteBuffer);
        handleOriginAddress(byteBuffer);
        handleDestinationAddress(byteBuffer);
        handleNodeInfo(byteBuffer);

        StringBuilder res = new StringBuilder();
        res.append(this.logType).append(": ").append(nodeInfo).append(" || ").append(connectionType).append(originAddress)
                .append("-->").append(destinationAddress);
        return ByteBuffer.wrap(res.toString().getBytes());
    }

    private static final byte COLON = ':';
    private static final byte COMMA = ',';
    private static final byte DOUBLE_QUOTES = '\"';
    private static final byte LEFT_BRACKETS = '[';
    private static final byte RIGHT_BRACKETS = ']';
    private static final byte SPACE = ' ';

    // from ':' -> ','
    private void handleLogType(ByteBuffer byteBuffer){
        byte val;
        StringBuilder logTypeBuilder = new StringBuilder();
        // skip to ':'
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != COLON);
        // skip "
        byteBuffer.get();
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != DOUBLE_QUOTES){
            logTypeBuilder.append((char)val);
        }
        this.logType = logTypeBuilder.toString();
    }
    private void handleConnectionType(ByteBuffer byteBuffer){
        byte val;
        StringBuilder connectionTypeBuilder = new StringBuilder();
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != LEFT_BRACKETS);
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != RIGHT_BRACKETS){
            connectionTypeBuilder.append((char)val);
        }
        this.connectionType = connectionTypeBuilder.toString();
    }
    private void handleOriginAddress(ByteBuffer byteBuffer){
        byte val;
        StringBuilder originAddressBuilder = new StringBuilder();
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != SPACE);
        originAddressBuilder.append((char) SPACE);
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != SPACE){
            originAddressBuilder.append((char) val);
        }
        originAddressBuilder.append((char) SPACE);
        this.originAddress = originAddressBuilder.toString();
    }
    private void handleDestinationAddress(ByteBuffer byteBuffer){
        byte val;
        StringBuilder destinationAddressBuilder = new StringBuilder();
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != SPACE);
        destinationAddressBuilder.append((char) SPACE);
        while (byteBuffer.hasRemaining() && (val = byteBuffer.get()) != SPACE){
            destinationAddressBuilder.append((char) val);
        }
        destinationAddressBuilder.append((char) SPACE);
        this.destinationAddress = destinationAddressBuilder.toString();
    }

    //FIXME UTF-16 encoding
    private void handleNodeInfo(ByteBuffer byteBuffer){
        char val;
        StringBuilder nodeInfoBuilder = new StringBuilder();
        while (byteBuffer.hasRemaining() && (val = byteBuffer.getChar()) != LEFT_BRACKETS);
        nodeInfoBuilder.append((char) LEFT_BRACKETS);
        while (byteBuffer.hasRemaining() && (val = byteBuffer.getChar()) != RIGHT_BRACKETS){
            nodeInfoBuilder.append(val);
        }
        nodeInfoBuilder.append((char) RIGHT_BRACKETS);
        this.nodeInfo = nodeInfoBuilder.toString();
    }

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.wrap("{\"type\":\"info\",\"payload\":\"[TCP] 127.0.0.1:59656 --\\u003e www.gstatic.com:443 match DomainSuffix(gstatic.com) using Bitz Net[\uD83C\uDDF9\uD83C\uDDFC 台湾-边缘访问 BGP]\"}".getBytes());
        LogHandler logHandler = new LogHandler();
        ByteBuffer handle = logHandler.handle(buffer);
        while (handle.hasRemaining()){
            System.out.print((char) handle.get());
        }
    }
}
