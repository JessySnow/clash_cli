package org.jessysnow.controller.pojo;

/**
 * Represent a proxy node
 */
public class ProxyNode {
    private String name;
    private String type;
    private String server;
    private int port;
    private boolean udp;

    public ProxyNode(String name, String type, String server, int port, boolean udp){
        this.name = name;
        this.type = type;
        this.server = server;
        this.port = port;
        this.udp = udp;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public boolean isUdp() {
        return udp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUdp(boolean udp) {
        this.udp = udp;
    }

    @Override
    public String toString() {
        return "ProxyNode{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", server='" + server + '\'' +
                ", port='" + port + '\'' +
                ", dup=" + udp +
                '}';
    }
}
