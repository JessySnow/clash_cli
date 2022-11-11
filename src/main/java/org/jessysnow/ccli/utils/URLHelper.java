package org.jessysnow.ccli.utils;

import org.jessysnow.ccli.component.enums.HttpParamEntry;
import org.jessysnow.ccli.component.enums.RequestContainer;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
     * Replace some unsupported character in clash-restful api
     * sample : + -> %20
     * @param encodedURL url which already being encoded
     */
    private static String urlEncodeReplace(String encodedURL, String origin, String replace){
        return encodedURL.replaceAll(origin, replace);
    }
}
