package org.jessysnow.controller.handler;

/**
 * Handle http response, support
 */
public abstract class ResultHandler {
    private String response;

    ResultHandler(String response){
        this.response = response;
    }

    // parse content here
    public abstract ResultHandler parseContent();

    public ResultHandler parseContent(ResultHandler next){
        return next.parseContent();
    }
}
