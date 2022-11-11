package org.jessysnow.ccli.component.jsonpojo;

import java.util.List;

public class CachedNode {
    private boolean updated;
    private List<Node> proxyList;

    public CachedNode(){
        this.proxyList = null;
        this.updated = false;
    }

    public boolean isUpdated() {
        return updated;
    }

    public List<Node> getProxyList() {
        return proxyList;
    }
}
