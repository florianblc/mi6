package simo.mi6.project.tier1;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Scanner;

public class Tier1ServiceWebClient {

    private static WebResource serviceTier2 = null;
    private static final Scanner SCANNER = new Scanner(System.in);
    private static boolean logged = false;
    private static String login = null;
    private static String password = null;

    public static void main(String args[]) throws Exception {
        serviceTier2 = Client.create().resource("http://localhost:8080/Twitter");

        // Lancement de l'interface textuelle.
        launchInterface();
    }

    public static void launchInterface() {
        boolean end = false;

        while (!end) {
            System.out.println("Que voulez-vous faire ?");
            System.out.println("1 - Créer un Compte");
            System.out.println("2 - " + (logged ? "Déconnexion" : "Connexion"));
            System.out.println("3 - Ecrire un tweet");
            System.out.println("4 - S'abonner à un utilisateur");
            System.out.println("5 - Se désabonner d'un utilisateur");
            System.out.println("6 - Consulter le fil d'actualité");
            System.out.println("7 - Rechercher un utilisateur");
            System.out.println("8 - Rechercher un tweet");
            System.out.println("9 - Quitter\n");
            System.out.print("Saisir un choix : ");

            String action = SCANNER.nextLine();

            System.out.println();

            switch (action) {
                case "1":
                    createUser();
                    break;

                case "2":
                    if (logged) {
                        deconnexion();
                    } else {
                        connexion();
                    }
                    break;

                case "3":
                    createNewTweet();
                    break;

                case "4":
                    startFollowing();
                    break;

                case "5":
                    stopFollowing();
                    break;

                case "6":
                    newsFeed();
                    break;

                case "7":
                    searchForUser();
                    break;

                case "8":
                    searchForTweet();
                    break;

                case "9":
                    System.out.println("Merci d'avoir utilisé ce service, à bientôt.\n");
                    end = true;
                    break;

                default:
                    System.out.println("Choix incorrect, veuillez recommencer.\n");
                    break;
            }
        }
    }

    protected static boolean isLogged() {
        if (logged) {
            return true;
        } else {
            System.out.println("Vous devez être connecté pour utiliser cette fonction.\n");

            return false;
        }
    }

    private static void createUser() {
        String newLogin;
        String newPassword = null;
        String newPasswordConfirmation = null;

        System.out.print("Saisir un nom d'utilisateur : ");
        newLogin = SCANNER.nextLine();

        while (newPassword == null || !newPassword.equals(newPasswordConfirmation)) {
            System.out.print("Saisir un mot de passe : ");
            newPassword = SCANNER.nextLine();

            System.out.print("Confirmer mot de passe : ");
            newPasswordConfirmation = SCANNER.nextLine();

            System.out.println();

            if (!newPassword.equals(newPasswordConfirmation)) {
                System.out.println("Les mots de passe saisis ne correspondent pas, veuillez réessayer.\n");
            }
        }

        if (Boolean.parseBoolean(serviceTier2.path("createUser/" + newLogin + "/" + newPassword).get(String.class))) {
            System.out.println("Le compte a été créé avec succès.\n");
        } else {
            System.out.println("Erreur lors de la création du compte.\n");
        }
    }

    public static void createNewTweet() {
        if (isLogged()) {
            String tweet;

            System.out.print("Saisir le tweet : ");
            tweet = SCANNER.nextLine();
            System.out.println();

            if (Boolean.parseBoolean(serviceTier2.path("createNewTweet/" + login + "/" + password + "/" + tweet).get(String.class))) {
                System.out.println("Le tweet a bien été publié.\n");
            } else {
                System.out.println("Erreur lors de la publication, le tweet doit faire 280 au maximum.\n");
            }
        }
    }

    public static void connexion() {
        System.out.print("Saisir votre nom d'utilisateur : ");
        login = SCANNER.nextLine();
        System.out.print("Saisir votre mot de passe : ");
        password = SCANNER.nextLine();
        System.out.println();

        if (Boolean.parseBoolean(serviceTier2.path("userConnexion/" + login + "/" + password).get(String.class))) {
            logged = true;
            System.out.println("Vous êtes maintenant connecté.\n");
        } else {
            login = null;
            password = null;
            System.out.println("Nom d'utilisateur/Mot de passe incorrecte, veuillez réessayer.\n");
        }
    }

    public static void deconnexion() {
        login = null;
        password = null;
        logged = false;

        System.out.println("Vous êtes maintenant déconnecté.\n");
    }

    public static void startFollowing() {
        if (isLogged()) {
            String userToFollow;

            System.out.print("Saisir le nom de l'utilisateur à suivre : ");
            userToFollow = SCANNER.nextLine();
            System.out.println();

            if (Boolean.parseBoolean(serviceTier2.path("startFollowing/" + login + "/" + userToFollow).get(String.class))) {
                System.out.println("Vous suivez maintenant l'utilisateur " + userToFollow + ".\n");
            } else {
                System.out.println("Erreur, l'utilistaur " + userToFollow + " n'existe pas ou vous êtes déjà abonné à celui-ci.\n");
            }
        }
    }

    public static void stopFollowing() {
        if (isLogged()) {
            String userToUnfollow;

            System.out.print("Saisir le nom de l'utilisateur à ne plus suivre : ");
            userToUnfollow = SCANNER.nextLine();
            System.out.println();

            if (Boolean.parseBoolean(serviceTier2.path("stopFollowing/" + login + "/" + userToUnfollow).get(String.class))) {
                System.out.println("Vous ne suivez plus l'utilisateur " + userToUnfollow + ".\n");
            } else {
                System.out.println("Erreur, vous n'êtes pas abonné à l'utilisateur " + userToUnfollow + ".\n");
            }
        }
    }

    public static void searchForUser() {
        String userSearchString;

        System.out.print("Saisir un nom d'utilisateur à rechercher : ");
        userSearchString = SCANNER.nextLine();
        System.out.println();

        String[] users = serviceTier2.path("searchForUser/" + userSearchString).get(String.class).split(";;");

        if (users.length == 0) {
            System.out.println("Aucun utilisateur ne correspond à la recherche.");
        } else {
            System.out.println("La recherche a retournée les utilisateurs suivants :");
            for (String user : users) {
                System.out.println(user);
            }
        }

        System.out.println();
    }

    public static void newsFeed() {
        if (isLogged()) {
            String[] tweets = serviceTier2.path("getTweetsForUser/" + login).get(String.class).split(";;");

            if (tweets.length > 0) {
                for (String tweet : tweets) {
                    System.out.println("------------------------------------------------");
                    System.out.println(tweet);
                }
                System.out.println("------------------------------------------------\n");
            } else {
                System.out.println("Aucun tweet dans votre fil d'actualité.\n");
            }
        }
    }

    public static void searchForTweet() {
        String searchString;

        System.out.print("Saisir les termes recherchés : ");
        searchString = SCANNER.nextLine();
        System.out.println();

        String[] tweets = serviceTier2.path("searchInTweets/" + searchString).get(String.class).split(";;");

        if (tweets.length > 0) {
            for (String tweet : tweets) {
                System.out.println("------------------------------------------------");
                System.out.println(tweet);
            }
            System.out.println("------------------------------------------------\n");
        } else {
            System.out.println("Aucun tweet ne correspond à votre recherche.\n");
        }
    }
}
