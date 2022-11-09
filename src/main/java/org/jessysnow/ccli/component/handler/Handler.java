package org.jessysnow.ccli.component.handler;

/**
 * Handle http response, support
 */
public interface Handler<T> {
    T handle();

    default T handle(T t){
        return t;
    }
}
