package org.jessysnow.controller.handler;

public abstract class AbstractHandler<T> implements Handler<T> {
    protected T content;

    public AbstractHandler(){}

    public AbstractHandler(T content){
        this.content = content;
    }

    public AbstractHandler<T> setContent(T content) {
        this.content = content;
        return this;
    }

    @Override
    public abstract T handle();
}
