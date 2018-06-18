/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simo.mi6.project.tier2;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import simo.mi6.project.tier3.TwitterDBService;
import java.util.Arrays;

/**
 * REST Web Service
 *
 * @author trist
 */
@Path("/")
public class ServiceWebTier2 {

    // Crée un nouvel utilisateur si username n'est pas déjà utilisé.
    // Retourne true si l'utilisateur est créé, false dans le cas concert.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("createUser/{username}/{password}")
    public boolean createUser(@PathParam("username") String username, @PathParam("password") String password) {
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
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("deleteUser/{username}")
    public boolean deleteUser(@PathParam("username") String username) {
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

    // Vérifier la correspondance entre le nom d'utilisateur et le mode passe.
    // Retourne true si le couple nom d'utilisateur/Mot de passe est correcte, false sinon.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("userConnexion/{username}/{password}")
    public boolean userConnexion(@PathParam("username") String username, @PathParam("password") String password) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Si le couple username/password est correcte...
            if (twitterDB.isUserPasswordCorrect(username, password)) {
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    // Ajoute un nouveau tweet si username existe en base de données, que le combo username/password est correcte et que le tweet fait < de 280 charactères.
    // Retourne true si le tweet est posté, false sinon.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("createNewTweet/{username}/{password}/{tweet}")
    public boolean createNewTweet(@PathParam("username") String username, @PathParam("password") String password, @PathParam("tweet") String tweet) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Si username existe déjà en base de données...
            if (twitterDB.getAllUsers().contains(username) && twitterDB.isUserPasswordCorrect(username, password)) {
                // Si le tweet fait moins de 280 charactères...
                if (tweet.length() < 280) {
                    // Ajout du tweet en base de données.
                    twitterDB.createNewTweet(username, tweet);
                    System.out.println("Le tweet a été créé.");

                    return true;
                }
            }
        } catch (Exception e) {
        }

        return false;
    }

    // Retourne la liste des tweets à destination de l'utilisateur username.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("getTweetsForUser/{username}")
    public String getTweetsForUser(@PathParam("username") String username) {
        // Initialisation de la liste des tweets à retourner.
        ArrayList<String> tweetsList = new ArrayList<>();
        String[] tweets;

        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Récupération des utilisateurs suivis par l'utilisateur.
            List<String> followedUsers = twitterDB.getUsersFollowedBy(username);

            // Pour chaques utilisateurs suivis...
            for (String user : followedUsers) {
                // Pour chaques tweet de l'utilisateur suivis...
                for (String tweet : twitterDB.getTweetsOfUser(user)) {
                    // Ajout du tweet à la liste des tweets retournés.
                    tweetsList.add(tweet);
                }
            }
        } catch (Exception e) {
        }

        // Remplissage de la liste des tweets à retourner.
        tweets = new String[tweetsList.size()];
        for (int i = 0; i < tweetsList.size(); i++) {
            tweets[i] = tweetsList.get(i);
        }

        return Arrays.toString(tweets);
    }

    // Abonne l'utilisateur followerUsername à l'utilisateur followedUsername.
    // Retourne true si followerUsername et followedUsername existe en base de données 
    // et si followerUsername n'est pas déjà abonné à followedUsername, false sinon.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("startFollowing/{followerUsername}/{followedUsername}")
    public boolean startFollowing(@PathParam("followerUsername") String followerUsername, @PathParam("followedUsername") String followedUsername) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            if (twitterDB.getAllUsers().contains(followerUsername)
                    && twitterDB.getAllUsers().contains(followedUsername)
                    && !twitterDB.getUsersFollowedBy(followerUsername).contains(followedUsername)) {
                // Abonnement à l'utilisateur.
                twitterDB.startFollowing(followerUsername, followedUsername);

                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    // Désabonne l'utilisateur followerUsername de l'utilisateur followedUsername.
    // Retourne true si followerUsername et followedUsername existe en base de données 
    // et si followerUsername est abonné à followedUsername, false sinon.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("stopFollowing/{followerUsername}/{followedUsername}")
    public boolean stopFollowing(@PathParam("followerUsername") String followerUsername, @PathParam("followedUsername") String followedUsername) {
        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            if (twitterDB.getAllUsers().contains(followerUsername)
                    && twitterDB.getAllUsers().contains(followedUsername)
                    && twitterDB.getUsersFollowedBy(followerUsername).contains(followedUsername)) {
                // Désabonnement de l'utilisateur.
                twitterDB.startFollowing(followerUsername, followedUsername);

                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    // Retourne la liste des utilisateurs qui correspondent à la recherche.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("searchForUser/{searchUsername}")
    public String searchForUser(@PathParam("searchUsername") String searchUsername) {
        // Liste des utilisateurs correspondant à la recherche.
        ArrayList<String> matchingUsersList = new ArrayList<>();
        String[] matchingUser;

        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            List<String> users = twitterDB.getAllUsers();

            // Pour chaques utilisateur dans la base de données,
            // si la chaîne recherchée est contenu dans le nom d'utilisateur...
            users.stream().filter((user) -> (user.toUpperCase().contains(searchUsername.trim().toUpperCase()))).forEachOrdered((user) -> {
                // Ajout de l'utilisateur à la liste des utilisateurs retournés.
                matchingUsersList.add(user);
            });
        } catch (Exception e) {
        }

        // Remplissage de la liste d'utilisateurs à retourner.
        matchingUser = new String[matchingUsersList.size()];
        for (int i = 0; i < matchingUsersList.size(); i++) {
            matchingUser[i] = matchingUsersList.get(i);
        }

        return Arrays.toString(matchingUser);
    }

    // Retourne la liste des tweets qui correspondent à la recherche.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("searchInTweets/{searchString}")
    public String searchInTweets(@PathParam("searchString") String searchString) {
        // Liste des tweets correspondant à la recherche.
        ArrayList<String> tweetsList = new ArrayList<>();
        String[] tweets;

        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Pour chaques utilisateurs de la base de données...
            for (String user : twitterDB.getAllUsers()) {
                // Pour chaques tweet de l'utilisateur...
                for (String tweet : twitterDB.getTweetsOfUser(user)) {
                    // Si la chaîne recherchée est contenu dans le tweet...
                    if (tweet.toUpperCase().contains(searchString.trim().toUpperCase())) {
                        // Ajout du tweet à la liste des tweets retournés.
                        tweetsList.add(tweet);
                    }
                }
            }
        } catch (Exception e) {
        }

        // Remplissage de la liste des tweets à retourner.
        tweets = new String[tweetsList.size()];
        for (int i = 0; i < tweetsList.size(); i++) {
            tweets[i] = tweetsList.get(i);
        }

        return Arrays.toString(tweets);
    }

    // Retourne la liste de tout les utilisateurs.
    @GET
    @Consumes(MediaType.APPLICATION_XML)
    @Path("getAllUsers")
    public String getAllUsers() {
        // Liste des tweets correspondant à la recherche.
        ArrayList<String> usersList = new ArrayList<>();
        String[] users;

        try {
            // Initialisation de la connexion à la base de données.
            TwitterDBService twitterDB = TwitterDBServiceClient.getTwitterDBServiceClient();

            // Pour chaques utilisateurs de la base de données...
            for (String user : twitterDB.getAllUsers()) {
                usersList.add(user);
            }
        } catch (Exception e) {
        }

        // Remplissage de la liste des tweets à retourner.
        users = new String[usersList.size()];
        for (int i = 0; i < usersList.size(); i++) {
            users[i] = usersList.get(i);
        }

        return Arrays.toString(users);
    }
}
