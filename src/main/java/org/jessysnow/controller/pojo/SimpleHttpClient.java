package org.jessysnow.controller.pojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * A simple single thread http client
 */
public class SimpleHttpClient {

    // Restful invoke
    public static String doRequest(URL baseURL, RequestContainer requestContainer){
        URL requestURL;
        HttpURLConnection connection;
        BufferedReader reader = null;
        StringBuilder res = new StringBuilder();
        try {
            // replace placeholder, construct url, get connection, and config connection method
            requestURL = new URL(baseURL, parseURL(requestContainer));
            connection = (HttpURLConnection) requestURL.openConnection();
            connection.setRequestMethod(requestContainer.getMethod());

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

    // replace the placeholder in the request-url-path
    // placeholder format -- :placeholder
    private static String parseURL(RequestContainer requestContainer){
        String relativePath = requestContainer.getRequestURLPath();
        if(requestContainer.getRequestParam() == null || requestContainer.getRequestParam().size() == 0){
            return relativePath;
        }

        // replace the placeholder in the request url
        for (Map.Entry<HttpParamEntry, String> next : requestContainer.getRequestParam().entrySet()) {
            String placeholder = next.getKey().getParamPlaceholder();
            String value = ":" + next.getValue();
            relativePath = relativePath.replace(placeholder, value);
        }

        return relativePath;
    }
}
