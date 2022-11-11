package org.jessysnow.ccli.component.jsonpojo;

import java.util.Arrays;

public class Node {
    private String name;
    private String type;
    private boolean udp;
    private DelayHistory[] histories;
    private String[] proxyGroup;

    public Node() {}

    public Node(String name, String type, boolean udp, DelayHistory[] histories, String[] proxyGroup) {
        this.name = name;
        this.type = type;
        this.udp = udp;
        this.histories = histories;
        this.proxyGroup = proxyGroup;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", udp=" + udp +
                ", histories=" + Arrays.toString(histories) +
                ", proxyGroup=" + Arrays.toString(proxyGroup) +
                '}';
    }
}
