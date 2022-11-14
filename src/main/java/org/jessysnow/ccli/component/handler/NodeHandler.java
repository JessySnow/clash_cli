package org.jessysnow.ccli.component.handler;

import com.google.gson.Gson;
import org.jessysnow.ccli.component.handler.StatelessHandler;
import org.jessysnow.ccli.component.jsonpojo.Node;

public class NodeHandler extends StatelessHandler<String> {

    @Override
    public String handle(String content) {
        if(content == null || content.isEmpty()){
            return content;
        }
        String[] nodeInfo = content.split("\n");
        Gson gson = new Gson();
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < nodeInfo.length; ++ i){
            res.append(gson.fromJson(nodeInfo[i], Node.class));
            res.append("\n");
        }
        return res.toString();
    }
}
