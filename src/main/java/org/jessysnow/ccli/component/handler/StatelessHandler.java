package org.jessysnow.ccli.component.handler;

public abstract class StatelessHandler<T>{
    public abstract T handle(T content);
}
