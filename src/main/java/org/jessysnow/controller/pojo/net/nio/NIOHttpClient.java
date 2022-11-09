package org.jessysnow.controller.pojo.net.nio;

import org.jessysnow.controller.Bootstrap;
import org.jessysnow.controller.handler.AbstractHandler;
import org.jessysnow.controller.handler.Handler;
import org.jessysnow.controller.pojo.enums.RequestContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

/**
 * It's a no blocking http client
 * mainly used to invoke a stream-http-api
 */
public class NIOHttpClient {
    public static final ExecutorService executor = Executors.newFixedThreadPool(1);
    private static Handler<ByteBuffer>[] cachedHandler = null;

    // deal with http-stream api
    public static Void doRequest(RequestContainer requestContainer ,OutputStream out){
        Future<Void> stopDump = executor.submit(new consoleListener());
        try(SocketChannel realTimeChannel = SocketChannel.open(new InetSocketAddress(Bootstrap.host, Bootstrap.port))){
            realTimeChannel.configureBlocking(false);
            while (!realTimeChannel.finishConnect());

            // invoke
            realTimeChannel.write(ByteBuffer.wrap(requestContainer.getFixedHttpHeader().getBytes()));
            // non-blocking read, loop until EOF
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            while(!stopDump.isDone() && realTimeChannel.read(readBuffer) != -1){
//                readBuffer.flip();
//                // skip header
//                byte preVal1 = 0;
//                byte preVal2 = 0;
//                while (readBuffer.hasRemaining()){
//                    byte nowVal1 = readBuffer.get();
//                    if(readBuffer.hasRemaining()){
//                        byte nowVal2 = readBuffer.get();
//                        if((nowVal2 == 10 && nowVal1 == 13) && ((preVal2 == 10 && preVal1 == 13) || !headTag)){
//                            if(headTag){
//                                while (readBuffer.hasRemaining()){
//                                    nowVal1 = readBuffer.get();
//                                    if(readBuffer.hasRemaining()){
//                                        nowVal2 = readBuffer.get();
//                                        if(nowVal2 == 10 && nowVal1 == 13){
//                                            break;
//                                        }
//                                    }
//                                }
//                            }
//                            headTag = false;
//                            break;
//                        }
//                        preVal2 = nowVal2;
//                        preVal1 = nowVal1;
//                    }
//                }
//
//                // output body
//                while (readBuffer.hasRemaining()){
//                    out.write((char)readBuffer.get());
//                }
//                readBuffer.clear();
                readBuffer.flip();
                doHandle(readBuffer, requestContainer.getHandlers());
                while (readBuffer.hasRemaining()){
                    out.write((char)readBuffer.get());
                }
                readBuffer.clear();
            }
        } catch (IOException e) {
            System.out.printf("Unknown error while building socket channel, check your host: %s, and port: %d\n",
                                                                                            Bootstrap.host,
                                                                                            Bootstrap.port);
            System.exit(1);
        }finally {
            // clean up
            cachedHandler = null;
        }
        return null;
    }

    private static void doHandle(ByteBuffer content, Class<? extends AbstractHandler>[] handlersClasses){
        if(null == handlersClasses || 0 == handlersClasses.length){
            return;
        }
        if(null == cachedHandler){
            cachedHandler = new Handler[handlersClasses.length];
            for (int i = 0; i < cachedHandler.length; i++) {
                try {
                    cachedHandler[i] = handlersClasses[i].getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ignored) {}
            }
        }

        for (Handler<ByteBuffer> byteBufferHandler : cachedHandler) {
            byteBufferHandler.handle(content);
        }
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