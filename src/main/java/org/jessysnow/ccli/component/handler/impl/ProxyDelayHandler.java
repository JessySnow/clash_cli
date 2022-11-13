package org.jessysnow.ccli.component.handler.impl;

import org.jessysnow.ccli.component.handler.AbstractHandler;

public class ProxyDelayHandler extends AbstractHandler<String> {
    @Override
    public String handle() {
        return "Delay " + content.substring(content.indexOf(":"), content.length() - 1);
    }
}
