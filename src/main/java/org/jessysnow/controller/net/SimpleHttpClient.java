package org.jessysnow.controller.net;

import org.jessysnow.controller.handler.AbstractHandler;
import org.jessysnow.controller.net.nio.NIOHttpClient;
import org.jessysnow.controller.pojo.enums.RequestContainer;
import org.jessysnow.controller.utils.URLHelper;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * A simple single thread http client
 */
public class SimpleHttpClient {
    public static Object request(URL baseURL, RequestContainer requestContainer){
        if(requestContainer.isLongConnection()){
            return NIOHttpClient.doRequest(requestContainer, System.out);
        }
        return SimpleHttpClient.doRequest(baseURL, requestContainer);
    }

    // Restful invoke
    public static String doRequest(URL baseURL, RequestContainer requestContainer){
        URL requestURL;
        HttpURLConnection connection;
        BufferedReader reader = null;
        StringBuilder res = new StringBuilder();
        try {
            // replace placeholder, construct url, get connection, and config connection method
            requestURL = new URL(baseURL, URLHelper.parseURL(requestContainer));
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod(requestContainer.getMethod());
            // fixme here
            // open socket io and do block request
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null){
                res.append(line);
            }
        } catch (MalformedURLException e) {
            System.out.println("Unknown error happened while construct request url");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Unknown error happened while dump data from connection");
            System.exit(1);
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Unknown error happened.");
                    System.exit(1);
                }
            }
        }

        return doHandle(res.toString(), requestContainer.getHandlers());
    }

    /**
     * Support for http stream, force flush, any input from System.in will case interrupt this stream
     * Write real-time data to console, enter c to quit
     * @param out destination to dump data
     */
    public static Void doRequest(URL baseURL, RequestContainer requestContainer, OutputStream out){
        URL requestURL;
        HttpURLConnection connection;
        BufferedReader reader = null;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
        try {
            // replace placeholder, construct url, get connection, and config connection method
            requestURL = new URL(baseURL, URLHelper.parseURL(requestContainer));
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod(requestContainer.getMethod());
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;

            // call-back thread, listen on System.in
            new Thread(new InterruptListener(connection)).start();

            // fixme, readLine is blocking here
            while ((line = reader.readLine()) != null){
                // force cut
                writer.write(line + "\n");
                // force flush
                writer.flush();
            }
        } catch (MalformedURLException e) {
            System.out.println("Unknown error happened while construct request url");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Connection closed!");
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Unknown error happened.");
                }
            }
            // Don't close it, cause System.out would be used again
//            try {
//                writer.close();
//            } catch (IOException e) {
//                System.out.println("Unknown error happened.");
//            }
        }
        return null;
    }

    private static String doHandle(String content, Class<? extends AbstractHandler>[] handlersClasses){
        for(Class<? extends AbstractHandler> clazz : handlersClasses){
            try {
                AbstractHandler handler = clazz.getConstructor().newInstance();
                handler.setContent(content);
                content = handler.handle();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println("Failed to process content.");
                return content;
            }
        }
        return content;
    }


    private static class InterruptListener implements Runnable{
        private final HttpURLConnection connection;

        private InterruptListener(HttpURLConnection connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            // Don't close it cause System.in could be used again
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                String line;
                while ((line = reader.readLine()) != null){
                    if (line.contains("C")){
                        // call back, disconnect
                        connection.disconnect();
                        break;
                    }
                }
            }catch (IOException e) {
                System.out.println("Unknown error");
                System.exit(1);
            }
        }
    }
}
