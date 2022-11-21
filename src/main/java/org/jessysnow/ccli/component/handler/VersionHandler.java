package org.jessysnow.ccli.component.handler;

public class VersionHandler extends StatelessHandler<String>{
    @Override
    public String handle(String content) {
        return "Version" + content.substring(content.indexOf(":"), content.length() - 1);
    }
}
