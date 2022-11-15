package org.jessysnow.ccli.utils;

import org.jessysnow.ccli.enums.HttpParamEntry;
import org.jessysnow.ccli.enums.RequestContainer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class URLHelper {
    /**
     * Replace placeholder in the url wrap in requestContainer
     * @param requestContainer url、parameter container
     */
    public static String parseURL(RequestContainer requestContainer){
        String relativePath = requestContainer.getRequestURLPath();
        if(requestContainer.getRequestParam() == null || requestContainer.getRequestParam().size() == 0){
            return relativePath;
        }

        // replace the placeholder in the request url
        for (Map.Entry<HttpParamEntry, String> next : requestContainer.getRequestParam().entrySet()) {
            String placeholder = next.getKey().getParamPlaceholder();
            String value = URLEncoder.encode(next.getValue(), StandardCharsets.UTF_8);
            value = urlEncodeReplace(value, "\\+", "%20");
            relativePath = relativePath.replace(placeholder, value);
        }

        return relativePath;
    }

    /**
     * TODO build a json request body
     * Replace placeholder in the url wrap in requestContainer
     * Build request body with param-key-value set (json)
     */
    public static HttpURLConnection parseHttpRequest(URL baseUrl, RequestContainer requestContainer){
        String relativePath = requestContainer.getRequestURLPath();
        if(requestContainer.getRequestParam() == null || requestContainer.getRequestParam().size() == 0){
            try {
                return (HttpURLConnection) new URL(baseUrl, relativePath).openConnection();
            } catch (IOException e) {
                System.out.println("Error");
                System.exit(1);
            }
        }

        // parse request path param and body param
        HashMap<String, String> bodyParam = new HashMap<>();
        for (Map.Entry<HttpParamEntry, String> next : requestContainer.getRequestParam().entrySet()) {
            String placeHolder;
            // a path param
            if((placeHolder = next.getKey().getParamPlaceholder()).startsWith(":")) {
                String value = URLEncoder.encode(next.getValue(), StandardCharsets.UTF_8);
                value = urlEncodeReplace(value, "\\+", "%20");
                relativePath = relativePath.replace(placeHolder, value);
            }
            if((placeHolder = next.getKey().getParamPlaceholder()).startsWith("#")){
                String value = URLEncoder.encode(next.getValue(), StandardCharsets.UTF_8);
                value = urlEncodeReplace(value, "\\+", "%20");
                bodyParam.put(placeHolder.substring(1), value);
            }
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(baseUrl, relativePath).openConnection();
            for(Map.Entry<String, String> paramEntry : bodyParam.entrySet()){
                httpURLConnection.addRequestProperty(paramEntry.getKey(), paramEntry.getValue());
            }
            return httpURLConnection;
        } catch (IOException e) {
            System.out.println("Error");
            System.exit(1);
        }
        return null;
    }

    /**
     * Replace some unsupported character in clash-restful api
     * sample : + -> %20
     * @param encodedURL url which already being encoded
     */
    private static String urlEncodeReplace(String encodedURL, String origin, String replace){
        return encodedURL.replaceAll(origin, replace);
    }
}
