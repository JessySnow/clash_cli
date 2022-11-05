package org.jessysnow.controller.utils;

import org.jessysnow.controller.Boot;

import java.net.MalformedURLException;
import java.net.URL;

public class BootHelper {
    /**
     * Check if the port is valid, and fill it
     * @param port port, get from command line param
     */
    public static void setPort(String port){
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
    public static void setHost(String host){
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

    /**
     * check if the option is valid
     */
    public static boolean checkOptionValid(int option){
        if(option < 0 || option >= Boot.containers.length){
            System.out.printf("Not a valid option, try enter a option number between %d and %d", 0, Boot.containers.length - 1);
            return false;
        }
        return true;
    }
}
