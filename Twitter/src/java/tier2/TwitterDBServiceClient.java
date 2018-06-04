/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tier2;

import java.rmi.registry.*;
import simo.mi6.project.tier3.TwitterDBService;

/**
 *
 * @author trist
 */
public class TwitterDBServiceClient {

    public static TwitterDBService getTwitterDBServiceClient() throws Exception {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        // Connexion à la base de données.
        Registry registry = LocateRegistry.getRegistry("86.76.4.24", 3200);
        TwitterDBService service;
        service = (TwitterDBService) registry.lookup("TwitterDBService");

        return service;
    }
}
