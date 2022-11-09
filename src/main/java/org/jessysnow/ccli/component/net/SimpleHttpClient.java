package org.jessysnow.ccli.component.net;

import org.jessysnow.ccli.component.handler.AbstractHandler;
import org.jessysnow.ccli.component.net.nio.NIOHttpClient;
import org.jessysnow.ccli.component.enums.RequestContainer;
import org.jessysnow.ccli.utils.URLHelper;

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
            return NIOHttpClient.doRequest(requestContainer, requestContainer.getOutputStream());
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

    private static String doHandle(String content, Class<? extends AbstractHandler>[] handlersClasses){
        for(Class<? extends AbstractHandler> clazz : handlersClasses){
            try {
                AbstractHandler<String> handler = clazz.getConstructor().newInstance();
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
}