package org.jessysnow.ccli.component.net;

import org.jessysnow.ccli.Bootstrap;
import org.jessysnow.ccli.component.handler.StatelessHandler;
import org.jessysnow.ccli.enums.RequestContainer;

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
    public final ExecutorService executor = Executors.newFixedThreadPool(1);
    private StatelessHandler<ByteBuffer>[] cachedHandler;

    private String host;
    private int port;

    public NIOHttpClient(){;}

    public NIOHttpClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    // deal with http-stream api
    public void doRequest(RequestContainer requestContainer , OutputStream out){
        try(SocketChannel realTimeChannel = SocketChannel.open(new InetSocketAddress(this.host, this.port))){
            Future<Void> stopDump = executor.submit(new consoleListener());
            realTimeChannel.configureBlocking(false);
            while (!realTimeChannel.finishConnect()){continue;}

            // invoke
            realTimeChannel.write(ByteBuffer.wrap(requestContainer.getFixedHttpHeader().getBytes()));
            // non-blocking ,until EOF
            ByteBuffer readBuffer = ByteBuffer.allocate(512);
            while(realTimeChannel.read(readBuffer) != -1){
                if(!readBuffer.hasRemaining()){continue;}

                readBuffer.flip();
                ByteBuffer res = doHandle(readBuffer, requestContainer.getHandlers());
                while (res.hasRemaining()){
                    out.write((char)res.get());
                }
                readBuffer.clear();
                if(stopDump.isDone()){
                    break;
                }
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
    }

    private ByteBuffer doHandle(ByteBuffer content, Class<? extends StatelessHandler>[] handlersClasses){
        if(null == handlersClasses || 0 == handlersClasses.length){
            return content;
        }
        // build handler pip-line
        if(null == cachedHandler){
            cachedHandler = new StatelessHandler[handlersClasses.length];
            for (int i = 0; i < cachedHandler.length; i++) {
                try {
                    cachedHandler[i] = handlersClasses[i].getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ignored) {}
            }
        }

        for (StatelessHandler<ByteBuffer> byteBufferHandler : cachedHandler) {
            content = byteBufferHandler.handle(content);
        }
        return content;
    }

    private static class consoleListener implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            // Don't close it, cause System.in would be used later
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (null == reader.readLine()){continue;}
            return null;
        }
    }

    public void cleanUp(){
        this.cachedHandler = null;
        this.executor.shutdownNow();
    }
}