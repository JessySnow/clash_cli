package org.jessysnow.controller.pojo.enums;

/*
 * Map the http request param name to it's shown name in the console
 */
public enum HttpParamEntry {

    PROXY(":name","proxy name");

    private final String paramPlaceholder;
    private final String paramShownName;

    HttpParamEntry(String paramName, String paramShownName){
        this.paramPlaceholder = paramName;
        this.paramShownName = paramShownName;
    }

    public String getParamPlaceholder() {
        return paramPlaceholder;
    }

    public String getParamShownName() {
        return paramShownName;
    }
}
