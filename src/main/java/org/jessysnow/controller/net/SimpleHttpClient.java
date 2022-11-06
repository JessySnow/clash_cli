package org.jessysnow.controller.net;

import org.jessysnow.controller.pojo.RequestContainer;
import org.jessysnow.controller.utils.URLHelper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple single thread http client
 */
public class SimpleHttpClient {
    public static Object reuqest(URL baseURL, RequestContainer requestContainer){
        if(requestContainer.isLongConnection()){
            return SimpleHttpClient.doRequest(baseURL, requestContainer, System.out);
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
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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

        return res.toString();
    }

    /**
     * Write real-time data to tou
     * @param out destination to dump data
     */
    public static Void doRequest(URL baseURL, RequestContainer requestContainer, OutputStream out){
        URL requestURL;
        HttpURLConnection connection;
        BufferedReader reader = null;
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        try {
            // replace placeholder, construct url, get connection, and config connection method
            requestURL = new URL(baseURL, URLHelper.parseURL(requestContainer));
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod(requestContainer.getMethod());
            // fixme here
            // open socket io and do block request
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                writer.write(line);
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
        return null;
    }
}
