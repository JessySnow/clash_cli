package org.jessysnow.controller;

import org.jessysnow.controller.pojo.HttpParamEntry;
import org.jessysnow.controller.pojo.RequestContainer;
import org.jessysnow.controller.pojo.SimpleHttpClient;
import org.jessysnow.controller.view.Menu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

/**
 * Entrance
 */
public class Boot {
    public static String host = "127.0.0.1";
    public static int port = 9090;

    public static final RequestContainer[] containers = RequestContainer.class.getEnumConstants();

    public static void main(String[] args) throws MalformedURLException {
        if(args.length == 1){
            fillPort(args[0]);
        }
        if(args.length == 2){
            fillPort(args[1]);
            fillHost(args[0]);
        }

        // Base url, it's a absolute URL
        URL baseURL = new URL("http://" + host + ":" + port + "/");
        Scanner keyIn = new Scanner(System.in);
        String input;

        // Into event loop
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

            // TODO
            // format output
            String res = SimpleHttpClient.doRequest(baseURL, specificRequestContainer);
            System.out.println(res);

            Menu.show();
        }
    }

    /**
     * Check if the port is valid, and fill it
     * @param port port, get from command line param
     */
    private static void fillPort(String port){
        try{
            Boot.port = Integer.parseInt(port);
            if(Boot.port > 65535 || Boot.port <= 0){
                throw new Exception("Port number out of range! Port: " + port);
            }
        }catch (NumberFormatException e){
            System.out.println("Check port format! Port: " + port);
            System.exit(1);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Check if the host is valid, and fill it
     * @param host host, get from command line param
     */
    private static void fillHost(String host){
        // try fast fail
        String requestURL = "unknown error!";
        try {
            requestURL = "http://" + host + ":" + host;
            URL test = new URL(requestURL);
        } catch (MalformedURLException e) {
            System.out.println("Not a valid and host combination! Request URL: " + requestURL);
            System.exit(1);
        }
        Boot.host = requestURL;
    }

    private static boolean checkOptionValid(int option){
        if(option < 0 || option >= containers.length){
            System.out.printf("Not a valid option, try enter a option number between %d and %d", 0, containers.length - 1);
            return false;
        }
        return true;
    }
}
