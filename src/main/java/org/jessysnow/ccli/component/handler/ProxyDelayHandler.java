package org.jessysnow.ccli.component.handler;

import org.jessysnow.ccli.component.handler.StatelessHandler;

public class ProxyDelayHandler extends StatelessHandler<String> {

    @Override
    public String handle(String content) {
        return "Delay " + content.substring(content.indexOf(":"), content.length() - 1);
    }
}
