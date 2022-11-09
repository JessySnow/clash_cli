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
    public static final ExecutorService executor = Executors.newFixedThreadPool(1);

    // deal with http-stream api
    public static Void doRequest(RequestContainer requestContainer ,OutputStream out){
        Future<Void> stopDump = executor.submit(new consoleListener());
        try(SocketChannel realTimeChannel = SocketChannel.open(new InetSocketAddress(Boot.host, Boot.port))){
            realTimeChannel.configureBlocking(false);
            while (!realTimeChannel.finishConnect());
            // invoke
            realTimeChannel.write(ByteBuffer.wrap(requestContainer.getFixedHttpHeader().getBytes()));
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

        return null;
    }


    // listen on System.in
    private static class consoleListener implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            // Don't close it, cause System.in would be used later
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while (null != (line = reader.readLine()) && !line.contains("C"));
            return null;
        }
    }
}