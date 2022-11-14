package org.jessysnow.ccli;

import org.jessysnow.ccli.component.net.NIOHttpClient;
import org.jessysnow.ccli.enums.HttpParamEntry;
import org.jessysnow.ccli.enums.RequestContainer;
import org.jessysnow.ccli.component.net.SimpleHttpClient;
import org.jessysnow.ccli.view.Menu;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

import static org.jessysnow.ccli.utils.BootHelper.setHost;
import static org.jessysnow.ccli.utils.BootHelper.setPort;
import static org.jessysnow.ccli.utils.BootHelper.checkOptionValid;

/**
 * Entrance
 */
public class Bootstrap {
    public static String host = "127.0.0.1";
    public static int port = 9090;

    public static final RequestContainer[] containers = RequestContainer.class.getEnumConstants();

    public static void main(String[] args) throws MalformedURLException {
        //parse custom host and port
        if(args.length == 1){
            setPort(args[0]);
        }
        if(args.length == 2){
            setPort(args[1]);
            setHost(args[0]);
        }

        // event loop
        Menu.show();
        String input;
        Scanner keyIn = new Scanner(System.in);
        URL baseURL = new URL("http://" + host + ":" + port);
        NIOHttpClient nioHttpClient = new NIOHttpClient(host, port);
        SimpleHttpClient simpleHttpClient = new SimpleHttpClient();
        while (!(input = keyIn.nextLine()).trim().equalsIgnoreCase("Q")){
            int opChoose;
            try {
                opChoose = Integer.parseInt(input);
                if(!checkOptionValid(opChoose)){
                    continue;
                }
            }catch (NumberFormatException e){
                System.out.printf("Unsupported option! It's not a valid number: %s", input);
                continue;
            }

            // construct request properties eg. request method, request path, request param
            RequestContainer specificRequestContainer = containers[opChoose];
            if(specificRequestContainer.getRequestParam() != null){
                // loop-fill request param
                for (Map.Entry<HttpParamEntry, String> paramEntry : specificRequestContainer
                        .getRequestParam()
                        .entrySet())
                {
                    System.out.printf("Enter %s: ", paramEntry.getKey().getParamShownName());
                    String keyValue = keyIn.nextLine();
                    paramEntry.setValue(keyValue);
                }
            }

            if(specificRequestContainer.isLongConnection()){
                nioHttpClient.doRequest(specificRequestContainer, specificRequestContainer.getOutputStream());
            }else{
                String res = simpleHttpClient.doRequest(baseURL, specificRequestContainer);
                System.out.println(res);
            }

            Menu.show();
        }

        // clean up
        keyIn.close();
        nioHttpClient.cleanUp();
    }
}
