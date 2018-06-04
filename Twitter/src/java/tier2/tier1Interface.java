package tier2;

import simo.mi6.project.tier3.TwitterDBService;

public class tier1Interface {

    // Crée un nouvel utilisateur si username n'est pas déjà utilisé.
    public void createUser(String username, String password) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Si username existe déjà en base de données...
            if (twitterDB.getAllUsers().contains(username)) {
                System.out.println("L'utilisateur existe déjà.");
            } // Sinon...
            else {
                twitterDB.createNewUser(username, password);
                System.out.println("L'utilisateur a été créé.");
            }
        } catch (Exception e) {

        }
    }

}
