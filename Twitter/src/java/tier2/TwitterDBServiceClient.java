package tier2;

import java.rmi.registry.*;
import simo.mi6.project.tier3.TwitterDBService;

public class TwitterDBServiceClient {

    // Retourne un objet TwitterDBService.
    public static TwitterDBService getTwitterDBServiceClient() throws Exception {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        // Connexion à la base de données.
        Registry registry = LocateRegistry.getRegistry("86.76.4.24", 3200);
        TwitterDBService service;
        service = (TwitterDBService) registry.lookup("TwitterDBService");

        return service;
    }
}
