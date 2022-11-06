package org.jessysnow.controller;

import org.jessysnow.controller.pojo.enums.HttpParamEntry;
import org.jessysnow.controller.pojo.enums.RequestContainer;
import org.jessysnow.controller.net.SimpleHttpClient;
import org.jessysnow.controller.view.Menu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import static org.jessysnow.controller.utils.BootHelper.setHost;
import static org.jessysnow.controller.utils.BootHelper.setPort;
import static org.jessysnow.controller.utils.BootHelper.checkOptionValid;

/**
 * Entrance
 */
public class Boot {
    public static String host = "127.0.0.1";
    public static int port = 9090;

    public static final RequestContainer[] containers = RequestContainer.class.getEnumConstants();

    public static void main(String[] args) {
        if(args.length == 1){
            setPort(args[0]);
        }
        if(args.length == 2){
            setPort(args[1]);
            setHost(args[0]);
        }

        // Base url, it's a absolute URL
        URL baseURL = null;
        try {
            baseURL = new URL("http://" + host + ":" + port);
        }catch(MalformedURLException e){
            System.out.printf("Bad url format. URL: %s", "http://"+host+":"+port +": ");
            System.exit(1);
        }
        Scanner keyIn = new Scanner(System.in);
        String input;

        // event loop
        Menu.show();
        while (!(input = keyIn.nextLine()).trim().equalsIgnoreCase("Q")){
            int opChoose;
            if(input.trim().equalsIgnoreCase("Q")){
                break;
            }
            try {
                opChoose = Integer.parseInt(input);
            }catch (NumberFormatException e){
                System.out.println("Unsupported option!");
                continue;
            }
            if(!checkOptionValid(opChoose)){
                continue;
            }

            // construct request properties eg. request method, request path, request param
            RequestContainer specificRequestContainer = containers[opChoose];
            if(specificRequestContainer.getRequestParam() != null){

                // loop-fill request param
                for (Map.Entry<HttpParamEntry, String> paramEntry : specificRequestContainer
                        .getRequestParam()
                        .entrySet()) {
                    System.out.printf("Enter %s: ", paramEntry.getKey().getParamShownName());
                    String keyValue = keyIn.nextLine();
                    paramEntry.setValue(keyValue);
                }
            }

            Object res = SimpleHttpClient.request(baseURL, specificRequestContainer);
            if(res != null && res.getClass().equals(String.class)){
                System.out.println(res);
            }
            Menu.show();
        }

        // clean up
        keyIn.close();
    }
}
