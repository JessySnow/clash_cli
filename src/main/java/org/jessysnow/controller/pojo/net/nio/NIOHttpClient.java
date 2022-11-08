package org.jessysnow.controller.pojo.net.nio;

import org.jessysnow.controller.Boot;
import org.jessysnow.controller.pojo.enums.RequestContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * It's a no blocking http client
 * mainly used to invoke a stream-http-api
 */
public class NIOHttpClient {

    private static final String logsHeader;
    private static final String trafficHeader;
    public static final ExecutorService executor = Executors.newFixedThreadPool(1);

    static{
        StringBuffer logsBuilder = new StringBuffer();
        StringBuffer trafficBuilder = new StringBuffer();

        // construct logsHeader
        logsBuilder.append("GET http://").append(Boot.host).append(":").append(Boot.port).append("/logs HTTP/1.1\n")
                .append("User-Agent: Java/19.0.1\r\n")
                .append("Host: 127.0.0.1:9090\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("\r\n")
                .append("\r\n");
        // construct trafficHeader
        trafficBuilder.append("GET http://").append(Boot.host).append(":").append(Boot.port).append("/traffic HTTP/1.1\n")
                .append("User-Agent: Java/19.0.1\r\n")
                .append("Host: 127.0.0.1:9090\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("\r\n")
                .append("\r\n");

        logsHeader = logsBuilder.toString();
        trafficHeader = trafficBuilder.toString();
    }

    public static Void doRequest(RequestContainer requestContainer, OutputStream out){
        switch (requestContainer.getRequestURLPath()){
            case "/logs":
                doRequest(ByteBuffer.wrap(logsHeader.getBytes()),out);
                break;
            case "/traffic":
                doRequest(ByteBuffer.wrap(trafficHeader.getBytes()), out);
                break;
        }
        return null;
    }

    // deal with http-stream api
    private static void doRequest(ByteBuffer header ,OutputStream out){
        Future<Boolean> stopDump = executor.submit(new consoleListener());
        try(SocketChannel realTimeChannel = SocketChannel.open(new InetSocketAddress(Boot.host, Boot.port))){
            realTimeChannel.configureBlocking(false);
            while (!realTimeChannel.finishConnect());
            // invoke
            realTimeChannel.write(header);
            // unblocking read, loop until EOF
            ByteBuffer readBuffer = ByteBuffer.allocate(512);
            boolean headTag = true;
            while(!stopDump.isDone() && realTimeChannel.read(readBuffer) != -1){

                readBuffer.flip();
                // skip header
                byte preVal1 = 0;
                byte preVal2 = 0;
                while (readBuffer.hasRemaining()){
                    byte nowVal1 = readBuffer.get();
                    if(readBuffer.hasRemaining()){
                        byte nowVal2 = readBuffer.get();
                        if((nowVal2 == 10 && nowVal1 == 13) && ((preVal2 == 10 && preVal1 == 13) || !headTag)){
                            if(headTag){
                                while (readBuffer.hasRemaining()){
                                    nowVal1 = readBuffer.get();
                                    if(readBuffer.hasRemaining()){
                                        nowVal2 = readBuffer.get();
                                        if(nowVal2 == 10 && nowVal1 == 13){
                                            break;
                                        }
                                    }
                                }
                            }
                            headTag = false;
                            break;
                        }
                        preVal2 = nowVal2;
                        preVal1 = nowVal1;
                    }
                }

                // output body
                while (readBuffer.hasRemaining()){
                    out.write((char)readBuffer.get());
                }
                readBuffer.clear();
            }
        } catch (IOException e) {
            System.out.printf("Unknown error while building socket channel, check your host: %s, and port: %d\n",
                                                                                            Boot.host,
                                                                                            Boot.port);
            System.exit(1);
            // fixme don't ignore runtime exception
        }
    }

    // listen on System.in
    private static class consoleListener implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            // Don't close it, cause System.in would be used later
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while (null != (line = reader.readLine()) && !line.contains("C"));
            return true;
        }
    }
}