package org.jessysnow.ccli.component.handler.impl;

import com.google.gson.Gson;
import org.jessysnow.ccli.component.handler.AbstractHandler;
import org.jessysnow.ccli.component.jsonpojo.CachedNode;
import org.jessysnow.ccli.component.jsonpojo.Node;

public class NodeHandler extends AbstractHandler<String> {
    private static CachedNode cache = new CachedNode();

    @Override
    public String handle() {
        if(content == null) return content;

        String[] nodeInfo = content.split("\n");
        Gson gson = new Gson();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < nodeInfo.length; ++ i){
            res.append(gson.fromJson(nodeInfo[i], Node.class));
            res.append("\n");
        }
        return res.toString();
    }


}
