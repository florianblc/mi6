package simo.mi6.project.tier3;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Cette interface est l'interface RMI du tier 3 du projet "Twitter".
 */
public interface TwitterDBService extends Remote 
{
	/**
	 * Retourne la liste de tous les noms d'utilisateurs enregistrés dans la base de données.
	 */
	public List<String> getAllUsers() throws RemoteException;
	
	/**
	 * Crée un nouvel utilisateur avec son nom d'utilisateur et son mot de passe.
	 * S'il existe déjà, rien ne se passe.
	 */
	public void createNewUser(String username, String password) throws RemoteException;

	/**
	 * Retourne true si le couple "nom d'utilisateur/mot de passe" est correct.
	 * Retourn false si le nom d'utilisateur n'existe pas ou si le mot de passe est incorrect.
	 */
	public boolean isUserPasswordCorrect(String username, String password) throws RemoteException;
	
	/**
	 * Supprime un utilisateur via son nom d'utilisateur.
	 * Tous les tweets et les abonnements associés seront supprimés.
	 * S'il n'existe pas, rien ne se passe.
	 */
	public void removeUser(String username) throws RemoteException;
	
	/**
	 * Retourne la liste des nom d'utilisateurs qui sont abonnés à l'utilisateur dont le nom est donné en paramètre.
	 * Si l'utilisateur n'existe pas, une liste vide est retournée.
	 * Exemple : si A et B suivent C, getUsersFollowing(C) retourne A et B.
	 */
	public List<String> getUsersFollowing(String username) throws RemoteException;
	
	/**
	 * Retourne la liste des noms d'utilisateurs dont l'utilisateur dont le nom est donné en paramètre est abonné.
	 * Si l'utilisateur n'existe pas, une liste vide est retournée.
	 * Exemple : si A suit B et C, getUsersFollowedBy(A) retourne B et C.
	 */
	public List<String> getUsersFollowedBy(String username) throws RemoteException;
	
	/**
	 * L'utilisateur dont le nom est en premier paramètre devient abonné à l'utilisateur dont le nom est en deuxième paramètre.
	 * Si un des utilisateurs n'existe pas, rien ne se passe.
	 * Si l'utilisateur dont le nom est en premier paramètre était déjà abonné au second, rien ne se passe.
	 * Si les deux paramètres sont identiques, rien ne se passe.
	 */
	public void startFollowing(String followerUsername, String followedUsername) throws RemoteException;

	/**
	 * L'utilisateur dont le nom est en premier paramètre cesse d'être abonné à l'utilisateur dont le nom est en deuxième paramètre.
	 * Si un des utilisateurs n'existe pas, rien ne se passe.
	 * Si l'utilisateur dont le nom est en premier paramètre n'était déjà pas abonné au second, rien ne se passe.
	 * Si les deux paramètres sont identiques, rien ne se passe.
	 */
	public void stopFollowing(String followerUsername, String followedUsername) throws RemoteException;
	
	/**
	 * Publie un nouveau tweet. 
	 * Le premier paramètre est le nom de l'utilisateur qui publie le tweet.
	 * Le deuxième paramètre est le tweet en question.
	 * Si l'utilisateur n'existe pas, rien ne se passe.
	 */
	public void createNewTweet(String username, String tweet) throws RemoteException;
	
	/**
	 * Retourne la liste des tweets publiés par un utilisateur.
	 * Si l'utilisateur n'existe pas, la liste retournée est vide.
	 */
	public List<String> getTweetsOfUser(String username) throws RemoteException;
}
