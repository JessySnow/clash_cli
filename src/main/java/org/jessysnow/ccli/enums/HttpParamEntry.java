package org.jessysnow.ccli.enums;

/*
 * Map the http request param name to it's shown name in the console
 * ":" a path param
 * "#" a body param
 */
public enum HttpParamEntry {

    SELECTOR_NAME("#name","selector group name"),
    PROXY(":name","proxy name"),
    TIMEOUT(":timeout", "timeout(eg: 2000ms)"),
    TEST_URL(":url", "test url(eg: https://www.google.com)");

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
