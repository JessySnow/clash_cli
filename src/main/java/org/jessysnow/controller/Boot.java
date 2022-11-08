package org.jessysnow.controller;

import org.jessysnow.controller.pojo.net.nio.NIOHttpClient;
import org.jessysnow.controller.pojo.enums.HttpParamEntry;
import org.jessysnow.controller.pojo.enums.RequestContainer;
import org.jessysnow.controller.pojo.net.SimpleHttpClient;
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

    public static void main(String[] args) throws MalformedURLException {
        if(args.length == 1){
            setPort(args[0]);
        }
        if(args.length == 2){
            setPort(args[1]);
            setHost(args[0]);
        }

        // base url, it's an absolute URL
        URL baseURL = new URL("http://" + host + ":" + port);
        Scanner keyIn = new Scanner(System.in);

        // event loop
        Menu.show();
        String input;
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

            // invoke RESTFul API, will be blocking here
            Object res = SimpleHttpClient.request(baseURL, specificRequestContainer);
            if(res != null && res.getClass().equals(String.class)){
                System.out.println(res);
            }
            Menu.show();
        }

        // clean up
        keyIn.close();
        NIOHttpClient.executor.close();
    }
}
