package org.jessysnow.ccli.enums;

// Menu option enum
public enum Option {
    DUMP_LOG(0, "Real-time log dump to console"),
    DUMP_TRAFFIC(1, "Real-time traffic dump to console"),
    GET_VERSION(2, "Get ClashCore's version info"),
    GET_PROXIES(3, "Get info of all proxies"),
    GET_SPECIFIC_PROXY(4, "Get info of specific proxy"),
    GET_SPECIFIC_PROXY_DELAY(5, "Get latency of specific proxy"),
    SELECT_SPECIFIC_PROXY(6, "Select a specific proxy");


    private final String desc;
    private final int key;

    Option(int key, String desc){
        this.key = key;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.key + ": " + this.desc + "\n";
    }
}