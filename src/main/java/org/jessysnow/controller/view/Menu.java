package org.jessysnow.controller.view;

import org.jessysnow.controller.pojo.enums.Option;

/**
 * Console Menu
 */
public class Menu {
    private static final String menu;

    // concat Enum option to build menu
    static {
        Option[] options = Option.class.getEnumConstants();
        StringBuilder temp = new StringBuilder();
        for(Option option : options){
            temp.append(option.toString());
        }
        temp.append("Enter Q to quit!");
        menu = temp.toString();
    }

    public static void show(){
        System.out.println(menu);
    }

}
