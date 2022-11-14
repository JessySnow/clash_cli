package org.jessysnow.ccli.component.handler;

import org.jessysnow.ccli.component.handler.StatelessHandler;

/**
 * Get proxies name array part from response content
 */
public class SliceHandler extends StatelessHandler<String> {
    public static final String PREFIX = "{\"proxies\":{";
    public static final String SUFFIX = "}}";

    private String slice(String content){
        int length = content.length();
        int begin = PREFIX.length();
        int end = length - SUFFIX.length();
        return content.substring(begin, end);
    }

    // remove proxy node name before json object
    @Override
    public String handle(String content) {
        content = slice(content);
        return content.replaceAll("},\"", "}\n\"")
                .replaceAll("\"*.*\":\\{","{");
    }
}
