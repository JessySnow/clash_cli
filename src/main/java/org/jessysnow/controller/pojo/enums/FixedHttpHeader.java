package org.jessysnow.controller.pojo.enums;

import org.jessysnow.controller.Boot;

public enum FixedHttpHeader {
    GET_TRAFFIC(Boot.host, Boot.port, "/traffic"), GET_LOG(Boot.host, Boot.port, "/logs");

    public final String HTTP_HEADER;

    FixedHttpHeader(String host, int port, String path){
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("GET http://").append(Boot.host).append(":").append(Boot.port).append(path).append(" HTTP/1.1\n")
                .append("User-Agent: Java/19.0.1\r\n")
                .append("Host: 127.0.0.1:9090\r\n")
                .append("Accept: */*\r\n")
                .append("Connection: keep-alive\r\n")
                .append("\r\n")
                .append("\r\n");
        this.HTTP_HEADER = headerBuilder.toString();
    }
}
