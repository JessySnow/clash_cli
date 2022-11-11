package org.jessysnow.ccli.component.handler.impl;

import com.google.gson.Gson;
import org.jessysnow.ccli.component.handler.AbstractHandler;
import org.jessysnow.ccli.component.jsonpojo.Node;

/**
 * Get proxies name array part from response content
 */
public class SliceHandler extends AbstractHandler<String> {
    public static final String PREFIX = "{\"proxies\":{";
    public static final String SUFFIX = "}}";

    private void slice(){
        int length = content.length();
        int begin = PREFIX.length();
        int end = length - SUFFIX.length();
        super.content = super.content.substring(begin, end);
    }

    // remove proxy node name before json object
    @Override
    public String handle() {
        slice();
        return super.content.replaceAll("},\"", "}\n\"")
                .replaceAll("\"*.*\":\\{","{");
    }

    public static void main(String[] args) {
        String json = "{\"history\":[{\"time\":\"2022-11-11T14:59:35.994043+08:00\",\"delay\":1127},{\"time\":\"2022-11-11T14:59:36.060894+08:00\",\"delay\":1197},{\"time\":\"2022-11-11T14:59:38.575484+08:00\",\"delay\":1179},{\"time\":\"2022-11-11T15:12:38.894702+08:00\",\"delay\":0}],\"name\":\"\uD83C\uDDEB\uD83C\uDDF7 法国-边缘访问 DP\",\"type\":\"Trojan\",\"udp\":true}";
        Gson gson = new Gson();
        Node node = gson.fromJson(json, Node.class);
        System.out.println(node);
    }
}
