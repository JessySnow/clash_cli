package org.jessysnow.controller.handler;

public abstract class AbstractHandler implements ProxyInfoHandler{
    protected String content;

    public AbstractHandler setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public abstract String handle();
}
