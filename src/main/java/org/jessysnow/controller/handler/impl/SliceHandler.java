package org.jessysnow.controller.handler.impl;

import org.jessysnow.controller.handler.AbstractHandler;

/**
 * Get proxies name array part from response content
 */
public class SliceHandler extends AbstractHandler<String> {
    public static final String PREFIX = "{\"proxies\":{";
    public static final String SUFFIX = "}}";

    private void slice(){
        int length = content.length();
        int begin = PREFIX.length() + 1;
        int end = length - SUFFIX.length();
        super.content = super.content.substring(begin, end);
    }

    @Override
    public String handle() {
        slice();
        return super.content.replace("},\"","}\n\"");
    }
}
