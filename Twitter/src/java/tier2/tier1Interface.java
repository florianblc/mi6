package tier2;

import simo.mi6.project.tier3.TwitterDBService;

public class tier1Interface {

    // Crée un nouvel utilisateur si username n'est pas déjà utilisé.
    // Retourne true si l'utilisateur est créé, false dans le cas concert.
    public boolean createUser(String username, String password) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Si username existe déjà en base de données...
            if (twitterDB.getAllUsers().contains(username)) {
                System.out.println("L'utilisateur existe déjà.");
            } // Sinon...
            else {
                // Ajout de l'utilisateur en base de données.
                twitterDB.createNewUser(username, password);
                System.out.println("L'utilisateur a été créé.");

                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    // Supprime un utilisateur si username est présent en base de données.
    // Return true si l'utilisateur est supprimé, false sinon.
    public boolean deleteUser(String username) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Si username existe déjà en base de données...
            if (twitterDB.getAllUsers().contains(username)) {
                // Suppression de l'utilisateur de la base de données.
                twitterDB.removeUser(username);
                System.out.println("L'utilisateur a été supprimé.");

                return true;
            } // Sinon...
            else {
                System.out.println("L'utilisateur n'existe pas.");
            }
        } catch (Exception e) {
        }

        return false;
    }

    // Ajoute un nouveau tweet si username existe en base de données et que le tweet fait < de 280 charactères.
    // Retourne true si le tweet est posté, false sinon.
    public boolean createNewTweet(String username, String tweet) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Si username existe déjà en base de données...
            if (twitterDB.getAllUsers().contains(username)) {
                // Si le tweet fait moins de 280 charactères...
                if (tweet.length() < 280) {
                    // Ajout du tweet en base de données.
                    twitterDB.createNewTweet(username, tweet);
                    System.out.println("Le tweet a été créé.");

                    return true;
                } // Sinon..
                else {
                    System.out.println("Le tweet est trop long.");
                }
            } // Sinon...
            else {
                System.out.println("L'utilisateur n'existe pas.");
            }
        } catch (Exception e) {
        }

        return false;
    }

}
