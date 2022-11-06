package org.jessysnow.controller.pojo.enums;

import org.jessysnow.controller.handler.AbstractHandler;
import org.jessysnow.controller.handler.impl.SliceHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Http request container
 * Method : RESTFul HTTP Connection Method -- GET/PUT/POST/DELETE
 * requestURLPath : The relative path of base url
 * requestParam : The request param followed in the end of url
 */
public enum RequestContainer {

    DUMP_LOG("/logs",
            RestfulMethod.GET,
            null,
            true,
            null),
    DUMP_TRAFFIC("/traffic",
            RestfulMethod.GET,
            null,
            true,
            null),
    GET_VERSION("/version",
            RestfulMethod.GET,
            null,
            false,
            null),
    GET_PROXIES("/proxies",
            RestfulMethod.GET,
            null,
            false,
            new Class[]{SliceHandler.class}),
    GET_SPECIFIC_PROXY_INFO("/proxies/:name",
            RestfulMethod.GET,
            constructRequestParamMap(new HttpParamEntry[]{HttpParamEntry.PROXY}),
            false, null),
    GET_SPECIFIC_PROXY_DELAY("/proxies/:name/delay",
            RestfulMethod.GET,
            constructRequestParamMap(new HttpParamEntry[]{HttpParamEntry.PROXY}),
            false,
            null),
    SELECT_SPECIFIC_PROXY("/proxies/:name",
            RestfulMethod.PUT,
            constructRequestParamMap(new HttpParamEntry[]{HttpParamEntry.PROXY}),
            false,
            null);

    private final String requestURLPath;
    private final RestfulMethod method;
    private final Map<HttpParamEntry, String> requestParam;
    private final boolean longConnection;
    private final Class<? extends AbstractHandler>[] handlers;

    RequestContainer(String requestURLPath,
                     RestfulMethod method, Map<HttpParamEntry, String> requestParam,
                     boolean longConnection, Class<? extends AbstractHandler>[] handlers){
        this.requestURLPath = requestURLPath;
        this.method = method;
        this.requestParam = requestParam;
        this.longConnection = longConnection;
        this.handlers = handlers;
    }

    public String getRequestURLPath() {
        return this.requestURLPath;
    }

    public String getMethod() {
        return method.getRestfulMethod();
    }

    public Map<HttpParamEntry, String> getRequestParam() {
        return requestParam;
    }


    // If it is a long TCP connection, try to continuously output what the socket reads to the console
    public boolean isLongConnection() {
        return longConnection;
    }

    public Class<? extends AbstractHandler>[] getHandlers() {
        return handlers;
    }

    // Supported http method
    public enum RestfulMethod{

        GET("GET"),PUT("PUT"),POST("POST"),DEL("DELETE");

        private final String restfulMethod;

        RestfulMethod(String restfulMethod) {
            this.restfulMethod = restfulMethod;
        }

        public String getRestfulMethod() {
            return restfulMethod;
        }
    }

    private static Map<HttpParamEntry, String> constructRequestParamMap(HttpParamEntry[] entries){
        Map<HttpParamEntry, String> paramMap = new HashMap<HttpParamEntry, String>();
        for(HttpParamEntry entry : entries){
            paramMap.put(entry, null);
        }
        return paramMap;
    }
}
