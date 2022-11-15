package org.jessysnow.ccli.component.net;

import org.jessysnow.ccli.component.handler.StatelessHandler;
import org.jessysnow.ccli.enums.RequestContainer;
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
    // Restful invoke
    public String doRequest(URL baseURL, RequestContainer requestContainer){
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

    private String doHandle(String content, Class<? extends StatelessHandler>[] handlersClasses){
        if(null == handlersClasses) return content;
        for(Class<? extends StatelessHandler> clazz : handlersClasses){
            try {
                StatelessHandler<String> handler = clazz.getConstructor().newInstance();
                content = handler.handle(content);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println("Failed to process content.");
                return content;
            }catch(Exception e){
                System.out.println("Failed to process content.");
                return content;
            }
        }
        return content;
    }
}
